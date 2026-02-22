package nihvostain.wordle_backend.auth;

import nihvostain.wordle_backend.auth.entity.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
    Optional<User> findUserByLogin(String login);
    boolean existsUserByLogin(String login);
    User save(User user);
}
