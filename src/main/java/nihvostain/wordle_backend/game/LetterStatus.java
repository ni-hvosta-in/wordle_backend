package nihvostain.wordle_backend.game;

import lombok.Getter;

@Getter
public enum LetterStatus {
    CORRECT ("correct"),
    INCLUDES ("includes"),
    UNUSED ("unused"),
    WRONG ("wrong"),;

    final String status;

    LetterStatus(String status) {
        this.status = status;
    }

}
