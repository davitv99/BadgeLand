package aua.badges.security;

import aua.badges.model.Token;
import aua.badges.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

/**
 * @author davitv
 */
@Service
@Slf4j
public class SecurityService {
    @Autowired
    private TokenRepository tokenRepository;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();


    public String generateAndSaveTokenForUser(String userId) {
        Token token = getUserCurrentToken(userId);
        if(token.getToken()!=null){
            destroyToken(token);
        }
        Token newToken = new Token();
        newToken.setUserId(userId);
        newToken.setToken(generateNewToken());
        tokenRepository.save(newToken);
        return newToken.getToken();
    }


    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public Token getUserCurrentToken(String userId) {
        Optional<Token> token = tokenRepository.findByUserId(userId);
        return token.orElseGet(Token::new);
    }

    public boolean validateToken(String token) {
        return tokenRepository.findByToken(token).isPresent();
    }
    public Token getTokenByToken(String token){
        return tokenRepository.findByToken(token).get();
    }
    public void destroyToken(Token token) {
        tokenRepository.delete(token);
    }

}
