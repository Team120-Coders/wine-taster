package mate.academy.winetaster.repository;

import mate.academy.winetaster.dto.wine.WineSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(WineSearchParameters searchParameters);
}
