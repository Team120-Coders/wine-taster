package mate.academy.winetaster.repository.cartitem;

import java.util.Optional;
import mate.academy.winetaster.model.CartItem;
import mate.academy.winetaster.model.ShoppingCart;
import mate.academy.winetaster.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndShoppingCartId(Long id, Long shoppingCartId);

    Optional<CartItem> findByShoppingCartAndWine(ShoppingCart shoppingCart, Wine wine);
}
