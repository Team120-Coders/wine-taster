package mate.academy.winetaster.repository.order;

import java.util.List;
import java.util.Optional;
import mate.academy.winetaster.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<List<Order>> findByUserId(Long userId);
}
