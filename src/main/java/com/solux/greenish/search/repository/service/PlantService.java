
package com.solux.greenish.search.repository.service;

import com.solux.greenish.dto.PlantDetail;
import com.solux.greenish.dto.PlantResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.util.List;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class PlantService {
    @Value("${nongsaro.api.key}")
    private String apiKey;
    @Value("${nongsaro.api.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public PlantService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //api를 객체로 받기
    //필터링과 이름 검색시 이용
    public PlantResponse XmlToPlant(String url) throws JAXBException {
        String apiResponse = restTemplate.getForObject(url, String.class);
        JAXBContext jaxbContext = JAXBContext.newInstance(PlantResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        PlantResponse response = (PlantResponse) jaxbUnmarshaller.unmarshal(new StringReader(apiResponse));
        System.out.println(response);
        return response;
    }

    //식물의 세부 정보를 불러오는 메소드
    public PlantDetail toPlantDetail(String url) throws JAXBException{
        String apiResponse = restTemplate.getForObject(url, String.class);
        JAXBContext jaxbContext = JAXBContext.newInstance(PlantDetail.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        PlantDetail plantDetail = (PlantDetail) jaxbUnmarshaller.unmarshal(new StringReader(apiResponse));
        return plantDetail;
    }



    //이름으로 검색하는 경우에 사용하는 메소드
    public PlantResponse.Body.Plant SearchPlantByName(String name) throws Exception {
        String url = baseUrl + "/gardenList?apiKey=" + apiKey + "&sType=sCntntsSj" + "&sText=" + name;
        PlantResponse response = XmlToPlant(url);
        List<PlantResponse.Body.Plant> plantList = response.getBody().getPlantList();
        if (plantList != null && !plantList.isEmpty()) {
            for (PlantResponse.Body.Plant plant : plantList) {
                System.out.println(plant.getCntntsNo());
            }
            return plantList.get(0);
        } else {
            return null;
        }
    }
    
    //식물 상세정보를 넘기는 메소드
    public PlantDetail.Body.Plantdtl GetPlantDetail(String cntntsNo) throws Exception {
        String url = baseUrl + "/gardenDtl?apiKey=" + apiKey + "&cntntsNo=" + cntntsNo;
        PlantDetail plantDetail = toPlantDetail(url);
        PlantDetail.Body.Plantdtl plantdtl = plantDetail.getBody().getPlantdtl();
        if (plantdtl!=null){
            return plantdtl;
        }
        else {
            return  null;
        }
    }
}