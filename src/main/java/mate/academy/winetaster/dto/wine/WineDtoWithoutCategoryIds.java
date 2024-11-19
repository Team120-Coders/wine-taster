package mate.academy.winetaster.dto.wine;

import java.math.BigDecimal;

public record WineDtoWithoutCategoryIds(
        Long id,
        String name,
        String producer,
        String region,
        String grapeVariety,
        int vintage,
        BigDecimal price,
        BigDecimal alcoholContent,
        String description,
        String imageUrl
) {
}
