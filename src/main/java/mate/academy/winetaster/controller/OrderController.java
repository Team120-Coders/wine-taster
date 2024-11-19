package mate.academy.winetaster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.winetaster.dto.order.CreateOrderRequestDto;
import mate.academy.winetaster.dto.order.OrderResponseDto;
import mate.academy.winetaster.dto.order.UpdateOrderRequestDto;
import mate.academy.winetaster.dto.orderitem.OrderItemResponseDto;
import mate.academy.winetaster.model.User;
import mate.academy.winetaster.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orders", description = "Endpoints for managing orders")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponseDto createOrder(
            @Valid @RequestBody CreateOrderRequestDto createOrderRequestDto
    ) {
        Long userId = getCurrentUserId();
        return orderService.createOrder(userId, createOrderRequestDto);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get all orders for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Orders not found",
                    content = @Content)
    })
    @GetMapping
    public List<OrderResponseDto> getOrders() {
        Long userId = getCurrentUserId();
        return orderService.getOrders(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status by order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content)
    })
    @PatchMapping("/{orderId}")
    public OrderResponseDto updateOrder(@PathVariable Long orderId,
                                @Valid @RequestBody UpdateOrderRequestDto updateOrderRequestDto) {
        return orderService.updateOrderStatus(orderId, updateOrderRequestDto.getStatus());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get all items for a specific order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order items retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderItemResponseDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order items not found",
                    content = @Content)
    })
    @GetMapping("/{orderId}/items")
    public Set<OrderItemResponseDto> getOrderItems(@PathVariable Long orderId) {
        return orderService.getOrderItems(orderId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get a specific item from an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order item retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderItemResponseDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order item not found",
                    content = @Content)
    })
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemResponseDto getOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId
    ) {
        return orderService.getOrderItem(orderId, itemId);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}
