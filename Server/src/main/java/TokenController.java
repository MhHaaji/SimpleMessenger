import java.util.ArrayList;

public class TokenController {
    private static TokenController instance;
    private ArrayList<String> allTokens = new ArrayList<>();

    private TokenController() {

    }

    public static TokenController getInstance() {
        if (instance == null)
            instance = new TokenController();
        return instance;
    }

    public void generateRandomToken() {
        String AlphaNumericString = "0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder token = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            token.append(AlphaNumericString.charAt(index));
        }
        allTokens.add(token.toString());


    }




    class Token{
        String body;
        public Token(String body){
            this.body = body;

        }

    }
}
