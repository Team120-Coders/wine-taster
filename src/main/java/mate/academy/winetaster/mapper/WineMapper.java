package mate.academy.winetaster.mapper;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.winetaster.config.MapperConfig;
import mate.academy.winetaster.dto.wine.CreateWineRequestDto;
import mate.academy.winetaster.dto.wine.WineDtoWithoutCategoryIds;
import mate.academy.winetaster.dto.wine.WineResponseDto;
import mate.academy.winetaster.model.Category;
import mate.academy.winetaster.model.Wine;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface WineMapper {
    Wine toEntity(CreateWineRequestDto requestDto);

    WineResponseDto toDto(Wine wine);

    List<WineDtoWithoutCategoryIds> toDtoWithoutCategoriesList(List<Wine> wines);

    @AfterMapping
    default void setCategoryIds(@MappingTarget WineResponseDto wineResponseDto, Wine wine) {
        if (wine.getCategories() != null) {
            wineResponseDto.setCategoryIds(wine.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet()));
        }
    }
}
