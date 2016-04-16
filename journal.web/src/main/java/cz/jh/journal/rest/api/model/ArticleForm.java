/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.rest.api.model;

import cz.jh.journal.Const;
import cz.jh.journal.business.model.Article;
import java.io.InputStream;
import java.text.ParseException;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.PartType;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jan.horky
 */
public class ArticleForm {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ArticleForm.class.getName());

    @FormParam("title")
    @PartType(MediaType.TEXT_PLAIN)
    private String title;
    @FormParam("authors")
    @PartType(MediaType.TEXT_PLAIN)
    private String authors;
    @FormParam("abst")
    @PartType(MediaType.TEXT_PLAIN)
    private String abst;
    @FormParam("doi")
    @PartType(MediaType.TEXT_PLAIN)
    private String doi;
    @FormParam("publishDate")
    @PartType(MediaType.TEXT_PLAIN)
    private String publishDate;
    @FormParam("copyright")
    @PartType(MediaType.TEXT_PLAIN)
    private String copyright;
    @FormParam("fileId")
    @PartType(MediaType.TEXT_PLAIN)
    private String fileId;

    @FormParam("attachment")
    @PartType("application/*")
    private InputStream attachment;

    public Article getArticle() {
        final Article article = new Article();
        article.setTitle(title);
        article.setAuthors(authors);
        article.setAbst(abst);
        article.setDoi(doi);
        try {
            article.setPublishDate(Const.XML_DATE_TIME_FORMAT().parse(publishDate));
        } catch (ParseException ex) {
            log.warn("Failed to parse Article.publishDate: {}", publishDate);
        }
        article.setCopyright(copyright);
        article.setFileId(fileId);
        return article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getAbst() {
        return abst;
    }

    public void setAbst(String abst) {
        this.abst = abst;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public InputStream getAttachment() {
        return attachment;
    }

    public void setAttachment(InputStream attachment) {
        this.attachment = attachment;
    }
}
