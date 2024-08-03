package com.solux.greenish.search.entity;

import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.watercycle.WaterCycle;
import jakarta.persistence.*;
import lombok.Data;

//데이터베이스와 맵핑되는 객체
@Data
@Entity
@Table(name="api_plant")
public class ApiPlant {
    @Id
    private String cntntsNo;

    private String adviseInfo;
    private String clCodeNm;
    private String distbNm;
    private String dlthtsCodeNm;
    private String fmlCodeNm;
    private String fmldecolrCodeNm;
    private String fmldeSeasonCodeNm;

    @Column(columnDefinition = "LONGTEXT")
    private String fncltyInfo;

    private String frtlzrInfo;
    private String growthAraInfo;
    private String growthHgInfo;
    private String grwhstleCodeNm;
    private String grwhTpCodeNm;
    private String grwtveCodeNm;
    private String hdCodeNm;
    private String ignSeasonCodeNm;
    private String lefcolrCodeNm;
    private String lefmrkCodeNm;
    private String lefStleInfo;
    private String lighttdemanddoCodeNm;
    private String managedemanddoCodeNm;
    private String managelevelCodeNm;
    private String orgplceInfo;
    private String plntbneNm;
    private String plntzrNm;
    private String rtnFileUrl;//사진
    //제공정보끝

    //필터링 위한 정보
    private String  fruit;
    private String  managedemanddoCode; //키우기단계
    private String  flclrCodeNm; //꽃컬러
    private String winterLwetTpCodeNm; //최저온도

    @ManyToOne
    @JoinColumn(name = "water_cycle_id")
    private WaterCycle waterCycle;
}
