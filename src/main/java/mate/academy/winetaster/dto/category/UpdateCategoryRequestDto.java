package mate.academy.winetaster.dto.category;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UpdateCategoryRequestDto {
    @Column(nullable = false)
    private String name;

    private String description;
}
