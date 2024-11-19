package mate.academy.winetaster.mapper;

import java.util.List;
import mate.academy.winetaster.config.MapperConfig;
import mate.academy.winetaster.dto.order.OrderResponseDto;
import mate.academy.winetaster.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderResponseDto toDto(Order order);

    List<OrderResponseDto> map(List<Order> orders);
}
