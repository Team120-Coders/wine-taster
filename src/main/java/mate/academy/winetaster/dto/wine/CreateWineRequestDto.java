package mate.academy.winetaster.dto.wine;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CreateWineRequestDto {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String producer;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String grapeVariety;

    @Min(value = 1900)
    private int vintage;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal alcoholContent;

    private String description;

    private String imageUrl;

    private Set<Long> categoryIds;
}
