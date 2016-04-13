/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.business.dao;

import cz.jh.journal.util.ProcessingContext;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Abstract test for easy DAO layer testing and JPQL/SQL practising
 *
 * @author jan.horky
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/database-config.xml")
@Rollback(true)
@ActiveProfiles(profiles = "standard", inheritProfiles = false)
public class AbstractDaoTesting extends ProcessingContext {

    public static final Long TEST_USER_ID = 123L;

    @Before
    public void initContext() {
        // fake some user in dao tests
        context.put(LOGGED_USER_ID_KEY, TEST_USER_ID);
    }
}
