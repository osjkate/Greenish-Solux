package com.solux.greenish.search.service;

import com.solux.greenish.dto.PlantDetail;
import com.solux.greenish.dto.PlantResponse;
import com.solux.greenish.entity.Plant;

import com.solux.greenish.search.repository.ApiPlantRepository;
import com.solux.greenish.search.specification.ApiPlantSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import static io.micrometer.common.util.StringUtils.truncate;
import static java.util.Arrays.stream;

@Service
public class PlantService {
    @Value("${nongsaro.api.key}")
    private String apiKey;
    @Value("${nongsaro.api.url}")
    private String baseUrl;

    private  final ApiPlantRepository apiPlantRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public PlantService(RestTemplate restTemplate,ApiPlantRepository apiPlantRepository) {
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
            case "1":
                return "낮음";
            case "2":
                return "보통";
            case "3":
                return "필요함";
            default:
                return null;
        }
    }

    //광도 변화
    private String mapPlaceTolight(String place) {
        switch (place) {
            case "욕실" : case "사무실 책상":
                return "낮은 광도";
            case "베란다": case"밝은 사무실":
                return "중간 광도";
            case "정원": case "테라스":
                return "높은 광도";
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
    private PlantDetail toPlantDetail(String url,PlantResponse.Body.Plant plantData) throws JAXBException{
        String apiResponse = restTemplate.getForObject(url, String.class);
        JAXBContext jaxbContext = JAXBContext.newInstance(PlantDetail.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        PlantDetail plantDetail = (PlantDetail) jaxbUnmarshaller.unmarshal(new StringReader(apiResponse));
        //사진 값 하나만 저장함
        //유통명 null값인 경우 검색한 값 넣어줌
        if (plantDetail.getBody().getPlantdtl().getDistbNm().isEmpty()){
            plantDetail.getBody().getPlantdtl().setDistbNm(plantData.getCntntsNo());
        }
        plantDetail.getBody().getPlantdtl().setRtnFileUrl(plantData.getRtnFileUrl());
        return plantDetail;
    }

    //첫화면에서 식물 정보를 넘기는 메소드
    //완료
    public Map<String, List<PlantResponse.Body.Plant>> getInitialPlants(){
        //추운 실내 추천(0도) : 가울테리아, 금식나무, 무늬석창포, 자금우
        List<String> coldPlantsName = new ArrayList<>(Arrays.asList("가울테리아", "금식나무", "무늬석창포", "자금우"));
        //따뜻한 실내 추천(15도 이상) : 히포에스테스, 해마리아, 털달개비, 듀란타
        List<String> warmPlantsName = new ArrayList<>(Arrays.asList("히포에스테스", "해마리아", "털달개비", "듀란타"));
        //따뜻한 식물리스트
        List<PlantResponse.Body.Plant> coldPlants = coldPlantsName.stream()
                .map(name -> {
                    try {
                        return SearchPlantByName(name).getBody().getPlantList();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
        //차가운 식물리스트
        List<PlantResponse.Body.Plant> warmPlants = warmPlantsName.stream()
                .map(name -> {
                    try {
                        return SearchPlantByName(name).getBody().getPlantList();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());

        //반환
        Map<String, List<PlantResponse.Body.Plant>> initialPlants = new HashMap<>();
        initialPlants.put("coldplants", coldPlants);
        initialPlants.put("warmplants", warmPlants);
        return initialPlants;


        /*걍 db에서 꺼내는 게 나은가...?생각해보기*/
    }


    //이름으로 검색하는 경우에 사용하는 메소드
    //완료
    public PlantResponse SearchPlantByName(String name) throws Exception {
        String url = baseUrl + "/gardenList?apiKey=" + apiKey + "&sType=sCntntsSj" + "&sText=" + name;
        PlantResponse response = XmlToPlant(url);
        List<PlantResponse.Body.Plant> plantList = response.getBody().getPlantList();
        if (plantList != null && !plantList.isEmpty()) {
            return response;
        } else {
            System.out.println("It is null!");
            return null;
        }
    }

    //entity를 plantdtl으로 변환
    private PlantDetail.Body.Plantdtl convertToPlantdtl(Plant plant) {
        PlantDetail.Body.Plantdtl plantdtl = new PlantDetail.Body.Plantdtl();
        plantdtl.setDistbNm(plant.getDistbNm());
        plantdtl.setDlthtsCodeNm(plant.getDlthtsCodeNm());
        plantdtl.setClCodeNm(plant.getClCodeNm());
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
        plantdtl.setPostngplaceCodeNm(plant.getPostngplaceCodeNm());
        plantdtl.setRtnFileUrl(plant.getRtnFileUrl());
        return plantdtl;
    }


    //식물 상세정보를 넘기는 메소드
    public PlantDetail.Body.Plantdtl GetPlantDetail(String cntntsNo) throws Exception {
       /* String url = baseUrl + "/gardenDtl?apiKey=" + apiKey + "&cntntsNo=" + cntntsNo;
        PlantDetail plantDetail = toPlantDetail(url,cntntsSj);
        PlantDetail.Body.Plantdtl plantdtl = plantDetail.getBody().getPlantdtl();
        if (plantdtl!=null){
            return plantdtl;
        }
        else {
            return  null;
        }*/

        //db이용
      Plant plant=apiPlantRepository.findBycntntsNo(cntntsNo);
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
            String listUrl = baseUrl + "/gardenList?apiKey=" + apiKey + "&numOfRows=1000&pageNo=" + pageNo;
            PlantResponse plantResponse=XmlToPlant(listUrl);
            List<PlantResponse.Body.Plant> plantList = plantResponse.getBody().getPlantList();
            if (plantList == null || plantList.isEmpty()) {
                moreData = false;
            } else {
                for (PlantResponse.Body.Plant plantData : plantList) {
/*
                    savePlantDetail(plantData.getCntntsNo());
*/
                    savePlantDetail(plantData);
                }
                pageNo++;
            }
        }
    }
    //식물 cntntnsNo마다 Plant entity를 생성하기

    private void savePlantDetail(PlantResponse.Body.Plant plantData) throws JAXBException {
        String detailUrl = baseUrl + "/gardenDtl?apiKey=" + apiKey + "&cntntsNo=" + plantData.getCntntsNo();
        PlantDetail plantDetail = toPlantDetail(detailUrl,plantData);
        PlantDetail.Body.Plantdtl item= plantDetail.getBody().getPlantdtl();
        //DTO

        Plant plant = new Plant();
        //Entity

        //DTO로 Entity 설정
        plant.setCntntsNo(plantData.getCntntsNo());
        plant.setDistbNm(item.getDistbNm());
        plant.setDlthtsCodeNm(item.getDlthtsCodeNm());
        plant.setClCodeNm(item.getClCodeNm());
        plant.setFmlCodeNm(item.getFmlCodeNm());
        plant.setFmldeSeasonCodeNm(item.getFmldeSeasonCodeNm());
        plant.setFncltyInfo(truncate(item.getFncltyInfo(), 255));
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
        plant.setPostngplaceCodeNm(item.getPostngplaceCodeNm());
        plant.setRtnFileUrl(truncate(plantData.getRtnFileUrl(),7000));

        //과일 유무를 설정하기
        String fruitColor = item.getFmldecolrCodeNm();
        if(fruitColor != null && !fruitColor.isEmpty()){
        plant.setFruit("true");
        }
        else{        plant.setFruit("false");
        }
        apiPlantRepository.save(plant);
        //db에 저장
    }
    //entity 생성 끝


    //필터링시 사용하는 메소드
    //필터링
    public List<Plant> getfilterPlant(
            String managedemanddoCode, //키우기단계
            String lighttdemanddoCodeNm, //광도(배치장소)
            String flclrCodeNm, //꽃컬러
            String ignSeasonCodeNm, //꽃피는 계절
            String fruit, //열매유무
            String lefmrkCodeNm, //잎무늬
            String grwhstleCodeNm //생육형태

    ){


        //광도변환
        if (lighttdemanddoCodeNm != null) {
            lighttdemanddoCodeNm = mapPlaceTolight(lighttdemanddoCodeNm);
        }
        //난이도변환
        if (managedemanddoCode != null) {

            managedemanddoCode = mapDifficultyToDemand(managedemanddoCode);
        }


        //필터링에 해당하는 거 읽어오기
        Specification<Plant> spec = Specification.where(ApiPlantSpecification.hasmMnagedemanddoCode(managedemanddoCode)) //키우기단계
                .and(ApiPlantSpecification.haslighttdemanddoCodeNm(lighttdemanddoCodeNm)) //광도
                .and(ApiPlantSpecification.hasflclrCodeNm(flclrCodeNm)) //꽃 컬러
                .and(ApiPlantSpecification.hasFruit(fruit)) //과일
                .and(ApiPlantSpecification.hasgrwhstleCodeNm(grwhstleCodeNm)) //생육
                .and(ApiPlantSpecification.haslefmkCodeNm(lefmrkCodeNm)) //잎무늬
                .and(ApiPlantSpecification.hasignSeasonCodeNm(ignSeasonCodeNm));
                
        return apiPlantRepository.findAll(spec);

    }


}