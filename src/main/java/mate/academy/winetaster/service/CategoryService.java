package mate.academy.winetaster.service;

import java.util.List;
import mate.academy.winetaster.dto.category.CategoryResponseDto;
import mate.academy.winetaster.dto.category.CreateCategoryRequestDto;
import mate.academy.winetaster.dto.category.UpdateCategoryRequestDto;
import mate.academy.winetaster.dto.wine.WineDtoWithoutCategoryIds;

public interface CategoryService {
    CategoryResponseDto save(CreateCategoryRequestDto categoryDto);

    List<CategoryResponseDto> findAll();

    CategoryResponseDto getById(Long id);

    void deleteById(Long id);

    CategoryResponseDto update(Long id, UpdateCategoryRequestDto categoryDto);

    List<WineDtoWithoutCategoryIds> findAllByCategoryId(Long id);
}
