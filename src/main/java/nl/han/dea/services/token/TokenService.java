package nl.han.dea.services.token;

import java.util.Random;

public class TokenService {

    public String generateToken() {
        Random rand = new Random();
        String token = "";
        for(int i = 0; i < 12; i++) {
            token += (rand.nextInt(10));
        }

        StringBuilder str = new StringBuilder(token);
        int idx = str.length() - 4;

        while (idx > 0) {
            str.insert(idx, "-");
            idx = idx - 4;
        }
        return str.toString();
    }
}
