/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.util;

/**
 * During use-case processing could be lot of context specific values, using this class provides single place to store that.
 *
 * @author jan.horky
 */
public class ProcessingContext {

    public static final String LOGGED_USER_ID_KEY = "context.userId";
    public static final String LOGGED_USER_KEY = "context.user";

    protected ThreadLocalContextHolder context = new ThreadLocalContextHolder();
}
