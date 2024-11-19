package mate.academy.winetaster.dto.wine;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class WineResponseDto {
    private Long id;
    private String name;
    private String producer;
    private String region;
    private String grapeVariety;
    private int vintage;
    private BigDecimal price;
    private BigDecimal alcoholContent;
    private String description;
    private String imageUrl;
    private Set<Long> categoryIds;
}
