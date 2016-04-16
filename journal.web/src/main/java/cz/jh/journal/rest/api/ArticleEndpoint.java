/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.rest.api;

import static cz.jh.journal.Const.ARTICLE_API_BASE;
import cz.jh.journal.business.model.Article;
import cz.jh.journal.business.model.Journal;
import cz.jh.journal.business.model.UserRole;
import cz.jh.journal.business.service.ArticleService;
import cz.jh.journal.rest.api.model.ArticleForm;
import cz.jh.journal.rest.util.ResponseFactory;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

/**
 *
 * @author jan.horky
 */
@Path(ARTICLE_API_BASE)
public class ArticleEndpoint extends GenericEndpoint<Article, ArticleService> {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed(value = {UserRole.EDIT})
    public Response create(@PathParam("journalId") long journalId, @MultipartForm ArticleForm input) {
        Article article = input.getArticle();
        article.setJournal(new Journal(journalId));

        article = service.createWithAttachment(
                article,
                input.getAttachment());

        return ResponseFactory.createResponseOK(article);
    }

    @GET
    @Path("/{articleId:[1-9][0-9]*}/attachment")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response findAttachmentById(@PathParam("articleId") Long id) {
        final Article article = service.find(id);
        if (article == null) {
            return ResponseFactory.createResponseNotFound();
        }
        ResponseBuilder response = Response.ok((Object) service.findAttachment(id));
        response.header("Content-Disposition", "attachment; filename=" + article.getFileId());
        return response.build();
    }
}
