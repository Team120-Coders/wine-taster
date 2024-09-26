package mate.academy.winetaster.dto.order;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequestDto {
    @Column(nullable = false)
    @Size(min = 1, max = 255)
    private String shippingAddress;
}
