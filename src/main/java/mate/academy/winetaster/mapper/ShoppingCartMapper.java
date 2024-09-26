package mate.academy.winetaster.mapper;

import mate.academy.winetaster.config.MapperConfig;
import mate.academy.winetaster.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.winetaster.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {CartItemMapper.class})
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);
}
