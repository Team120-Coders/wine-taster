package mate.academy.winetaster.service;

import mate.academy.winetaster.dto.user.UserRegistrationRequestDto;
import mate.academy.winetaster.model.User;

public interface RegistrationService {
    User register(UserRegistrationRequestDto request);
}
