package ch.totoluto.coworkingspace.Service;

import ch.totoluto.coworkingspace.Entity.Token;
import ch.totoluto.coworkingspace.Entity.User;
import ch.totoluto.coworkingspace.Repository.RoleRepository;
import ch.totoluto.coworkingspace.Repository.TokenRepository;
import ch.totoluto.coworkingspace.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    private final TokenRepository tokenService;

    @Autowired
    public TokenService(TokenRepository tokenService) {
        this.tokenService = tokenService;
    }

    public Token createToken(Token token){
        return tokenService.save(token);
    }

    public void deleteToken(Token token){
        tokenService.delete(token);
    }

    public Token getTokenByToken(String token){
        return tokenService.findByToken(token);
    }

    public Token generateToken(User user){
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 864000000))
                .signWith(SignatureAlgorithm.HS256, "coWorkingSpaceSecretKey")
                .compact();

        Token tokenDB = new Token();
        tokenDB.setToken(token);

        createToken(tokenDB);

        return tokenDB;
    }

    public int getUserIdByToken(String token){
        return Integer.parseInt(
                Jwts.parser()
                        .setSigningKey("coWorkingSpaceSecretKey")
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject());
    }

    public boolean isTokenNotExpired(String token){
        Date expireDate = Jwts.parser()
                .setSigningKey("coWorkingSpaceSecretKey")
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expireDate.after(new Date());
    }
}
