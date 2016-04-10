/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.storage.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author jan.horky
 */
public class DocumentTest {

    /**
     * Test of getPath method, of class Document.
     */
    @Test
    public void testGetPath() {
        Document d = new Document();
        d.setId(453237L);
        d.setExtension("pdf");
        assertEquals("/06/EA/75/" + d.getId() + "." + d.getExtension(), d.getPath(1));
        assertEquals("/0006/EA75/" + d.getId() + "." + d.getExtension(), d.getPath(2));
        assertEquals("/06EA75/" + d.getId() + "." + d.getExtension(), d.getPath(3));
    }

}
