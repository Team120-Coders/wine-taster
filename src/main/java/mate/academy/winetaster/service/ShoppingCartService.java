package mate.academy.winetaster.service;

import mate.academy.winetaster.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.winetaster.dto.cartitem.UpdateCartItemRequestDto;
import mate.academy.winetaster.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.winetaster.model.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCartForUser(Long userId);

    ShoppingCartResponseDto addItemToCart(CreateCartItemRequestDto requestDto, Long userId);

    ShoppingCartResponseDto updateCartItem(Long cartItemId, UpdateCartItemRequestDto requestDto,
                                   Long userId);

    void removeCartItem(Long cartItemId);

    void createShoppingCart(User user);
}
