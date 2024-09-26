package mate.academy.winetaster.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.winetaster.dto.user.UserRegistrationRequestDto;
import mate.academy.winetaster.exception.RegistrationException;
import mate.academy.winetaster.mapper.UserMapper;
import mate.academy.winetaster.model.Role;
import mate.academy.winetaster.model.User;
import mate.academy.winetaster.repository.role.RoleRepository;
import mate.academy.winetaster.repository.user.UserRepository;
import mate.academy.winetaster.service.RegistrationService;
import mate.academy.winetaster.service.ShoppingCartService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public User register(UserRegistrationRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("Registration failed: Email "
                    + request.getEmail() + " is already taken.");
        }
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new RegistrationException("Registration failed: Login "
                    + request.getLogin() + " is already taken.");
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role defaultRole = roleRepository.findByRole(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Default role 'ROLE_USER' not found."));
        user.setRoles(Set.of(defaultRole));
        user = userRepository.save(user);
        shoppingCartService.createShoppingCart(user);
        return user;
    }
}
