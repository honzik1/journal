package cz.jh.journal.service;

/**
 * Keys to configuration properties file.
 *
 * @author jan.horky
 */
public interface ConfigurationServiceKey {

    public static final String KEY_PREFIX = "cz.jh.journal";

    public static final String VERSION_NUMBER = KEY_PREFIX + ".version";

    public static final String STORAGE_DEPTH = KEY_PREFIX + ".storage.depth";
    public static final String STORAGE_ROOT = KEY_PREFIX + ".storage.root";

    public static final String REST_LOG_ENABLED = KEY_PREFIX + ".rest.logging.enable";
    public static final String JWT_SECRET = KEY_PREFIX + ".rest.jwt.secret";
    public static final String JWT_EXPIRATION = KEY_PREFIX + ".rest.jwt.expiration";
}
