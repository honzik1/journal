/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.storage.service;

import cz.jh.journal.storage.model.Document;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author jan.horky
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/application-config.xml")
public class StorageServiceTest {

    @Inject
    private StorageService store;

    /**
     * Test of saveDocument method, of class StorageService.
     */
    @Test
    public void testSaveAndReadDocument() throws FileNotFoundException, IOException {
        FileUtils.deleteDirectory(new File("target/storage"));
        final Document doc = new Document(3264, "pdf");
        store.saveDocument(doc, new FileInputStream("src/test/resources/test-data/health-bingo.pdf"));
        final File file = store.readDocument(doc);
        assertEquals(128291, file.length());
    }
}
