package greendar.global.auth.oauthlogin.application;

import greendar.global.auth.oauthlogin.dao.user.UserRepository;
import greendar.global.auth.oauthlogin.domain.user.User;
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
