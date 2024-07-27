package com.solux.greenish.search.service;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.search.dto.PlantDetail;
import com.solux.greenish.search.dto.PlantResponse;
import com.solux.greenish.search.dto.ResponseDtoPlantInfo;
import com.solux.greenish.search.dto.ResponseDtoPlantdtl;
import com.solux.greenish.search.entity.ApiPlant;

import com.solux.greenish.search.repository.ApiPlantRepository;
import com.solux.greenish.search.specification.ApiPlantSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import static io.micrometer.common.util.StringUtils.truncate;
import static org.springframework.util.StringUtils.hasText;

@Service
public class PlantSearchService {
    @Value("${nongsaro.api.key}")
    private String apiKey;
    @Value("${nongsaro.api.url}")
    private String baseUrl;

    private  final ApiPlantRepository apiPlantRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public PlantSearchService(RestTemplate restTemplate,ApiPlantRepository apiPlantRepository) {
        this.restTemplate = restTemplate;
        this.apiPlantRepository=apiPlantRepository;
    }
    //db확인
    public long countPlants() {
        return apiPlantRepository.count();
    }

    // 관리도 변환
    private String mapDifficultyToDemand(String difficulty) {
        switch (difficulty) {
            case "초보자":
                return "낮음";
            case "초중급자":
                return "보통";
            case "고급자":
                return "필요함";
            default:
                return null;
        }
    }

    //광도 변화
    private String mapPlaceTolight(String place) {
        switch (place) {
            case "욕실" : case "사무실 책상":
                return "낮은";
            case "베란다": case"밝은 사무실":
                return "중간";
            case "정원": case "테라스":
                return "높은";
            default:
                return  null;

        }
    }

