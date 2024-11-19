package mate.academy.winetaster.dto.orderitem;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {
    private Long id;
    private Long wineId;
    private int quantity;
    private BigDecimal price;
}
