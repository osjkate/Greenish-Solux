package com.solux.greenish.search.repository;

import com.solux.greenish.search.entity.ApiPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiPlantRepository extends JpaRepository<ApiPlant, String>, JpaSpecificationExecutor<ApiPlant> {
    List<ApiPlant> findByDistbNmContaining(String distbNm);
    ApiPlant findBycntntsNo(String cntnsNo);
    ApiPlant findByDistbNm(String distbNm);
}
