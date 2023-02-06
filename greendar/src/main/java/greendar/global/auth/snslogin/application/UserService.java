package greendar.global.auth.snslogin.application;

import greendar.global.auth.snslogin.dao.user.UserRepository;
import greendar.global.auth.snslogin.domain.user.User;
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
