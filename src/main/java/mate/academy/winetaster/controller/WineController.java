package mate.academy.winetaster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.winetaster.dto.wine.CreateWineRequestDto;
import mate.academy.winetaster.dto.wine.UpdateWineRequestDto;
import mate.academy.winetaster.dto.wine.WineResponseDto;
import mate.academy.winetaster.dto.wine.WineSearchParameters;
import mate.academy.winetaster.service.WineService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Wine", description = "Endpoints for managing wines")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wines")
public class WineController {
    private final WineService wineService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get all wines with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wines retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WineResponseDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content)
    })
    @GetMapping
    public List<WineResponseDto> getAll(
            @Parameter(description = "Pagination and sorting information")
            @PageableDefault(size = 10, sort = {"name", "producer"}) Pageable pageable
    ) {
        return wineService.findAll(pageable);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get a wine by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the wine",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WineResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Wine not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public WineResponseDto getWineById(@PathVariable @Positive Long id) {
        return wineService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new wine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wine created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WineResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public WineResponseDto createWine(@RequestBody @Valid CreateWineRequestDto wineDto) {
        return wineService.save(wineDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a wine by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Wine deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Wine not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteWine(@PathVariable @Positive Long id) {
        wineService.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a wine by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wine updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WineResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Wine not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public WineResponseDto updateWine(@PathVariable @Positive Long id,
                                      @RequestBody @Valid UpdateWineRequestDto wineDto) {
        return wineService.update(id, wineDto);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Search for wines with parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wines retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WineResponseDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content)
    })
    @GetMapping("/search")
    public List<WineResponseDto> searchWines(
            WineSearchParameters searchParameters,
            @Parameter(description = "Pagination and sorting information")
            @PageableDefault(size = 10, sort = {"name", "producer"})
            Pageable pageable
    ) {
        return wineService.search(searchParameters, pageable);
    }
}
