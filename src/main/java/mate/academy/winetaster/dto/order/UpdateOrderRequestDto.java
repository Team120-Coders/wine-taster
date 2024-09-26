package mate.academy.winetaster.dto.order;

import lombok.Getter;
import lombok.Setter;
import mate.academy.winetaster.model.Order;

@Getter
@Setter
public class UpdateOrderRequestDto {
    private Order.Status status;
}
