package com.solux.greenish.search.contoller;
import com.solux.greenish.dto.PlantDetail;
import com.solux.greenish.dto.PlantResponse;
import com.solux.greenish.entity.Plant;
import com.solux.greenish.search.service.PlantService;

import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/plantsearch")
@RestController
public class PlantSearchController {


    private final PlantService plantService;

    @Autowired
    public PlantSearchController(PlantService plantService) {
        this.plantService = plantService;

    }



    //이름으로만 찾기
    //Json으로 이름,사진,식물번호 전달
    @GetMapping
    public ResponseEntity<?> searchPlant(@RequestParam(required = false) String name) throws Exception {
        //이름 검색
        if(name!=null &&!name.isEmpty()){
        PlantResponse plantResponse= plantService.SearchPlantByName(name);
        Map<String, Object> requestResponse = new HashMap<>();
        //검색시 결과가 있는 경우

            if ( plantResponse!= null) {
                List<PlantResponse.Body.Plant> plantList = plantResponse.getBody().getPlantList();
                requestResponse.put("plants",plantList);
            return new ResponseEntity<>(requestResponse, HttpStatus.OK);
        }
        //검색시 결과가 없는 경우
            else{

            requestResponse.put("msg","해당 식물을 찾을 수 없습니다");
            return new ResponseEntity<>(requestResponse, HttpStatus.NOT_FOUND);
        }
        }

        //초기화면에서의 데이터 전달
        else {
            Map<String, List<PlantResponse.Body.Plant>> initialPlants =  plantService.getInitialPlants();
            return new ResponseEntity<>(initialPlants,HttpStatus.OK);
        }
    }


    //상세페이지
    @GetMapping("/plant/{cntntsNo}")
    public ResponseEntity<?> getPlantDetail(@PathVariable String cntntsNo
    ) throws Exception {
        PlantDetail.Body.Plantdtl plantdtl=plantService.GetPlantDetail(cntntsNo);
        if (plantdtl!=null){
        return new ResponseEntity<>(plantdtl,HttpStatus.OK);
        }
        else{
            Map<String, Object> requestResponse = new HashMap<>();
            requestResponse.put("msg","해당 식물 정보를 찾을 수 없습니다. 다시 시도해주세요.");
            return new ResponseEntity<>(requestResponse,HttpStatus.NOT_FOUND);
        }
    }

    //필터링
    @GetMapping("/filter")
    public ResponseEntity<Map<String,Object>> searchPlantByFilter(
            @RequestParam(required = false)  String  managedemanddoCode, //키우기단계
            @RequestParam(required = false)  String  lighttdemanddoCodeNm, //광도(배치장소)
            @RequestParam(required = false)  String  flclrCodeNm, //꽃컬러
            @RequestParam(required = false)  String ignSeasonCodeNm, //꽃피는 계절
            @RequestParam(required = false)  String  fruit, //열매유무
            @RequestParam(required = false)  String lefmrkCodeNm, //잎무늬
            @RequestParam(required = false)  String grwhstleCodeNm //생육형태

    ) throws JAXBException {

        List<Plant> plants = plantService.getfilterPlant(managedemanddoCode, //키우기단계
                lighttdemanddoCodeNm, //광도(배치장소)
                flclrCodeNm, //꽃컬러
                ignSeasonCodeNm, //꽃피는 계절
                fruit, //열매유무
                lefmrkCodeNm, //잎무늬
                grwhstleCodeNm); //생육형태)
        Map<String, Object> requestResponse = new HashMap<>();
        if (plants.isEmpty()) {
            requestResponse.put("status", "404 NOT Found");
            requestResponse.put("msg", "해당하는 식물이 없습니다.");
            return new ResponseEntity<>(requestResponse, HttpStatus.NOT_FOUND);
        } else {
            requestResponse.put("status", "202 OK");
            requestResponse.put("plants", plants);
            return new ResponseEntity<>(requestResponse, HttpStatus.OK);

        }


    }
}



