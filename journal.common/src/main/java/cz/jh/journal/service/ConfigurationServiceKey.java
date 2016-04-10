package cz.jh.journal.service;

/**
 * Keys to configuration properties file.
 *
 * @author jan.horky
 */
public interface ConfigurationServiceKey {

    public static final String KEY_PREFIX = "cz.jh.journal";
    public static final String STORAGE_DEPTH = KEY_PREFIX + ".storage.depth";
    public static final String STORAGE_ROOT = KEY_PREFIX + ".storage.root";
}
