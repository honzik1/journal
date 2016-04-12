/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;

/**
 * Date formatting (NPU safe)
 *
 * @author jan.horky
 */
public class NullSafeDateFormat {

    String pattern;

    public NullSafeDateFormat(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Parsovani NULL nebo prazdneho retezce nebo retezce z bilych znaku, vraci null
     *
     * @param source
     * @return
     * @throws ParseException
     */
    public synchronized Date parse(String source) throws ParseException {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        return DateUtils.parseDateStrictly(source, new String[]{pattern});
    }

    /**
     * Formatovani NULL vraci null
     *
     * @param date
     * @return
     */
    public String format(Date date) {
        if (date == null) {
            return null;
        }
        return FastDateFormat.getInstance(pattern).format(date);
    }

    /**
     * Formatovani NULL vraci null
     *
     * @param date
     * @return
     */
    public String format(Calendar date) {
        if (date == null) {
            return null;
        }
        return FastDateFormat.getInstance(pattern).format(date);
    }

    /**
     * Formatuje datum v milisekundach
     *
     * @param date
     * @return
     */
    public String format(long date) {
        return FastDateFormat.getInstance(pattern).format(date);
    }
}
