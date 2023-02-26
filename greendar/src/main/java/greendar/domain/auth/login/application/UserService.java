package greendar.domain.auth.login.application;

import greendar.domain.auth.login.dao.user.UserRepository;
import greendar.domain.auth.login.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}
