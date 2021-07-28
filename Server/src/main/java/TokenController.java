import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class TokenController {
    private static TokenController instance;
    public static ArrayList<String> allTokens = new ArrayList<>();

    private TokenController() {

    }

    public static TokenController getInstance() {
        if (instance == null)
            instance = new TokenController();
        return instance;
    }

    public String generateRandomToken() {
        String AlphaNumericString = "0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder token = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            token.append(AlphaNumericString.charAt(index));
        }
        return token.toString();
    }

    public String getNewToken() {
        String token = generateRandomToken();
        Timer timer = new Timer();
        allTokens.add(token);
        autoRemove task = new autoRemove(timer, token);
        timer.schedule(task, 5 * 60 * 1000);
        return token;
    }

    class autoRemove extends TimerTask {
        Timer timer;
        String token;

        public autoRemove(Timer timer, String token) {
            this.timer = timer;
            this.token = token;
        }

        @Override
        public void run() {
            allTokens.remove(this.token);
            System.out.println(this.token + " has removed automatic after 5 minuets;");
            this.timer.cancel();
        }
    }


}
