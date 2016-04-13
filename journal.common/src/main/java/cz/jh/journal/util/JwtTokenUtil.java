/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.util;

import java.util.Date;
import java.util.UUID;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang.time.DateUtils;
import org.jboss.resteasy.jose.jwe.JWEBuilder;
import org.jboss.resteasy.jose.jwe.JWEInput;
import org.jboss.resteasy.jwt.JsonWebToken;

/**
 *
 * @author jan.horky
 */
public class JwtTokenUtil {

    public static Token createToken(Long userId, int expiration, String secret, String issuer) {
        final String uuid = UUID.randomUUID().toString();
        final JsonWebToken jsonWebToken = new JsonWebToken();
        final Date now = new Date();

        jsonWebToken
                .expiration(DateUtils.addMinutes(now, expiration).getTime())
                .id(uuid)
                .principal(Long.toString(userId))
                .issuer(issuer)
                .issuedNow();

        String encoded = new JWEBuilder().content(jsonWebToken, MediaType.APPLICATION_JSON_TYPE).A128CBC_HS256().dir(secret);

        return new Token(encoded, uuid);
    }

    public static Long verifyToken(String jwt, String secret) {
        JsonWebToken token = new JWEInput(jwt).decrypt(secret).readContent(JsonWebToken.class);
        return token.isActive() ? Long.parseLong(token.getPrincipal()) : null;
    }

    public static class Token {

        private String jwt;
        private String tokenUuid;

        public Token(String jwt, String tokenUuid) {
            this.jwt = jwt;
            this.tokenUuid = tokenUuid;
        }

        public String getJwt() {
            return jwt;
        }

        public String getTokenUuid() {
            return tokenUuid;
        }
    }
}
