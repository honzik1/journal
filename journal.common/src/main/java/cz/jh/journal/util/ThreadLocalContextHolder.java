/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds map for current thread
 *
 * @author jan.horky
 */
public class ThreadLocalContextHolder {

    private static final ThreadLocal<Map<String, Object>> THREAD_WITH_CONTEXT = new ThreadLocal<>();

    public void put(String key, Object payload) {
        if (THREAD_WITH_CONTEXT.get() == null) {
            THREAD_WITH_CONTEXT.set(new HashMap<String, Object>());
        }
        THREAD_WITH_CONTEXT.get().put(key, payload);
    }

    public void putAll(Map<String, Object> map) {
        if (THREAD_WITH_CONTEXT.get() == null) {
            THREAD_WITH_CONTEXT.set(new HashMap<String, Object>());
        }
        THREAD_WITH_CONTEXT.get().putAll(map);
    }

    public Object get(String key) {
        final Map<String, Object> ctx = THREAD_WITH_CONTEXT.get();
        if (ctx != null) {
            return ctx.get(key);
        } else {
            return null;
        }
    }

    public void cleanupThread() {
        THREAD_WITH_CONTEXT.remove();
    }

    public Object removeKey(String key) {
        if (THREAD_WITH_CONTEXT.get() != null) {
            return THREAD_WITH_CONTEXT.get().remove(key);
        }
        return null;
    }

}
