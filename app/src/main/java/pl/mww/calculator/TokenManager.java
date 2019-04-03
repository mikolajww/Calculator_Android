package pl.mww.calculator;

import java.util.ArrayList;
import java.util.List;

public class TokenManager {
    private List<String> tokenList;

    TokenManager() {
        tokenList = new ArrayList<>();
    }

    TokenManager(List<String> tokens) {
        if(tokens == null) {
            tokens = new ArrayList<>();
        }
        tokenList = tokens;
    }

    public String constructString() {
        StringBuilder sb = new StringBuilder();
        for (String token:tokenList) {
            sb.append(token);
        }
        return sb.toString();
    }

    public void append(String t) {
        tokenList.add(t);
    }

    public String removeToken() {
        if(tokenList.size() == 0) {
            return null;
        }
        String token = tokenList.get(tokenList.size() - 1);
        tokenList.remove(tokenList.size() - 1);
        return token;
    }

    public ArrayList<String> getTokenList() {
        return (ArrayList<String>)tokenList;
    }

    public void clearTokens() {
        this.tokenList.clear();
    }

}
