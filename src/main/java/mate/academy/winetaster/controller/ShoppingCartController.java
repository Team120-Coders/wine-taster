package mate.academy.winetaster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.winetaster.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.winetaster.dto.cartitem.UpdateCartItemRequestDto;
import mate.academy.winetaster.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.winetaster.model.User;
import mate.academy.winetaster.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping Cart", description = "Endpoints for managing the shopping cart")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get the current user's shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shopping cart retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShoppingCartResponseDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Shopping cart not found",
                    content = @Content)
    })
    @GetMapping
    public ShoppingCartResponseDto getShoppingCart() {
        Long userId = getCurrentUserId();
        return shoppingCartService.getShoppingCartForUser(userId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Add an item to the shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item added to cart",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShoppingCartResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ShoppingCartResponseDto addItemToCart(
            @Valid @RequestBody
            CreateCartItemRequestDto requestDto
    ) {
        Long userId = getCurrentUserId();
        return shoppingCartService.addItemToCart(requestDto, userId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update an item in the shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item updated in cart",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShoppingCartResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found in cart",
                    content = @Content)
    })
    @PutMapping("/items/{cartItemId}")
    public ShoppingCartResponseDto updateCartItem(@PathVariable Long cartItemId,
                                          @Valid @RequestBody UpdateCartItemRequestDto requestDto) {
        Long userId = getCurrentUserId();
        return shoppingCartService.updateCartItem(cartItemId, requestDto, userId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Remove an item from the shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item removed from cart",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found in cart",
                    content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/items/{cartItemId}")
    public void removeCartItem(@PathVariable Long cartItemId) {
        shoppingCartService.removeCartItem(cartItemId);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}
