package mate.academy.winetaster.repository.wine.spec;

import java.util.Arrays;
import mate.academy.winetaster.model.Wine;
import mate.academy.winetaster.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Wine> {
    @Override
    public String getKey() {
        return "producer";
    }

    public Specification<Wine> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get("producer")
                .in(Arrays.stream(params).toArray());
    }
}
