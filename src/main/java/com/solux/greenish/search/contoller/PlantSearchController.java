package com.solux.greenish.search.contoller;
import com.solux.greenish.dto.PlantDetail;
import com.solux.greenish.dto.PlantResponse;
import com.solux.greenish.search.repository.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/plantsearch")
@RestController
public class PlantSearchController {

    private final PlantService plantService;

    public PlantSearchController(PlantService plantService) {
        this.plantService = plantService;
    }


    //이름으로만 찾기
    //Json으로 이름,사진,식물번호 전달
    @GetMapping
    public ResponseEntity<Map<String,Object>> searchPlant(@RequestParam("name") String name) throws Exception {
        PlantResponse.Body.Plant plant = plantService.SearchPlantByName(name);
        //검색시 결과가 있는 경우
        if (plant != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("cntntsNo",plant.getCntntsNo());
            response.put("cntntsSj", plant.getCntntsSj());
            response.put("rtnFileUrl", plant.getRtnFileUrl());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        //검색시 결과가 없는 경우
        else{
            Map<String, Object> response = new HashMap<>();
            response.put("msg","해당 식물을 찾을 수 없습니다");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //상세페이지
    @GetMapping("/plant/{cntntsNo}")
    public ResponseEntity<PlantDetail.Body.Plantdtl> getPlantDetail(@PathVariable String cntntsNo) throws Exception {
        PlantDetail.Body.Plantdtl plantdtl=plantService.GetPlantDetail(cntntsNo);
        if (plantdtl!=null){
        return new ResponseEntity<>(plantdtl,HttpStatus.OK);
        }
        else{
            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
        }
    }
    //필터링
    /*Get방식이 캐싱을 사용할 수 있으므로 get으로 구현*/
    @GetMapping("/filter")

    public List<PlantResponse.Body.Plant> searchPlantByFilter(@RequestParam  String flclrCodeNm,
                                                              @RequestParam String growthHgInfo,
                                                              @RequestParam String  grwhTpCodeNm,
                                                              @RequestParam String grwtveCodeNm,
                                                              @RequestParam String hdCodeNm,
                                                              @RequestParam String managelevelCodeNm,
                                                              @RequestParam String postngplaceCodeNm

    ){
        //구현중~
        return null;
    }


    }


