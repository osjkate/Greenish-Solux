package com.solux.greenish.search.contoller;
import com.solux.greenish.search.dto.PlantResponse;
import com.solux.greenish.search.dto.ResponseDtoPlantInfo;
import com.solux.greenish.search.dto.ResponseDtoPlantdtl;

import com.solux.greenish.search.service.PlantSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/plantsearch")
@RestController
public class PlantSearchController {


    private final PlantSearchService plantService;

    @Autowired
    public PlantSearchController(PlantSearchService plantService) {
        this.plantService = plantService;


    }


    //완료...!!!
    // 이름으로만 찾기
    @GetMapping
    public ResponseEntity<?> searchPlant(@RequestParam(required = false) String name) {
        try {
            // 이름이 null아니고, 문자열이 공백이 아니고, 글자 크기 한개이상
            if (StringUtils.hasText(name)) {
                List<ResponseDtoPlantInfo> plantInfos = plantService.SearchPlantByName(name);
                Map<String, Object> requestResponse = new HashMap<>();
                // 검색시 결과가 있는 경우
                if (plantInfos != null) {
                    requestResponse.put("plants", plantInfos);
                    return new ResponseEntity<>(requestResponse, HttpStatus.OK);
                } else {
                    // 검색시 결과가 없는 경우
                    requestResponse.put("msg", "해당 식물을 찾을 수 없습니다");
                    return new ResponseEntity<>(requestResponse, HttpStatus.NOT_FOUND);
                }
            } else {
                // 초기화면에서의 데이터 전달
                Map<String, List<PlantResponse.Body.Plant>> initialPlants = plantService.getInitialPlants();
                return new ResponseEntity<>(initialPlants, HttpStatus.OK);
            }
        } catch (Exception e) {
            // 예외 처리
            Map<String, Object> response = new HashMap<>();
            response.put("msg", "검색 중 오류가 발생했습니다. 다시 시도해주세요.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 상세페이지
    @GetMapping("/plant/{cntntsNo}")
    public ResponseEntity<?> getPlantDetail(@PathVariable String cntntsNo) {
        try {
            ResponseDtoPlantdtl plantdtl = plantService.GetPlantDetail(cntntsNo);
            if (plantdtl != null) {
                return new ResponseEntity<>(plantdtl, HttpStatus.OK);
            } else {
                Map<String, Object> requestResponse = new HashMap<>();
                requestResponse.put("msg", "해당 식물 정보를 찾을 수 없습니다. 다시 시도해주세요.");
                return new ResponseEntity<>(requestResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // 예외 처리
            Map<String, Object> response = new HashMap<>();
            response.put("msg", "상세 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해주세요.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 필터링
    @GetMapping("/filter")
    public ResponseEntity<Map<String, Object>> searchPlantByFilter(
            @RequestParam(required = false) String managedemanddoCode, // 키우기 단계
            @RequestParam(required = false) String lighttdemanddoCodeNm, // 광도(배치장소)
            @RequestParam(required = false) String flclrCodeNm, // 꽃 컬러
            @RequestParam(required = false) String ignSeasonCodeNm, // 꽃 피는 계절
            @RequestParam(required = false) String fruit, // 열매 유무
            @RequestParam(required = false) String lefmrkCodeNm, // 잎무늬
            @RequestParam(required = false) String grwhstleCodeNm, // 생육 형태
            @RequestParam(required = false) String winterLwetTpCodeNm // 최저 온도
    ) {
        try {

            List<ResponseDtoPlantInfo> plants = plantService.getfilterPlant(
                    managedemanddoCode, // 키우기 단계
                    winterLwetTpCodeNm, // 최저 온도
                    lighttdemanddoCodeNm, // 광도(배치장소)
                    flclrCodeNm, // 꽃 컬러
                    ignSeasonCodeNm, // 꽃 피는 계절
                    fruit, // 열매 유무
                    lefmrkCodeNm, // 잎무늬
                    grwhstleCodeNm // 생육 형태
            );

            Map<String, Object> requestResponse = new HashMap<>();
            if (plants.isEmpty()) {
                requestResponse.put("msg", "해당하는 식물이 없습니다.");
                return new ResponseEntity<>(requestResponse, HttpStatus.NOT_FOUND);
            } else {
                requestResponse.put("plants", plants);
                return new ResponseEntity<>(requestResponse, HttpStatus.OK);
            }
        } catch (Exception e) {
            // 예외 처리
            Map<String, Object> response = new HashMap<>();
            response.put("msg", "필터링 진행 중 오류가 발생했습니다. 다시 시도해주세요.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



