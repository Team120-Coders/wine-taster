package mate.academy.winetaster.service;

import java.util.List;
import java.util.Set;
import mate.academy.winetaster.dto.order.CreateOrderRequestDto;
import mate.academy.winetaster.dto.order.OrderResponseDto;
import mate.academy.winetaster.dto.orderitem.OrderItemResponseDto;
import mate.academy.winetaster.model.Order;

public interface OrderService {
    List<OrderResponseDto> getOrders(Long userId);

    OrderResponseDto createOrder(Long userId, CreateOrderRequestDto createOrderRequestDto);

    OrderResponseDto updateOrderStatus(Long orderId, Order.Status status);

    Set<OrderItemResponseDto> getOrderItems(Long orderId);

    OrderItemResponseDto getOrderItem(Long orderId, Long itemId);
}
