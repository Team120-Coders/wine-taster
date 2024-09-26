package mate.academy.winetaster.mapper;

import java.util.List;
import mate.academy.winetaster.config.MapperConfig;
import mate.academy.winetaster.dto.category.CategoryResponseDto;
import mate.academy.winetaster.dto.category.CreateCategoryRequestDto;
import mate.academy.winetaster.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    Category toEntity(CreateCategoryRequestDto categoryDto);

    List<CategoryResponseDto> toDtoList(List<Category> categories);
}
