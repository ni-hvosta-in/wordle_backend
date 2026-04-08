package nihvostain.wordle_backend.game.modes;

import jakarta.persistence.EntityNotFoundException;
import nihvostain.wordle_backend.game.GameMode;
import nihvostain.wordle_backend.game.LetterStatus;
import nihvostain.wordle_backend.game.Level;
import nihvostain.wordle_backend.game.entity.UserGame;
import nihvostain.wordle_backend.game.entity.UserGameRepository;
import nihvostain.wordle_backend.game.services.GameSessionService;
import nihvostain.wordle_backend.game.services.WordChecker;
import nihvostain.wordle_backend.game.services.WordService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class PersonalGameStrategy implements GameModeStrategy {

    private static final int MAX_ATTEMPTS = 6;

    UserGameRepository userGameRepository;
    WordChecker wordChecker;
    WordService wordService;
    GameSessionService gameSessionService;

    public PersonalGameStrategy(UserGameRepository userGameRepository, WordChecker wordChecker, WordService wordService, GameSessionService gameSessionService) {
        this.userGameRepository = userGameRepository;
        this.wordChecker = wordChecker;
        this.wordService = wordService;
        this.gameSessionService = gameSessionService;
    }
    @Override
    @Transactional
    public LetterStatus[] check(Long id, String attempt, Level level) {

        UserGame userGame = userGameRepository.findByUserId(id).orElseThrow(
                () -> new EntityNotFoundException("User game not found")
        );

        int index = userGame.getIndexByLevel(level);
        String target = wordService.getPersonalWord(level, index);
        LetterStatus [] statuses = wordChecker.checkWord(attempt, target);

        gameSessionService.addAttempt(id, level, attempt, statuses);

        if (Arrays.stream(statuses).allMatch((status) -> status.equals(LetterStatus.CORRECT))
                || gameSessionService.getAttemptsByLevel(id, level).size() == MAX_ATTEMPTS) {

            userGame.setIndexByLevel(level, index + 1);
            gameSessionService.clearAttempts(id, level);

        }

        return statuses;
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.PERSONAL;
    }
}
