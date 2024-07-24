package com.solux.greenish.search.specification;

import com.solux.greenish.search.entity.ApiPlant;
import org.springframework.data.jpa.domain.Specification;

public class ApiPlantSpecification {
    public static Specification<ApiPlant> hasmMnagedemanddoCode(String managedemanddoCode) {
        return (root, query, criteriaBuilder) -> managedemanddoCode == null ? null : criteriaBuilder.like(root.get("managedemanddoCodeNm"), "%" + managedemanddoCode + "%");
    }

    public static Specification<ApiPlant> hasWinterLwetTpCodeNm(String winterLwetTpCodeNm) {
        return (root, query, criteriaBuilder) -> winterLwetTpCodeNm == null ? null : criteriaBuilder.like(root.get("winterLwetTpCodeNm"), "%" + winterLwetTpCodeNm + "%");
    }

    public static Specification<ApiPlant> haslighttdemanddoCodeNm(String lighttdemanddoCodeNm) {
        return (root, query, criteriaBuilder) -> lighttdemanddoCodeNm == null ? null : criteriaBuilder.like(root.get("lighttdemanddoCodeNm"), "%" + lighttdemanddoCodeNm + "%");
    }

    public static Specification<ApiPlant> hasflclrCodeNm(String flclrCodeNm) {
        return (root, query, criteriaBuilder) -> flclrCodeNm == null ? null : criteriaBuilder.like(root.get("flclrCodeNm"), "%" + flclrCodeNm + "%");
    }

    public static Specification<ApiPlant> haslefmkCodeNm(String lefmrkCodeNm) {
        return (root, query, criteriaBuilder) -> lefmrkCodeNm == null ? null : criteriaBuilder.like(root.get("lefmrkCodeNm"), "%" + lefmrkCodeNm + "%");
    }

    public static Specification<ApiPlant> hasgrwhstleCodeNm(String grwhstleCodeNm) {
        return (root, query, criteriaBuilder) -> grwhstleCodeNm == null ? null : criteriaBuilder.like(root.get("grwhstleCodeNm"), "%" + grwhstleCodeNm + "%");
    }

    public static Specification<ApiPlant> hasFruit(String fruit) {
        return (root, query, criteriaBuilder) -> fruit == null ? null : criteriaBuilder.equal(root.get("fruit"), fruit);
    }

    public static Specification<ApiPlant> hasignSeasonCodeNm(String ignSeasonCodeNm) {
        return (root, query, criteriaBuilder) -> ignSeasonCodeNm == null ? null : criteriaBuilder.like(root.get("ignSeasonCodeNm"), "%" + ignSeasonCodeNm + "%");
    }
}
