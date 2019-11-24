package com.takeIt.specification;

import com.takeIt.entity.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class GiftSpecification implements Specification<Gift> {
    private SearchCriteria criteria;

    public GiftSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Gift> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                System.out.println(criteria.getKey() + " - " + criteria.getValue());
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        } else if (criteria.getOperation().equalsIgnoreCase("join")) {
            Join<Gift, City> giftCityJoin = root.join("city");
            Join<Gift, District> giftDistrictJoin = root.join("district");
            Join<Gift, Category> giftCategoryJoin = root.join("category");
            Predicate predicate = builder.or(
                    builder.like(giftCityJoin.get("name"), "%" + criteria.getValue() + "%"),
                    builder.like(giftDistrictJoin.get("name"), "%" + criteria.getValue() + "%"),
                    builder.like(giftCategoryJoin.get("name"), "%" + criteria.getValue() + "%"));
            return predicate;
        }
        return null;
    }
}
