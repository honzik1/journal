/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jboss.resteasy.jose.jws.JWSBuilder;
import org.jboss.resteasy.jose.jws.JWSInput;
import org.jboss.resteasy.jose.jws.crypto.HMACProvider;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 *
 * @author jan.horky
 */
public class JwtTokenUtil {

    private static final String SECRET_CHARSET = "US-ASCII";

    public static Token createToken(Long userId, int expiration, String secret, String issuer) {
        final String uuid = UUID.randomUUID().toString();
        final org.jboss.resteasy.jwt.JsonWebToken jsonWebToken = new org.jboss.resteasy.jwt.JsonWebToken();
        final Date now = new Date();

        jsonWebToken
                .expiration(DateUtils.addMinutes(now, expiration).getTime())
                .id(uuid)
                .principal(Long.toString(userId))
                .issuer(issuer)
                .type("JWT")
                .issuedNow();

        String encoded;
        try {
            encoded = new JWSBuilder().contentType(MediaType.APPLICATION_JSON_TYPE).content(jsonWebToken, MediaType.APPLICATION_JSON_TYPE).hmac256(secret.getBytes(SECRET_CHARSET));
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("System charset setting is corrupted.", ex);
        }

        return new Token(encoded, uuid);
    }

    public static Long verifyToken(String jwt, String secret) {
        try {
            final ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();
            instance.getContextResolver(ObjectMapper.class, MediaType.APPLICATION_JSON_TYPE).getContext(JsonWebToken.class).setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            final JWSInput jwsInput = new JWSInput(jwt, instance);
            if (HMACProvider.verify(jwsInput, secret.getBytes(SECRET_CHARSET))) {
                final JsonWebToken token = (JsonWebToken) jwsInput.readContent(JsonWebToken.class, JsonWebToken.class, null, MediaType.APPLICATION_JSON_TYPE);
                return token.isActive() ? Long.parseLong(token.getPrincipal()) : null;
            } else {
                return null;
            }
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Java System charsets corrupted.", ex);
        }
    }

    private static boolean isActive(Map token) {
        long now = System.currentTimeMillis() / 1000L;
        long expiration = Long.parseLong((String) token.get("expiration"));
        long notBefore = Long.parseLong((String) token.get("notBefore"));

        return (!(now >= expiration) || expiration == 0) && ((now >= notBefore) || notBefore == 0);
    }

    public static class JsonWebToken extends org.jboss.resteasy.jwt.JsonWebToken {

        @JsonCreator
        public JsonWebToken() {
        }

        @JsonCreator
        public JsonWebToken(@JsonProperty("id") String id, @JsonProperty("expiration") long expiration, @JsonProperty("notBefore") long notBefore, @JsonProperty("issuedAt") long issuedAt, @JsonProperty("issuer") String issuer, @JsonProperty("audience") String audience, @JsonProperty("principal") String principal, @JsonProperty("type") String type) {
            this.id = id;
            this.expiration = expiration;
            this.notBefore = notBefore;
            this.issuedAt = issuedAt;
            this.issuer = issuer;
            this.audience = audience;
            this.principal = principal;
            this.type = type;
        }

        @JsonIgnore
        private boolean active;
        @JsonIgnore
        private boolean expired;
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
