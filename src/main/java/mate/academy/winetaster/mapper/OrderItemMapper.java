package mate.academy.winetaster.mapper;

import java.util.Set;
import mate.academy.winetaster.config.MapperConfig;
import mate.academy.winetaster.dto.orderitem.OrderItemResponseDto;
import mate.academy.winetaster.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "wine.id", target = "wineId")
    OrderItemResponseDto toDto(OrderItem orderItem);

    Set<OrderItemResponseDto> map(Set<OrderItem> orderItems);
}
