package dev.friox.springsecurityjwt.security.jwt;

import dev.friox.springsecurityjwt.exception.ApiException;
import dev.friox.springsecurityjwt.exception.JwtErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final SecretKey key;

    public JwtTokenProvider(@Value("${jwt.secret}") String jwtSecretKey) {
        this.key = Jwts.SIG.HS256.key().build();
        String keyBase64 = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("JWT Secret Key initialized: " + keyBase64);
    }

    public Authentication getAuthentication(String accessToken) {
        validateToken(accessToken);
        Claims claims = parseClaims(accessToken);
        Object authClaims = claims.get("auth");
        if (authClaims == null) throw new RuntimeException("권한 정보 없음");
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authClaims.toString());
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public JwtToken generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        String accessToken = Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .expiration(new Date(now + 86400000))
                .signWith(key)
                .compact();
        String refreshToken = Jwts.builder()
                .expiration(new Date(now + 86400000))
                .signWith(key)
                .compact();
        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean validateToken(String token) {
        getSignedClaims(token);
        return true;
    }

    private Claims parseClaims(String accessToken) {
        Jws<Claims> jws = getSignedClaims(accessToken);
        return jws.getPayload();
    }

    private Jws<Claims> getSignedClaims(String token) throws ApiException {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new ApiException(JwtErrorCode.EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new ApiException(JwtErrorCode.UNSUPPORTED);
        } catch (MalformedJwtException e) {
            throw new ApiException(JwtErrorCode.MALFORMED);
        } catch (SignatureException e) {
            throw new ApiException(JwtErrorCode.SIGNATURE);
        } catch (PrematureJwtException e) {
            throw new ApiException(JwtErrorCode.PREMATURE);
        } catch (ClaimJwtException e) {
            throw new ApiException(JwtErrorCode.CLAIM);
        }
    }
}
