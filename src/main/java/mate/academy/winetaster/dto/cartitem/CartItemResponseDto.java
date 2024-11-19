package mate.academy.winetaster.dto.cartitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemResponseDto {
    private Long id;
    private Long wineId;
    private String wineName;
    private int quantity;
}
