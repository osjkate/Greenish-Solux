package com.solux.greenish.search.specification;

import com.solux.greenish.entity.Plant;
import org.springframework.data.jpa.domain.Specification;

public class ApiPlantSpecification {/*
    String  flclrCodeNm, //꽃컬러
    String ignSeasonCodeNm,
    String  fruit, //열매유무
    String lefmrkCodeNm, //잎무늬
    String grwhstleCodeNm //생육형태*/

    //null이면 null이고 아니면 쿼리에 추가해주기.
    public static Specification<Plant> hasmMnagedemanddoCode(String managedemanddoCode){
        return (root,query,criteriaBuilder)-> managedemanddoCode==null ? null:criteriaBuilder.like(root.get("managedemanddoCodeNm"),"%"+managedemanddoCode+"%");
    }

    public static Specification<Plant> haslighttdemanddoCodeNm(String lighttdemanddoCodeNm){
        return (root,query,criteriaBuilder)-> lighttdemanddoCodeNm==null?  null:criteriaBuilder.like(root.get("lighttdemanddoCodeNm"),"%"+lighttdemanddoCodeNm+"%");
    }
    public  static Specification<Plant> hasflclrCodeNm( String  flclrCodeNm ){
        return (root,query,crteriaBuilder)->flclrCodeNm==null? null:crteriaBuilder.like(root.get("flclrCodeNm"),"%"+flclrCodeNm+"%");
    }
    public  static Specification<Plant> haslefmkCodeNm( String  lefmkCodeNm ){
        return (root,query,crteriaBuilder)->lefmkCodeNm==null? null:crteriaBuilder.like(root.get("lefmkCodeNm"),"%"+lefmkCodeNm+"%");
    }
    public  static Specification<Plant> hasgrwhstleCodeNm( String  grwhstleCodeNm ){
        return (root,query,crteriaBuilder)->grwhstleCodeNm==null? null:crteriaBuilder.like(root.get("grwhstleCodeNm"),"%"+grwhstleCodeNm+"%");
    }
    public static Specification<Plant> hasFruit(String fruit) {
        return (root,query,crteriaBuilder)->fruit==null? null:crteriaBuilder.equal(root.get("fruit"),fruit);
    }

    public static Specification<Plant> hasignSeasonCodeNm(String ignSeasonCodeNm) {
        return (root, query, criteriaBuilder) -> ignSeasonCodeNm==null? null:criteriaBuilder.like(root.get("ignSeasonCodeNm"),"%s"+ignSeasonCodeNm+"%s");
    }
}
