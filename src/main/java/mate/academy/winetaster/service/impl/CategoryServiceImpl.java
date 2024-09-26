package mate.academy.winetaster.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.winetaster.dto.category.CategoryResponseDto;
import mate.academy.winetaster.dto.category.CreateCategoryRequestDto;
import mate.academy.winetaster.dto.category.UpdateCategoryRequestDto;
import mate.academy.winetaster.dto.wine.WineDtoWithoutCategoryIds;
import mate.academy.winetaster.exception.CategoryNameAlreadyExistsException;
import mate.academy.winetaster.exception.CategoryNotFoundException;
import mate.academy.winetaster.mapper.CategoryMapper;
import mate.academy.winetaster.mapper.WineMapper;
import mate.academy.winetaster.model.Category;
import mate.academy.winetaster.model.Wine;
import mate.academy.winetaster.repository.category.CategoryRepository;
import mate.academy.winetaster.repository.wine.WineRepository;
import mate.academy.winetaster.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final WineRepository wineRepository;
    private final WineMapper wineMapper;

    @Override
    @Transactional
    public CategoryResponseDto save(CreateCategoryRequestDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new CategoryNameAlreadyExistsException("Category with the name '"
                    + categoryDto.getName() + "' already exists.");
        }

        Category category = categoryMapper.toEntity(categoryDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponseDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toDtoList(categories);
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID '"
                        + id + "' not found."));

        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID '"
                        + id + "' not found."));

        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public CategoryResponseDto update(Long id, UpdateCategoryRequestDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID '"
                        + id + "' not found."));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<WineDtoWithoutCategoryIds> findAllByCategoryId(Long id) {
        List<Wine> wines = wineRepository.findAllByCategoryId(id);
        return wineMapper.toDtoWithoutCategoriesList(wines);
    }
}
