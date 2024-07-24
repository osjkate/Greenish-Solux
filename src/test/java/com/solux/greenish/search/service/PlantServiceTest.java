//package com.solux.greenish.search.service;
//
//import com.solux.greenish.entity.ApiPlant;
//import com.solux.greenish.search.repository.ApiPlantRepository;
//import com.solux.greenish.dto.ResponseDtoPlantInfo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class PlantServiceTest {
//
//    @InjectMocks
//    private PlantService plantService;
//
//    @Mock
//    private ApiPlantRepository apiPlantRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetFilterPlant() {
//        // given
//        String managedemanddoCode = null;
//        String lighttdemanddoCodeNm = null;
//        String flclrCodeNm = null;
//        String ignSeasonCodeNm = null;
//        String fruit = "true";
//        String lefmrkCodeNm = null;
//        String grwhstleCodeNm = null;
//        String winterLwetTpCodeNm = "10";
//
//        List<ApiPlant> mockPlants = new ArrayList<>();
//        ApiPlant plant = new ApiPlant();
//        plant.setCntntsNo("12345");
//        plant.setDistbNm("Test Plant");
//        plant.setRtnFileUrl("http://example.com/image.jpg");
//        plant.setFruit("false");
//        plant.setWinterLwetTpCodeNm("10");
//        mockPlants.add(plant);
//
//        when(apiPlantRepository.findAll(any(Specification.class))).thenReturn(mockPlants);
//
//        // when
//        List<ResponseDtoPlantInfo> result = plantService.getfilterPlant(
//                managedemanddoCode,
//                winterLwetTpCodeNm,
//                lighttdemanddoCodeNm,
//                flclrCodeNm,
//                ignSeasonCodeNm,
//                fruit,
//                lefmrkCodeNm,
//                grwhstleCodeNm
//        );
//
//        // then
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        assertEquals("12345", result.get(0).getCntntsNo());
//        assertEquals("Test Plant", result.get(0).getCntntsSj());
//        assertEquals("http://example.com/image.jpg", result.get(0).getRtnFileUrl());
//    }
//}
