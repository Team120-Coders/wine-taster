package mate.academy.winetaster.service;

import mate.academy.winetaster.dto.user.UpdateUserProfileRequestDto;
import mate.academy.winetaster.model.User;

public interface UserService {
    User updateUserRole(Long id, String roleName);

    User getUserByEmail(String email);

    User updateUserProfile(String email, UpdateUserProfileRequestDto requestDto);
}
