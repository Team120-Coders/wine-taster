package mate.academy.winetaster.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.winetaster.dto.order.CreateOrderRequestDto;
import mate.academy.winetaster.dto.order.OrderResponseDto;
import mate.academy.winetaster.dto.orderitem.OrderItemResponseDto;
import mate.academy.winetaster.exception.OrderItemsNotFoundException;
import mate.academy.winetaster.exception.OrderNotFoundException;
import mate.academy.winetaster.exception.ShoppingCartNotFoundException;
import mate.academy.winetaster.mapper.OrderItemMapper;
import mate.academy.winetaster.mapper.OrderMapper;
import mate.academy.winetaster.model.Order;
import mate.academy.winetaster.model.OrderItem;
import mate.academy.winetaster.model.ShoppingCart;
import mate.academy.winetaster.repository.order.OrderRepository;
import mate.academy.winetaster.repository.orderitem.OrderItemRepository;
import mate.academy.winetaster.repository.shoppingcart.ShoppingCartRepository;
import mate.academy.winetaster.service.EmailSenderService;
import mate.academy.winetaster.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final EmailSenderService emailSenderService;

    @Override
    public List<OrderResponseDto> getOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId)
                .orElseThrow(() -> new OrderNotFoundException("Orders not found "
                        + "for user id " + userId));

        return orderMapper.map(orders);
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(Long userId, CreateOrderRequestDto createOrderRequestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found "
                        + "for user id " + userId));

        BigDecimal total = calculateTotal(shoppingCart);

        Order order = createNewOrder(shoppingCart, total,
                createOrderRequestDto.getShippingAddress());

        createAndSaveOrderItems(shoppingCart, order);

        clearShoppingCart(shoppingCart);

        String recipientEmail = shoppingCart.getUser().getEmail();
        String subject = "Your order has been created!";
        String text = String.format("Dear %s, your order with ID %d has been successfully created.",
                shoppingCart.getUser().getLogin(), order.getId());

        emailSenderService.sendEmail(recipientEmail, subject, text);

        OrderResponseDto orderDto = orderMapper.toDto(order);
        Set<OrderItemResponseDto> orderItemsDto = getOrderItems(order.getId());
        orderDto.setOrderItems(orderItemsDto);
        return orderDto;
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, Order.Status status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id "
                        + orderId + " not found"));

        order.setStatus(status);
        orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    @Override
    public Set<OrderItemResponseDto> getOrderItems(Long orderId) {
        Set<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId)
                .orElseThrow(() -> new OrderItemsNotFoundException("No items found for order"
                        + " with id " + orderId));

        return orderItemMapper.map(orderItems);
    }

    @Override
    public OrderItemResponseDto getOrderItem(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new OrderItemsNotFoundException("No item found for order"
                        + " with id " + orderId));

        if (!orderItem.getOrder().getId().equals(orderId)) {
            throw new IllegalArgumentException("OrderItem does not belong to the specified Order "
                    + "with id " + orderId);
        }

        return orderItemMapper.toDto(orderItem);
    }

    private BigDecimal calculateTotal(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().stream()
                .map(cartItem -> cartItem.getWine().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order createNewOrder(ShoppingCart shoppingCart, BigDecimal total,
                                 String shippingAddress) {
        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setStatus(Order.Status.PENDING);
        order.setTotal(total);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(shippingAddress);
        return orderRepository.save(order);
    }

    private void createAndSaveOrderItems(ShoppingCart shoppingCart, Order order) {
        shoppingCart.getCartItems().forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setWine(cartItem.getWine());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getWine().getPrice());
            orderItemRepository.save(orderItem);
        });
    }

    private void clearShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);
    }
}
