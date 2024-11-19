package mate.academy.winetaster.repository.wine.spec;

import java.util.Arrays;
import mate.academy.winetaster.model.Wine;
import mate.academy.winetaster.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Wine> {
    @Override
    public String getKey() {
        return "name";
    }

    public Specification<Wine> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get("name")
                .in(Arrays.stream(params).toArray());
    }
}
