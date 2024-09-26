package mate.academy.winetaster.dto.shoppingcart;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import mate.academy.winetaster.dto.cartitem.CartItemResponseDto;

@Getter
@Setter
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItemResponseDto> cartItems;
}
