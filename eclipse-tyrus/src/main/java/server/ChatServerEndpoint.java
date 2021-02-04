package server;

import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.util.logging.Logger;

@ServerEndpoint(value = "/server")
public class ChatServerEndpoint {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected, sessionID = " + session.getId());
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        switch (message) {
            case "start":
                logger.info("Starting the game sending first word");
                String scrambledWord = WordRepository.getInstance().getRandomWord().getScrambledWord();
                session.getUserProperties().put("scrambledWord", scrambledWord);
                return scrambledWord;
            case "quit":
                logger.info("Quitting the game");
                try {
                    session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Game finished"));
                    System.exit(0);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
        String scrambledWord = (String) session.getUserProperties().get("scrambledWord");
        return checkLastWordAndSendANewWord(scrambledWord, message, session);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session " + session.getId() +
                " closed because " + closeReason);
    }

    private String checkLastWordAndSendANewWord(String scrambledWord, String unscrambledWord, Session session) {
        WordRepository repository = WordRepository.getInstance();
        Word word = repository.getWord(scrambledWord);

        String nextScrambledWord = repository.getRandomWord().getScrambledWord();

        session.getUserProperties().put("scrambledWord", nextScrambledWord);

        String correctUnscrambledWord = word.getUnscrambleWord();

        if (word == null || !correctUnscrambledWord.equals(unscrambledWord)) {
            return String.format("You guessed it wrong. Correct answer %s. Try the next one .. %s",
                    correctUnscrambledWord, nextScrambledWord);
        }
        return String.format("You guessed it right. Try the next word ...  %s", nextScrambledWord);
    }
}
