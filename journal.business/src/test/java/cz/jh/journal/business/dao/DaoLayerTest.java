/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.business.dao;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import cz.jh.journal.Const;
import cz.jh.journal.business.model.Discipline;
import cz.jh.journal.business.model.Frequency;
import cz.jh.journal.business.model.Journal;
import cz.jh.journal.business.model.User;
import cz.jh.journal.business.model.UserRole;
import java.text.ParseException;
import javax.inject.Inject;
import javax.transaction.Transactional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author jan.horky
 */
public class DaoLayerTest extends AbstractDaoTesting {

    @Inject
    private JournalDao jDao;

    @Inject
    private DisciplineDao dDao;

    @Inject
    private UserDao uDao;

    @Test
    @Transactional
    public void testBasicGenericCrud() {
        final Journal j = new Journal();
        j.setTitle("American Journal of Transplantation");
        jDao.saveAndFlush(j);

        assertEquals(TEST_USER_ID, j.getCreatedBy());

        assertNotNull(jDao.findOne(j.getId()));

        assertNotNull(jDao.findOne("e", ImmutableList.of("e.title like :title"), ImmutableMap.of("title", "%Transplantation")));

        jDao.delete(j.getId());

        assertNull(jDao.findOne(j.getId()));
    }

    @Test
    @Transactional
    public void testCreateRealJournal() throws ParseException {
        final Discipline discipline = new Discipline("Organ transplantation");
        dDao.save(discipline);

        final User user = new User();
        user.setFirstName("Allan D.");
        user.setLastName("Kirk");
        user.setEmail("adk@am-journal.org");
        user.setRole(UserRole.EDITOR);
        uDao.save(user);

        final Journal j = new Journal();
        j.setTitle("American Journal of Transplantation");
        j.setAbbrTitle("Am. J. Transplant.");
        j.setDiscipline(discipline);
        j.setLanguage("English");
        j.setEditedBy(user);
        j.setPublisher("Wiley-Blackwell on behalf of the American Society of Transplant Surgeons and the American Society of Transplantation (United States)");
        j.setPublicFrom(Const.XML_DATE_FORMAT().parse("2001-01-01"));
        j.setFrequency(Frequency.MONTHLY);
        j.setImpactFactor(5.683f);
        j.setImpactFactorYear(2014);
        j.setIssn("1600-6135");
        j.setLccn("2001229844");
        j.setCoden("AJTMBR");
        j.setOclc("47727849");
        jDao.saveAndFlush(j);

        assertEquals("Kirk", uDao.findBy("lastName", "Kirk").getLastName());

        assertEquals(1, dDao.findAll().size());
    }

}
