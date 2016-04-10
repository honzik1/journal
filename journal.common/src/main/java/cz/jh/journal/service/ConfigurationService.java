package cz.jh.journal.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.ejb.Local;
import org.apache.commons.configuration.Configuration;

/**
 * Configuration Service - Loads *.properties from jboss/standalone/configuration folder
 *
 * @author jan.horky
 */
@Local
public interface ConfigurationService {

    public static final String GLOBAL_JNDI_NAME = "java:app/ConfigurationService";

    boolean containsKey(String string);

    BigDecimal getBigDecimal(String string);

    BigDecimal getBigDecimal(String string, BigDecimal bd);

    BigInteger getBigInteger(String string);

    BigInteger getBigInteger(String string, BigInteger bi);

    boolean getBoolean(String string);

    boolean getBoolean(String string, boolean bln);

    Boolean getBoolean(String string, Boolean bln);

    byte getByte(String string);

    byte getByte(String string, byte b);

    Byte getByte(String string, Byte b);

    double getDouble(String string);

    double getDouble(String string, double d);

    Double getDouble(String string, Double d);

    float getFloat(String string);

    float getFloat(String string, float f);

    Float getFloat(String string, Float f);

    int getInt(String string);

    int getInt(String string, int i);

    Integer getInteger(String string, Integer intgr);

    Iterator getKeys(String string);

    Iterator getKeys();

    List getList(String string);

    List getList(String string, List list);

    long getLong(String string);

    long getLong(String string, long l);

    Long getLong(String string, Long l);

    Properties getProperties(String string);

    Object getProperty(String string);

    short getShort(String string);

    short getShort(String string, short s);

    Short getShort(String string, Short s);

    String getString(String string);

    String getString(String string, String string1);

    String[] getStringArray(String string);

    boolean isEmpty();

    Configuration subset(String string);

}
