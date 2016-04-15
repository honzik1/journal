/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.rest.api;

import static cz.jh.journal.Const.JSON_MT;
import cz.jh.journal.business.model.UserRole;
import cz.jh.journal.model.DBEntity;
import cz.jh.journal.rest.util.ResponseFactory;
import cz.jh.journal.rest.view.View;
import cz.jh.journal.service.GenericService;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 * Generic REST endpoint for any database entity and its service
 *
 * @author jan.horky
 * @param <Entity>
 * @param <Service>
 */
public abstract class GenericEndpoint<Entity extends DBEntity, Service extends GenericService<Entity>> {

    @EJB
    protected Service service;

    @Context
    protected HttpServletRequest request;

    @POST
    @Consumes(JSON_MT)
    @Produces(JSON_MT)
    @RolesAllowed(value = {UserRole.EDIT})
    public Response create(@NotNull Entity entity) {
        return Response.ok(service.create(entity)).build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    @Produces(JSON_MT)
    @RolesAllowed(value = {UserRole.EDIT})
    public Response deleteById(@PathParam("id") @NotNull Long id) {
        return Response.ok(service.delete(id)).build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(JSON_MT)
    @RolesAllowed(value = {UserRole.EDIT, UserRole.SUBS})
    @JsonView({View.Detail.class})
    public Response findById(@PathParam("id") @NotNull Long id) {
        return Response.ok(service.find(id)).build();
    }

    @GET
    @Produces(JSON_MT)
    @PermitAll
    @JsonView(View.Summary.class)
    public List<Entity> listAll(
            @QueryParam("start") @NotNull Integer startPosition,
            @QueryParam("max") @NotNull Integer maxResult) {
        return service.list(startPosition, maxResult);
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes(JSON_MT)
    @Produces(JSON_MT)
    @RolesAllowed(value = {UserRole.EDIT})
    public Response update(@PathParam("id")
            @NotNull Long id, @NotNull Entity entity) {
        if (!id.equals(entity.getId())) {
            return ResponseFactory.createResponseError(Response.Status.BAD_REQUEST, "CorruptedData", "Unable to update entity because IDs not corresponding.");
        }
        if (service.find(id) == null) {
            return ResponseFactory.createResponseNotFound();
        }
        service.update(entity);

        return ResponseFactory.createResponseOK();
    }

}
