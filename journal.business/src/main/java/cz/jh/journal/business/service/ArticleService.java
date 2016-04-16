/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.business.service;

import cz.jh.journal.business.model.Article;
import cz.jh.journal.service.GenericService;
import java.io.File;
import java.io.InputStream;
import javax.ejb.Local;

/**
 *
 * @author jan.horky
 */
@Local
public interface ArticleService extends GenericService<Article> {

    Article createWithAttachment(Article e, InputStream docStream);

    File findAttachment(long id);

}
