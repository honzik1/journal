/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.business.service.impl;

import static cz.jh.journal.Const.DEFAULT_EXT;
import cz.jh.journal.business.dao.ArticleDao;
import cz.jh.journal.business.model.Article;
import cz.jh.journal.business.model.User;
import cz.jh.journal.business.service.ArticleService;
import cz.jh.journal.dao.GenericDao;
import cz.jh.journal.service.impl.GenericServiceImpl;
import cz.jh.journal.storage.model.Document;
import cz.jh.journal.storage.service.StorageService;
import java.io.File;
import java.io.InputStream;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 *
 * @author jan.horky
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ArticleServiceImpl extends GenericServiceImpl<Article> implements ArticleService {

    @Inject
    private ArticleDao dao;

    @Inject
    private StorageService store;

    @Override
    protected GenericDao getRootEntityDao() {
        return dao;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Article createWithAttachment(Article e, InputStream docStream) {
        e.setOwner((User) context.get(LOGGED_USER_KEY));
        // save article
        super.create(e);
        // save attachment and update article
        final Document doc = new Document(e.getId(), DEFAULT_EXT); // TODO: defines extension
        e.setFilePath(store.saveDocument(doc, docStream));
        dao.saveOrUpdate(e);
        return e;
    }

    public File findAttachment(long id) {
        return store.readDocument(new Document(id, DEFAULT_EXT));
    }
}
