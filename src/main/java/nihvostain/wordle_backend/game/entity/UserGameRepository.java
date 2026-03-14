package nihvostain.wordle_backend.game.entity;

import nihvostain.wordle_backend.user.User;
import org.springframework.data.repository.Repository;

public interface UserGameRepository extends Repository<UserGame, Long> {
    void save(UserGame userGame);
    boolean existsByUser(User user);
}
