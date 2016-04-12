package cz.jh.journal;

import cz.jh.journal.util.NullSafeDateFormat;

/**
 *
 * @author jan.horky
 */
public class Const {

    public static final int CONFIGURATION_REFRESH_DELAY = 60000;
    public static final String DEFAULT_CONFIGURATION_FILE = "conf/journal.default.properties";
    public static final String JBOSS_CONF_DIR_SYS_PROP = "jboss.server.config.dir";

    /**
     * Date Time Patterns
     */
    public static final String CZECH_DATE_PATTERN = "dd.MM.yyyy";
    public static final String CZECH_DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss";
    public static final String XML_DATE_PATTERN = "yyyy-MM-dd";
    public static final String XML_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DB_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static synchronized NullSafeDateFormat XML_DATE_FORMAT() {
        return new NullSafeDateFormat(XML_DATE_PATTERN);
    }

    public static synchronized NullSafeDateFormat CZECH_DATE_FORMAT() {
        return new NullSafeDateFormat(CZECH_DATE_PATTERN);
    }

    public static synchronized NullSafeDateFormat CZECH_DATE_TIME_FORMAT() {
        return new NullSafeDateFormat(CZECH_DATE_TIME_PATTERN);
    }

    public static synchronized NullSafeDateFormat XML_DATE_TIME_FORMAT() {
        return new NullSafeDateFormat(XML_DATE_TIME_PATTERN);
    }

    public static synchronized NullSafeDateFormat DB_DATE_TIME_FORMAT() {
        return new NullSafeDateFormat(DB_DATE_TIME_PATTERN);
    }

}
