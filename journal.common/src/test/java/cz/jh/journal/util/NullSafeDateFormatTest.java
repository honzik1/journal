/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.util;

import cz.jh.journal.Const;
import java.util.Calendar;
import java.util.Date;
import org.junit.Assert;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author jan.horky
 */
public class NullSafeDateFormatTest {

    /**
     * Test of parse method, of class NullSafeDateFormat.
     */
    @Test
    public void testParse() throws Exception {
        final NullSafeDateFormat f = Const.XML_DATE_FORMAT();

        final String stringDate = "2014-01-02";
        final Date date = f.parse(stringDate);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        Assert.assertEquals(stringDate, f.format(date));
        Assert.assertEquals(stringDate, f.format(date.getTime()));
        Assert.assertEquals(stringDate, f.format(cal));
        assertNull(f.parse(null));
        assertNull(f.format((Date) null));

    }

}
