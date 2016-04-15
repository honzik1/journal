/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.util;

import cz.jh.journal.service.ConfigurationService;
import static cz.jh.journal.service.ConfigurationServiceKey.VERSION_NUMBER;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author jan.horky
 */
public class EjbUtil {

    public static <T> T findBean(Class<T> clazz, String module, ConfigurationService conf) throws NamingException {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context jndiContext = new InitialContext(jndiProperties);
        return (T) jndiContext.lookup("java:app/" + module + "-" + conf.getString(VERSION_NUMBER) + "/" + clazz.getSimpleName() + "Impl");
    }

    public static <T> T findBeanWithinModule(Class<T> clazz) throws NamingException {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context jndiContext = new InitialContext(jndiProperties);
        return (T) jndiContext.lookup("java:module/" + clazz.getSimpleName());
    }
}