    //api를 객체로 받기
    //객체로 받는 건 번호, 유통명, 사진
    private PlantResponse XmlToPlant(String url) throws JAXBException {
        String apiResponse = restTemplate.getForObject(url, String.class);
        JAXBContext jaxbContext = JAXBContext.newInstance(PlantResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        PlantResponse response = (PlantResponse) jaxbUnmarshaller.unmarshal(new StringReader(apiResponse));

        //사진은 하나씩만
        response.getBody().getPlantList().forEach(PlantResponse.Body.Plant::oneRtnFile);
        return response;

    }

    //식물의 세부 정보 객체로 불러오는 메소드
    //Api를 DB에 저장하기 위해 사용
    private PlantDetail toPlantDetail(String url, PlantResponse.Body.Plant plantData) throws JAXBException{
        String apiResponse = restTemplate.getForObject(url, String.class);
        JAXBContext jaxbContext = JAXBContext.newInstance(PlantDetail.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        PlantDetail plantDetail = (PlantDetail) jaxbUnmarshaller.unmarshal(new StringReader(apiResponse));
        //사진 값 하나만 저장함
        //유통명 null값인 경우 검색한 값 넣어줌
        if (plantDetail.getBody().getPlantdtl().getDistbNm().isEmpty()){
            plantDetail.getBody().getPlantdtl().setDistbNm(plantData.getCntntsSj());
        }
        plantDetail.getBody().getPlantdtl().setRtnFileUrl(plantData.getRtnFileUrl());
        return plantDetail;
    }

    //첫화면에서 식물 정보를 넘기는 메소드
    //완료
    public Map<String, List<PlantResponse.Body.Plant>> getInitialPlants(){
        //추운 실내 추천(0도) : 가울테리아, 금식나무, 무늬석창포, 자금우
        List<String> coldPlantsNo= new ArrayList<>(Arrays.asList("12938", "12998", "18576", "17741"));
        //따뜻한 실내 추천(15도 이상) : 히포에스테스, 해마리아, 털달개비, 듀란타
        List<String> warmPlantsNo = new ArrayList<>(Arrays.asList("12901", "12987", "12996", "14675"));
        //따뜻한 식물리스트

        List<PlantResponse.Body.Plant> coldPlants=new ArrayList<>();
      coldPlantsNo.stream()
                .map(cntntsNo -> {
                    try {
                        PlantResponse.Body.Plant plant=new PlantResponse.Body.Plant();
                        ApiPlant apiPlant = apiPlantRepository.findBycntntsNo(cntntsNo);
                        plant.setCntntsNo(apiPlant.getCntntsNo());
                        plant.setCntntsSj(apiPlant.getDistbNm());
                        plant.setRtnFileUrl(apiPlant.getRtnFileUrl());
                        return plant;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
              .filter(Objects::nonNull)
              .forEach(coldPlants::add);
        //따뜻한 식물리스트
        List<PlantResponse.Body.Plant> warmPlants=new ArrayList<>();
        warmPlantsNo.stream()
                .map(cntntsNo -> {
                    try {
                        PlantResponse.Body.Plant plant=new PlantResponse.Body.Plant();
                        ApiPlant apiPlant = apiPlantRepository.findBycntntsNo(cntntsNo);
                        plant.setCntntsNo(apiPlant.getCntntsNo());
                        plant.setCntntsSj(apiPlant.getDistbNm());
                        plant.setRtnFileUrl(apiPlant.getRtnFileUrl());
                        return plant;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .forEach(warmPlants::add);

        //반환
        Map<String, List<PlantResponse.Body.Plant>> initialPlants = new HashMap<>();
        initialPlants.put("coldplants", coldPlants);
        initialPlants.put("warmplants", warmPlants);
        return initialPlants;



    }


    //이름으로 검색하는 경우에 사용하는 메소드
    //완료
    public List<ResponseDtoPlantInfo> SearchPlantByName(String name) throws Exception {
        /*        String url = baseUrl + "/gardenList?apiKey=" + apiKey + "&sType=sCntntsSj" + "&sText=" + name;
        PlantResponse response = XmlToPlant(url);
        List<PlantResponse.Body.Plant> plantList = response.getBody().getPlantList();
        if (plantList != null && !plantList.isEmpty()) {
            return response;
        } else {
            return null;
        }*/

        List<ApiPlant> plants = apiPlantRepository.findByDistbNmContaining(name);
        List<ResponseDtoPlantInfo> plantInfos=plants.stream().map(this::convertToPlantInfo)
                .collect(Collectors.toList());
        if (plantInfos!=null && !plantInfos.isEmpty())
        return plantInfos;
        else
            return null;

    }

    //entity를 Dto으로 변환
    //제공정보 전달
    private ResponseDtoPlantdtl convertToPlantdtl(ApiPlant plant) {
        ResponseDtoPlantdtl plantdtl = new ResponseDtoPlantdtl();
        plantdtl.setCntntsNo(plant.getCntntsNo());
        plantdtl.setAdviseInfo(plant.getAdviseInfo());
        plantdtl.setClCodeNm(plant.getClCodeNm());
        plantdtl.setDistbNm(plant.getDistbNm());
        plantdtl.setDlthtsCodeNm(plant.getDlthtsCodeNm());
        plantdtl.setFmlCodeNm(plant.getFmlCodeNm());
        plantdtl.setFmldecolrCodeNm(plant.getFmldecolrCodeNm());
        plantdtl.setFmldeSeasonCodeNm(plant.getFmldeSeasonCodeNm());
        plantdtl.setFncltyInfo(plant.getFncltyInfo());
        plantdtl.setFrtlzrInfo(plant.getFrtlzrInfo());
        plantdtl.setGrowthAraInfo(plant.getGrowthAraInfo());
        plantdtl.setGrowthHgInfo(plant.getGrowthHgInfo());
        plantdtl.setGrwhstleCodeNm(plant.getGrwhstleCodeNm());
        plantdtl.setGrwhTpCodeNm(plant.getGrwhTpCodeNm());
        plantdtl.setGrwtveCodeNm(plant.getGrwtveCodeNm());
        plantdtl.setHdCodeNm(plant.getHdCodeNm());
        plantdtl.setIgnSeasonCodeNm(plant.getIgnSeasonCodeNm());
        plantdtl.setLefcolrCodeNm(plant.getLefcolrCodeNm());
        plantdtl.setLefmrkCodeNm(plant.getLefmrkCodeNm());
        plantdtl.setLefStleInfo(plant.getLefStleInfo());
        plantdtl.setLighttdemanddoCodeNm(plant.getLighttdemanddoCodeNm());
        plantdtl.setManagedemanddoCodeNm(plant.getManagedemanddoCodeNm());
        plantdtl.setManagelevelCodeNm(plant.getManagelevelCodeNm());
        plantdtl.setOrgplceInfo(plant.getOrgplceInfo());
        plantdtl.setPlntbneNm(plant.getPlntbneNm());
        plantdtl.setPlntzrNm(plant.getPlntzrNm());
        plantdtl.setRtnFileUrl(plant.getRtnFileUrl());
        return plantdtl;

    }

    //필터링시 Entity를 dto로 변환
    //식물번호 사진 유통명만 가지고 있는 리스트 생성을 위함
    public ResponseDtoPlantInfo convertToPlantInfo(ApiPlant apiplant){
        ResponseDtoPlantInfo  plant=new ResponseDtoPlantInfo();
        plant.setCntntsSj(apiplant.getDistbNm());
        plant.setCntntsNo(apiplant.getCntntsNo());
        plant.setRtnFileUrl(apiplant.getRtnFileUrl());
        plant.setFmlCodeNm(apiplant.getFmlCodeNm());
        plant.setClCodeNm(apiplant.getClCodeNm());
        return plant;
    }





    //식물 상세정보를 넘기는 메소드
    public ResponseDtoPlantdtl GetPlantDetail(String cntntsNo) throws Exception {

        //db이용으로 변경
      ApiPlant plant=apiPlantRepository.findBycntntsNo(cntntsNo);
      if(plant!=null){
          return convertToPlantdtl(plant);
      }
      else {
          return null;
      }
    }




    //API의 데이터를 db에 저장하기
    //cntnsNo를 하나씩 읽고 나서 상세정보를 받아오고 디비에 저장 반복
    public void fetchAndSaveAllPlants() throws JAXBException {
        int pageNo = 1;
        boolean moreData = true;

        while (moreData) {
            try {
                String listUrl = baseUrl + "/gardenList?apiKey=" + apiKey + "&numOfRows=1000&pageNo=" + pageNo;
                PlantResponse plantResponse = XmlToPlant(listUrl);
                List<PlantResponse.Body.Plant> plantList = plantResponse.getBody().getPlantList();
                if (plantList == null || plantList.isEmpty()) {
                    moreData = false;
                } else {
                    for (PlantResponse.Body.Plant plantData : plantList) {
                        savePlantDetail(plantData);
                    }
                    pageNo++;
                }
            } catch (JAXBException e) {
                // 예외가 발생하면 재시도 요청 메시지를 출력하고 종료
                System.out.println("데이터를 가져오는 중 오류가 발생했습니다. 재시도 해주세요.");
                e.printStackTrace();
                return;
            }
        }
    }
    //식물 cntntnsNo마다 Plant entity를 생성하기

    private void savePlantDetail(PlantResponse.Body.Plant plantData) {
        try {
            String detailUrl = baseUrl + "/gardenDtl?apiKey=" + apiKey + "&cntntsNo=" + plantData.getCntntsNo();
            PlantDetail plantDetail = toPlantDetail(detailUrl, plantData);
            PlantDetail.Body.Plantdtl item = plantDetail.getBody().getPlantdtl();
            // DTO

            ApiPlant plant = new ApiPlant();
            // Entity

            // DTO로 Entity 설정
            plant.setCntntsNo(plantData.getCntntsNo());
            plant.setDistbNm(item.getDistbNm());
            plant.setDlthtsCodeNm(item.getDlthtsCodeNm());
            plant.setClCodeNm(item.getClCodeNm());
            plant.setFmlCodeNm(item.getFmlCodeNm());
            plant.setFmldeSeasonCodeNm(item.getFmldeSeasonCodeNm());
            plant.setFncltyInfo(item.getFncltyInfo());
            plant.setFrtlzrInfo(item.getFrtlzrInfo());
            plant.setGrowthAraInfo(item.getGrowthAraInfo());
            plant.setGrowthHgInfo(item.getGrowthHgInfo());
            plant.setGrwhstleCodeNm(item.getGrwhstleCodeNm());
            plant.setGrwhTpCodeNm(item.getGrwhTpCodeNm());
            plant.setGrwtveCodeNm(item.getGrwtveCodeNm());
            plant.setHdCodeNm(item.getHdCodeNm());
            plant.setIgnSeasonCodeNm(item.getIgnSeasonCodeNm());
            plant.setLefcolrCodeNm(item.getLefcolrCodeNm());
            plant.setLefmrkCodeNm(item.getLefmrkCodeNm());
            plant.setLefStleInfo(item.getLefStleInfo());
            plant.setLighttdemanddoCodeNm(item.getLighttdemanddoCodeNm());
            plant.setManagedemanddoCodeNm(item.getManagedemanddoCodeNm());
            plant.setManagelevelCodeNm(item.getManagelevelCodeNm());
            plant.setOrgplceInfo(item.getOrgplceInfo());
            plant.setPlntbneNm(item.getPlntbneNm());
            plant.setPlntzrNm(item.getPlntzrNm());
            plant.setRtnFileUrl(truncate(plantData.getRtnFileUrl(), 7000));
            plant.setAdviseInfo(item.getAdviseInfo());
            plant.setManagedemanddoCode(item.getManagedemanddoCode());
            plant.setFlclrCodeNm(item.getFlclrCodeNm());
            plant.setWinterLwetTpCodeNm(item.getWinterLwetTpCodeNm());
            plant.setFmldecolrCodeNm(item.getFmldecolrCodeNm());

            // 과일 유무를 설정하기
            String fruitColor = item.getFmldecolrCodeNm();
            if (hasText(fruitColor)) {
                plant.setFruit("true");
            } else {
                plant.setFruit("false");
            }
            apiPlantRepository.save(plant);
            // db에 저장
        } catch (JAXBException e) {
            // 예외 발생 시 처리
            System.out.println("식물 세부 정보를 저장하는 중 오류가 발생했습니다. 재시도 해주세요.");
            e.printStackTrace();
        }
    }

    //entity 생성 끝


    //필터링시 사용하는 메소드

    public List<ResponseDtoPlantInfo> getfilterPlant(
            String managedemanddoCode, //키우기단계
            String winterLwetTpCodeNm, //최저온도
            String lighttdemanddoCodeNm, //광도
            String flclrCodeNm, //꽃색
            String ignSeasonCodeNm, //꽃피는 계절
            String fruit, //과일
            String lefmrkCodeNm, //잎무늬
            String grwhstleCodeNm //생육형태
    ) {
        // 광도변환
        if (StringUtils.hasText(lighttdemanddoCodeNm)) {
            lighttdemanddoCodeNm = mapPlaceTolight(lighttdemanddoCodeNm);
        }

        // 난이도변환
        if (StringUtils.hasText(managedemanddoCode)) {
            managedemanddoCode = mapDifficultyToDemand(managedemanddoCode);
        }

        Specification<ApiPlant> spec = Specification.where(null);

        if (StringUtils.hasText(managedemanddoCode)) {
            spec = spec.and(ApiPlantSpecification.hasmMnagedemanddoCode(managedemanddoCode));
        }
        if (StringUtils.hasText(winterLwetTpCodeNm)) {
            spec = spec.and(ApiPlantSpecification.hasWinterLwetTpCodeNm(winterLwetTpCodeNm));
        }
        if (StringUtils.hasText(lighttdemanddoCodeNm)) {
            spec = spec.and(ApiPlantSpecification.haslighttdemanddoCodeNm(lighttdemanddoCodeNm));
        }
        if (StringUtils.hasText(flclrCodeNm)) {

            spec = spec.and(ApiPlantSpecification.hasflclrCodeNm(flclrCodeNm));
        }
        if (StringUtils.hasText(ignSeasonCodeNm)) {
            spec = spec.and(ApiPlantSpecification.hasignSeasonCodeNm(ignSeasonCodeNm));
        }
        if (StringUtils.hasText(fruit)) {
            spec = spec.and(ApiPlantSpecification.hasFruit(fruit));
        }
        if (StringUtils.hasText(lefmrkCodeNm)) {
            spec = spec.and(ApiPlantSpecification.haslefmkCodeNm(lefmrkCodeNm));
        }
        if (StringUtils.hasText(grwhstleCodeNm)) {
            spec = spec.and(ApiPlantSpecification.hasgrwhstleCodeNm(grwhstleCodeNm));
        }
        List<ResponseDtoPlantInfo> collectplant = apiPlantRepository.findAll(spec)
                .stream()
                .map(this::convertToPlantInfo)
                .collect(Collectors.toList());
        return collectplant;
    }



}