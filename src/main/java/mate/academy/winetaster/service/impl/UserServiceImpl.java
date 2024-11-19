package mate.academy.winetaster.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.winetaster.dto.user.UpdateUserProfileRequestDto;
import mate.academy.winetaster.exception.UserNotFoundException;
import mate.academy.winetaster.model.Role;
import mate.academy.winetaster.model.User;
import mate.academy.winetaster.repository.role.RoleRepository;
import mate.academy.winetaster.repository.user.UserRepository;
import mate.academy.winetaster.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public User updateUserRole(Long id, String roleName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));
        Role role = roleRepository.findByRole(Role.RoleName.valueOf(roleName))
                .orElseThrow(() -> new RuntimeException("Role " + roleName
                        + " not found in the system."));
        user.getRoles().clear();
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new UserNotFoundException("User with email " + email
                        + " not found or has been deleted."));
    }

    @Override
    @Transactional
    public User updateUserProfile(String email, UpdateUserProfileRequestDto requestDto) {
        User user = getUserByEmail(email);
        user.setLogin(requestDto.login());
        user.setFirstName(requestDto.firstName());
        user.setLastName(requestDto.lastName());
        return userRepository.save(user);
    }
}
