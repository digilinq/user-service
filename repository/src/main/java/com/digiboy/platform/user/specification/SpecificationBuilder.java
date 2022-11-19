package com.digiboy.platform.user.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SpecificationBuilder {
    public static <T> Specification<T> build(Specification<T> specification) {
        return Specification.where(specification);
    }

    public static <T> Specification<T> build(Specification<T>... specifications) {
        return build(List.of(specifications));
    }

    public static <T> Specification<T> build(List<Specification<T>> specifications) {
        Specification<T> specification = Specification.where(null);
        for (Specification<T> tSpecification : specifications) {
            specification = specification.and(tSpecification);
        }
        return specification;
    }
}
