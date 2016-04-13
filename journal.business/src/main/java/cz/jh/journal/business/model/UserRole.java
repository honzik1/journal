package cz.jh.journal.business.model;

/**
 * @author jan.horky
 * @version 1.0
 * @created 09-4-2016 17:38:01
 */
public enum UserRole {

    EDITOR, SUBSCRIBER, AUTHENTICATED;
    public static final String AUTH = "AUTHENTICATED";
    public static final String SUBS = "SUBSCRIBER";
    public static final String EDIT = "EDITOR";
}
