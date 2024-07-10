package com.solux.greenish.dto;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;
//XML을 JAXB를 사용하여 맵핑하기
@XmlAccessorType(XmlAccessType.FIELD)//XML데이터의 맵핑 방식
@Data
@XmlRootElement(name = "response") //해당 클래스가 특정 노드의 루트
//response는 plantResponse의 루트
public class PlantResponse {
    //맵핑을 위한 객체
    @XmlElement(name = "header")
    private Header header;
    @XmlElement(name = "body")
    private Body body;
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)

    public static class Header {
        private String resultCode;
        private String resultMsg;

    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    public static class Body {
        //xml을 리스트로 감싼다.
      
        @XmlElementWrapper(name = "items")
        @XmlElement(name ="item")
        private List<Plant> PlantList;
            @Data
            public static class Plant {
                private String cntntsNo;
                private String cntntsSj;
                private String rtnFileUrl;




}}}
