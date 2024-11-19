package mate.academy.winetaster.service;

import java.util.List;
import mate.academy.winetaster.dto.wine.CreateWineRequestDto;
import mate.academy.winetaster.dto.wine.UpdateWineRequestDto;
import mate.academy.winetaster.dto.wine.WineResponseDto;
import mate.academy.winetaster.dto.wine.WineSearchParameters;
import org.springframework.data.domain.Pageable;

public interface WineService {
    WineResponseDto save(CreateWineRequestDto requestDto);

    List<WineResponseDto> findAll(Pageable pageable);

    WineResponseDto findById(Long id);

    void deleteById(Long id);

    WineResponseDto update(Long id, UpdateWineRequestDto wineDto);

    List<WineResponseDto> search(WineSearchParameters params, Pageable pageable);
}
