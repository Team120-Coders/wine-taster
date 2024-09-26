package mate.academy.winetaster.dto.user;

public record UserResponseDto(
        Long id,
        String login,
        String email,
        String firstName,
        String lastName
) {}

