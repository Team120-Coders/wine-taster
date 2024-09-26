package mate.academy.winetaster.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.winetaster.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.winetaster.dto.cartitem.UpdateCartItemRequestDto;
import mate.academy.winetaster.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.winetaster.exception.CartItemNotFoundException;
import mate.academy.winetaster.exception.ShoppingCartNotFoundException;
import mate.academy.winetaster.exception.WineNotFoundException;
import mate.academy.winetaster.mapper.ShoppingCartMapper;
import mate.academy.winetaster.model.CartItem;
import mate.academy.winetaster.model.ShoppingCart;
import mate.academy.winetaster.model.User;
import mate.academy.winetaster.model.Wine;
import mate.academy.winetaster.repository.cartitem.CartItemRepository;
import mate.academy.winetaster.repository.shoppingcart.ShoppingCartRepository;
import mate.academy.winetaster.repository.wine.WineRepository;
import mate.academy.winetaster.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final WineRepository wineRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartResponseDto getShoppingCartForUser(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found "
                        + "for user id " + userId));
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto addItemToCart(CreateCartItemRequestDto requestDto, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found "
                        + "for user id " + userId));

        Wine wine = wineRepository.findById(requestDto.getWineId())
                .orElseThrow(() -> new WineNotFoundException("Wine not found for id "
                        + requestDto.getWineId()));

        CartItem cartItem = cartItemRepository.findByShoppingCartAndWine(shoppingCart, wine)
                .orElseGet(() -> {
                    CartItem newCartItem = new CartItem();
                    newCartItem.setShoppingCart(shoppingCart);
                    newCartItem.setWine(wine);
                    newCartItem.setQuantity(requestDto.getQuantity());
                    shoppingCart.getCartItems().add(newCartItem);
                    return newCartItem;
                });

        if (cartItem.getId() != null) {
            cartItem.setQuantity(cartItem.getQuantity() + requestDto.getQuantity());
        }

        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto updateCartItem(
            Long cartItemId,
            UpdateCartItemRequestDto requestDto,
            Long userId
    ) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found "
                        + "for user id " + userId));

        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(cartItemId, cart.getId())
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found for id "
                        + cartItemId + " and cart id " + cart.getId()));

        cartItem.setQuantity(requestDto.getQuantity());
        return shoppingCartMapper.toDto(cartItemRepository.save(cartItem).getShoppingCart());
    }

    @Override
    @Transactional
    public void removeCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found for id "
                        + cartItemId));
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }
}
