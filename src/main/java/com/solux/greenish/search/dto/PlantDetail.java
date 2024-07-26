package com.solux.greenish.search.dto;

import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "response")
public class PlantDetail {
    @XmlElement(name = "header")
    private Header header;
    @XmlElement(name = "body")
    private Body body;
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Header{
        private String resultCode;
        private String resultMsg;

    }
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Body{
        @XmlElement(name="item")
        private Plantdtl plantdtl;
        @Data
        public static class Plantdtl{
            private String cntntsNo;
            private String adviseInfo;
            private String clCodeNm;
            private String distbNm;
            private String dlthtsCodeNm;
            private String fmlCodeNm;
            private String fmldecolrCodeNm;
            private String fmldeSeasonCodeNm;
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
            private String fruit;
            private String  managedemanddoCode; //키우기단계
            private String  flclrCodeNm; //꽃컬러
            private String winterLwetTpCodeNm; //최저온도


        }

    }


}
