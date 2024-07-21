package com.solux.greenish.search.repository;

import com.solux.greenish.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiPlantRepository extends JpaRepository<Plant, String>, JpaSpecificationExecutor<Plant> {
    List<Plant> findByDistbNmIn(List<String> distbNms);
    Plant findBycntntsNo(String cntnsNo);

}
