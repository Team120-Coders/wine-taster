package mate.academy.winetaster.mapper;

import mate.academy.winetaster.config.MapperConfig;
import mate.academy.winetaster.dto.cartitem.CartItemResponseDto;
import mate.academy.winetaster.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "wineId", source = "wine.id")
    @Mapping(target = "wineName", source = "wine.name")
    CartItemResponseDto toDto(CartItem cartItem);
}
