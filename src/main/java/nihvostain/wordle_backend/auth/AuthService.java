package nihvostain.wordle_backend.auth;

import nihvostain.wordle_backend.auth.dto.AuthRequest;
import nihvostain.wordle_backend.auth.dto.AuthResponse;
import nihvostain.wordle_backend.auth.entity.User;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse login(AuthRequest authRequest) throws AuthException {

        User user = userRepository.findUserByLogin(authRequest.login()).orElseThrow(() ->
                new AuthException("Invalid login")
        );

        if (!user.getPassword().equals(authRequest.password())){
            throw new AuthException("Invalid password");
        }

        return new AuthResponse("auth", "1234");

    }

    public AuthResponse register(AuthRequest authRequest) throws AuthException {

        if (userRepository.existsUserByLogin(authRequest.login())) {
            throw new AuthException("User already exists");
        }

        User user = new User(authRequest.login(), authRequest.password());
        userRepository.save(user);

        return new AuthResponse("auth", "1234");

    }
}
