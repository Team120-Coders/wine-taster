package mate.academy.winetaster.repository.wine;

import lombok.RequiredArgsConstructor;
import mate.academy.winetaster.dto.wine.WineSearchParameters;
import mate.academy.winetaster.model.Wine;
import mate.academy.winetaster.repository.SpecificationBuilder;
import mate.academy.winetaster.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WineSpecificationBuilder implements SpecificationBuilder<Wine> {
    private final SpecificationProviderManager<Wine> bookSpecificationProviderManager;

    @Override
    public Specification<Wine> build(WineSearchParameters searchParameters) {
        Specification<Wine> spec = Specification.where(null);
        spec = addSpecifications(spec, "name", searchParameters.names());
        spec = addSpecifications(spec, "producer", searchParameters.producers());
        return spec;
    }

    private Specification<Wine> addSpecifications(Specification<Wine> spec,
                                                  String field, String[] values) {
        if (values != null && values.length > 0) {
            return spec.and(bookSpecificationProviderManager.getSpecificationProvider(field)
                    .getSpecification(values));
        }
        return spec;
    }
}
