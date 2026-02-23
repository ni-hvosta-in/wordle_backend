package nihvostain.wordle_backend.game.services;

import nihvostain.wordle_backend.game.LetterStatus;
import nihvostain.wordle_backend.game.Level;
import nihvostain.wordle_backend.game.NonExistentWordException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {

    WordService wordService;

    public GameService(WordService wordService) {
        this.wordService = wordService;
    }

    public String [] checkDaily (String attempt, Level level) throws NonExistentWordException {

        if (!wordService.dictionaryContains(attempt)){
            throw new NonExistentWordException("Such a word does not exist");
        }

        char [] dailyWordChars = wordService.getDailyWord(level).toCharArray();

        char [] attemptChars = attempt.toCharArray();
        Map<Character, Integer> letterCount = new HashMap<>();
        for (char ch : dailyWordChars) {
            letterCount.put(ch, letterCount.getOrDefault(ch, 0) + 1);
        }

        System.out.println(letterCount);
        String [] statues = new String [attempt.length()];

        for (int i = 0; i < attemptChars.length; i++) {

            if (letterCount.containsKey(attemptChars[i]) && letterCount.get(attemptChars[i]) > 0) {
                if (dailyWordChars[i] == attemptChars[i]) {
                    statues[i] = LetterStatus.CORRECT.getStatus();
                } else {
                    statues[i] = LetterStatus.INCLUDES.getStatus();
                }
                letterCount.put(attemptChars[i], letterCount.get(attemptChars[i]) - 1);
            }
            if (!letterCount.containsKey(attemptChars[i])) {
                statues[i] = LetterStatus.WRONG.getStatus();
            }
        }

        return statues;
    }
}
