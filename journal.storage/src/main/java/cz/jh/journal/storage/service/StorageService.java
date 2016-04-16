/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.storage.service;

import cz.jh.journal.storage.model.Document;
import java.io.File;
import java.io.InputStream;

/**
 *
 * @author jan.horky
 */
public interface StorageService {

    String saveDocument(Document doc, InputStream data);

    File readDocument(Document doc);
}
