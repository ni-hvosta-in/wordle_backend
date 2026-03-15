package nihvostain.wordle_backend.game;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nihvostain.wordle_backend.game.dto.GameRequest;
import nihvostain.wordle_backend.game.dto.GameResponse;
import nihvostain.wordle_backend.game.services.GameService;
import nihvostain.wordle_backend.game.services.WordService;
import org.osgi.annotation.bundle.Header;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    WordService wordService;
    GameService gameService;

    public GameController(WordService wordService, GameService gameService) {
        this.wordService = wordService;
        this.gameService = gameService;
    }

    @PostMapping("/daily/check")
    public ResponseEntity<GameResponse> checkDailyWord(@Valid @RequestBody GameRequest gameRequest){
        return ResponseEntity.ok(new GameResponse(gameService.check(GameMode.DAILY, null,gameRequest.attempt(), gameRequest.level())));
    }

    @GetMapping("/daily/word")
    public ResponseEntity<String> getDailyWord(@RequestParam Level level){
        return ResponseEntity.ok(wordService.getDailyWord(level));
    }

    @PostMapping("/personal/check")
    public ResponseEntity<GameResponse> checkPersonalWord(@Valid @RequestBody GameRequest gameRequest,
                                                          @RequestHeader("Authorization") String token, HttpServletRequest request){
        Long userID = (Long) request.getAttribute("userID");
        System.out.println(userID);
        return ResponseEntity.ok(new GameResponse(gameService.check(GameMode.PERSONAL, userID, gameRequest.attempt(), gameRequest.level())));
    }
}
