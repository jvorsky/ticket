package org.hillel.persistence.jpa.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

public interface SpecificationCreator<T> {

    Specification<T> getSpecification(@Nullable String filterValue);

}
