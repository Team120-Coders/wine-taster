package mate.academy.winetaster.repository.wine;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.winetaster.model.Wine;
import mate.academy.winetaster.repository.SpecificationProvider;
import mate.academy.winetaster.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WineSpecificationProviderManager implements SpecificationProviderManager<Wine> {
    private final List<SpecificationProvider<Wine>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Wine> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find correct "
                        + "specification provider for key " + key));
    }
}
