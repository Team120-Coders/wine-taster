package mate.academy.winetaster.security;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mate.academy.winetaster.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws EntityNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with email: " + email));
    }
}
