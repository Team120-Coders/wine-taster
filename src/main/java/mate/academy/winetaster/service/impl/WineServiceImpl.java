package mate.academy.winetaster.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.winetaster.dto.wine.CreateWineRequestDto;
import mate.academy.winetaster.dto.wine.UpdateWineRequestDto;
import mate.academy.winetaster.dto.wine.WineResponseDto;
import mate.academy.winetaster.dto.wine.WineSearchParameters;
import mate.academy.winetaster.exception.WineNotFoundException;
import mate.academy.winetaster.exception.WineRegionAlreadyExistsException;
import mate.academy.winetaster.mapper.WineMapper;
import mate.academy.winetaster.model.Category;
import mate.academy.winetaster.model.Wine;
import mate.academy.winetaster.repository.category.CategoryRepository;
import mate.academy.winetaster.repository.wine.WineRepository;
import mate.academy.winetaster.repository.wine.WineSpecificationBuilder;
import mate.academy.winetaster.service.WineService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WineServiceImpl implements WineService {
    private final WineRepository wineRepository;
    private final WineMapper wineMapper;
    private final WineSpecificationBuilder wineSpecificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public WineResponseDto save(CreateWineRequestDto requestDto) {
        if (wineRepository.existsByRegion(requestDto.getRegion())) {
            throw new WineRegionAlreadyExistsException("Wine with the region '"
                    + requestDto.getRegion() + "' already exists.");
        }

        Wine wine = wineMapper.toEntity(requestDto);
        wine.setCategories(getCategoriesFromIds(requestDto.getCategoryIds()));
        return wineMapper.toDto(wineRepository.save(wine));
    }

    @Override
    public List<WineResponseDto> findAll(Pageable pageable) {
        return wineRepository.findAll(pageable)
                .map(wineMapper::toDto)
                .toList();
    }

    @Override
    public WineResponseDto findById(Long id) {
        Wine wine = wineRepository.findById(id)
                .orElseThrow(() -> new WineNotFoundException("Wine with ID '"
                        + id + "' not found."));

        return wineMapper.toDto(wine);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Wine wine = wineRepository.findById(id)
                .orElseThrow(() -> new WineNotFoundException("Wine with ID '"
                        + id + "' not found."));

        wineRepository.delete(wine);
    }

    @Override
    @Transactional
    public WineResponseDto update(Long id, UpdateWineRequestDto wineDto) {
        Wine wine = wineRepository.findById(id)
                .orElseThrow(() -> new WineNotFoundException("Wine with ID '"
                        + id + "' not found."));

        wine.setName(wineDto.getName());
        wine.setProducer(wineDto.getProducer());
        wine.setRegion(wineDto.getRegion());
        wine.setGrapeVariety(wineDto.getGrapeVariety());
        wine.setVintage(wineDto.getVintage());
        wine.setPrice(wineDto.getPrice());
        wine.setAlcoholContent(wineDto.getAlcoholContent());
        wine.setDescription(wineDto.getDescription());
        wine.setImageUrl(wineDto.getImageUrl());
        wine.setCategories(getCategoriesFromIds(wineDto.getCategoryIds()));

        wine = wineRepository.save(wine);
        return wineMapper.toDto(wine);
    }

    @Override
    public List<WineResponseDto> search(WineSearchParameters params, Pageable pageable) {
        Specification<Wine> wineSpecification = wineSpecificationBuilder.build(params);
        return wineRepository.findAll(wineSpecification, pageable)
                .map(wineMapper::toDto)
                .toList();
    }

    private Set<Category> getCategoriesFromIds(Set<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(categoryRepository.findAllById(categoryIds));
    }
}
