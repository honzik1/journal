package cz.jh.journal.service.impl;

import cz.jh.journal.Const;
import static cz.jh.journal.Const.CONFIGURATION_REFRESH_DELAY;
import static cz.jh.journal.Const.DEFAULT_CONFIGURATION_FILE;
import cz.jh.journal.service.ConfigurationService;
import static cz.jh.journal.service.ConfigurationService.GLOBAL_JNDI_NAME;
import java.io.File;
import java.io.FilenameFilter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.interceptor.ExcludeDefaultInterceptors;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;

/**
 * Configuration Service - Loads *.properties from jboss/standalone/configuration folder
 *
 * @author jan.horky
 */
@Singleton
@Startup
@EJB(name = GLOBAL_JNDI_NAME, beanInterface = ConfigurationService.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@ExcludeDefaultInterceptors
@Named
@ApplicationScoped
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ConfigurationServiceImpl.class.getName());

    private CompositeConfiguration config;

    @PostConstruct
    protected void init() {
        config = new CompositeConfiguration();
        // reading system configuration
        config.addConfiguration(new SystemConfiguration());

        File folder = new File(System.getProperty(Const.JBOSS_CONF_DIR_SYS_PROP, "."));
        File[] listOfFiles = folder.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("properties");
            }
        });

        for (File file : listOfFiles) {
            if (file.isFile()) {
                log.info("Processing configuration file: {}", file.getAbsolutePath());
                try {
                    final PropertiesConfiguration singleConfig = new PropertiesConfiguration(file);
                    final FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
                    // check if configuration was changed
                    strategy.setRefreshDelay(CONFIGURATION_REFRESH_DELAY);
                    singleConfig.setReloadingStrategy(strategy);
                    // add configuration file to composite
                    config.addConfiguration(singleConfig);
                } catch (ConfigurationException ex) {
                    log.warn("Reading configuration file '{}' failed. {}", file.getAbsolutePath(), ExceptionUtils.getFullStackTrace(ex));
                }
            }
        }

        // loading default configuration
        try {
            final String pathToConfiguration = StringUtils.repeat("../", 6).concat(DEFAULT_CONFIGURATION_FILE);
            config.addConfiguration(new PropertiesConfiguration(this.getClass().getResource(pathToConfiguration)));
        } catch (ConfigurationException ex) {
            log.warn("Reading configuration file '{}' failed. {}", DEFAULT_CONFIGURATION_FILE, ExceptionUtils.getFullStackTrace(ex));
        }

        // loading test configuration
        try {
            File testConf = new File("target/test-classes/" + DEFAULT_CONFIGURATION_FILE);
            if (testConf.exists()) {
                config.addConfiguration(new PropertiesConfiguration(testConf));
            }
        } catch (ConfigurationException ex) {
            // nothing happend
        }

    }

    @Override
    public Configuration subset(String string) {
        return config.subset(string);
    }

    @Override
    public boolean isEmpty() {
        return config.isEmpty();
    }

    @Override
    public boolean containsKey(String string) {
        return config.containsKey(string);
    }

    @Override
    public Object getProperty(String string) {
        return config.getProperty(string);
    }

    @Override
    public Iterator getKeys(String string) {
        return config.getKeys(string);
    }

    @Override
    public Iterator getKeys() {
        return config.getKeys();
    }

    @Override
    public Properties getProperties(String string) {
        return config.getProperties(string);
    }

    @Override
    public boolean getBoolean(String string) {
        return config.getBoolean(string);
    }

    @Override
    public boolean getBoolean(String string, boolean bln) {
        return config.getBoolean(string, bln);
    }

    @Override
    public Boolean getBoolean(String string, Boolean bln) {
        return config.getBoolean(string, bln);
    }

    @Override
    public byte getByte(String string) {
        return config.getByte(string);
    }

    @Override
    public byte getByte(String string, byte b) {
        return config.getByte(string, b);
    }

    @Override
    public Byte getByte(String string, Byte b) {
        return config.getByte(string, b);
    }

    @Override
    public double getDouble(String string) {
        return config.getDouble(string);
    }

    @Override
    public double getDouble(String string, double d) {
        return config.getDouble(string, d);
    }

    @Override
    public Double getDouble(String string, Double d) {
        return config.getDouble(string, d);
    }

    @Override
    public float getFloat(String string) {
        return config.getFloat(string);
    }

    @Override
    public float getFloat(String string, float f) {
        return config.getFloat(string, f);
    }

    @Override
    public Float getFloat(String string, Float f) {
        return config.getFloat(string, f);
    }

    @Override
    public int getInt(String string) {
        return config.getInt(string);
    }

    @Override
    public int getInt(String string, int i) {
        return config.getInt(string, i);
    }

    @Override
    public Integer getInteger(String string, Integer intgr) {
        return config.getInteger(string, intgr);
    }

    @Override
    public long getLong(String string) {
        return config.getLong(string);
    }

    @Override
    public long getLong(String string, long l) {
        return config.getLong(string, l);
    }

    @Override
    public Long getLong(String string, Long l) {
        return config.getLong(string, l);
    }

    @Override
    public short getShort(String string) {
        return config.getShort(string);
    }

    @Override
    public short getShort(String string, short s) {
        return config.getShort(string, s);
    }

    @Override
    public Short getShort(String string, Short s) {
        return config.getShort(string, s);
    }

    @Override
    public BigDecimal getBigDecimal(String string) {
        return config.getBigDecimal(string);
    }

    @Override
    public BigDecimal getBigDecimal(String string, BigDecimal bd) {
        return config.getBigDecimal(string, bd);
    }

    @Override
    public BigInteger getBigInteger(String string) {
        return config.getBigInteger(string);
    }

    @Override
    public BigInteger getBigInteger(String string, BigInteger bi) {
        return config.getBigInteger(string, bi);
    }

    @Override
    public String getString(String string) {
        return config.getString(string);
    }

    @Override
    public String getString(String string, String string1) {
        return config.getString(string, string1);
    }

    @Override
    public String[] getStringArray(String string) {
        return config.getStringArray(string);
    }

    @Override
    public List getList(String string) {
        return config.getList(string);
    }

    @Override
    public List getList(String string, List list) {
        return config.getList(string, list);
    }
}
