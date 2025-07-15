package diego.soro.model2.core.User.service;

import diego.soro.model2.core.User.User;
import diego.soro.model2.core.User.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("A user with that email already exists.");
        }
        return userRepository.save(user);
    }
}
