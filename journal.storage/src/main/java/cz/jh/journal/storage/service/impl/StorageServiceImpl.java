/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.storage.service.impl;

import cz.jh.journal.service.ConfigurationService;
import static cz.jh.journal.service.ConfigurationServiceKey.STORAGE_DEPTH;
import static cz.jh.journal.service.ConfigurationServiceKey.STORAGE_ROOT;
import cz.jh.journal.storage.exception.StorageException;
import cz.jh.journal.storage.model.Document;
import cz.jh.journal.storage.service.StorageService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author jan.horky
 */
@Named
public class StorageServiceImpl implements StorageService {

    @Inject
    private ConfigurationService conf;

    @Override
    public void saveDocument(Document doc, InputStream data) {
        final int depth = conf.getInt(STORAGE_DEPTH);
        final String root = conf.getString(STORAGE_ROOT);

        final Path path = Paths.get(root + doc.getPath(depth));
        if (path.getParent().toFile().mkdirs()) {
            try {
                FileUtils.copyInputStreamToFile(data, path.toFile());
            } catch (IOException ex) {
                throw new StorageException("Failed to save document to storage: " + path.toString(), ex);
            }
        } else {
            throw new StorageException("Unable to create storage directory: " + path.getParent().toString());
        }
    }

    @Override
    public File readDocument(Document doc) {
        final int depth = conf.getInt(STORAGE_DEPTH);
        final String root = conf.getString(STORAGE_ROOT);

        final Path path = Paths.get(root + doc.getPath(depth));
        final File file = path.toFile();
        if (file != null && file.canRead() && file.length() > 0) {
            return file;
        } else {
            throw new StorageException("File does not exist: " + path.toString());
        }
    }
}
