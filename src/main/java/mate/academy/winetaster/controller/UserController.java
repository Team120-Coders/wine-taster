package mate.academy.winetaster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.winetaster.dto.user.UpdateUserProfileRequestDto;
import mate.academy.winetaster.dto.user.UpdateUserRoleRequestDto;
import mate.academy.winetaster.dto.user.UserProfileResponseDto;
import mate.academy.winetaster.dto.user.UserResponseDto;
import mate.academy.winetaster.mapper.UserMapper;
import mate.academy.winetaster.model.User;
import mate.academy.winetaster.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "Endpoints for managing users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update a user's role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserProfileResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @PutMapping("/{id}/role")
    public UserProfileResponseDto updateUserRole(
            @Parameter(description = "ID of the user to update")
            @PathVariable Long id,
            @Parameter(description = "New role information")
            @Valid @RequestBody UpdateUserRoleRequestDto requestDto
    ) {
        User updatedUser = userService.updateUserRole(id, requestDto.getRoleName());
        return userMapper.toProfileDto(updatedUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(summary = "Get the profile of the currently logged-in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserProfileResponseDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @GetMapping("/me")
    public UserProfileResponseDto getMyProfile(Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        return userMapper.toProfileDto(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(summary = "Update the profile of the currently logged-in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @PutMapping("/me")
    public UserResponseDto updateMyProfile(
            @Parameter(description = "Updated user profile information")
            @Valid @RequestBody UpdateUserProfileRequestDto requestDto,
            Authentication authentication
    ) {
        User user = userService.updateUserProfile(authentication.getName(), requestDto);
        return userMapper.toDto(user);
    }
}
