/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.rest.api;

import static cz.jh.journal.Const.JSON_MT;
import static cz.jh.journal.Const.USER_API_BASE;
import cz.jh.journal.business.model.User;
import cz.jh.journal.business.model.UserRole;
import cz.jh.journal.business.service.UserService;
import cz.jh.journal.rest.api.model.Token;
import cz.jh.journal.rest.util.ResponseFactory;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author jan.horky
 */
@Path(USER_API_BASE)
public class UserEndpoint extends GenericEndpoint<User, UserService> {

    @POST
    @Path("/login")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON)
    @PermitAll
    public Response login(@FormParam("email") @NotNull String email, @FormParam("password") @NotNull String password, @Context HttpServletRequest req) {
        final String jwt = service.login(email, DigestUtils.shaHex(password));
        if (jwt != null) {
            return ResponseFactory.createResponseOK(new Token(jwt));
        } else {
            return ResponseFactory.createResponseBadCredentials();
        }
    }

    @POST
    @Path("/logout")
    @RolesAllowed(value = {UserRole.AUTH, UserRole.EDIT, UserRole.SUBS})
    public Response logout() {
        service.logout();
        return ResponseFactory.createResponseOK();
    }

    @POST
    @Path("/new-account")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON)
    @PermitAll
    public Response createNewAccount(
            @FormParam("firstName") @NotNull String firstName,
            @FormParam("lastName") @NotNull String lastName,
            @FormParam("email") @NotNull String email,
            @FormParam("password") @NotNull String password,
            @Context HttpServletRequest req) {

        final User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(DigestUtils.shaHex(password));
        user.setRole(UserRole.SUBSCRIBER);
        user.setActive(true);
        service.create(user);
        return ResponseFactory.createResponseOK();
    }

    @PUT
    @Path("/{id:[1-9][0-9]*}")
    @Consumes(JSON_MT)
    @Produces(JSON_MT)
    @RolesAllowed(value = {UserRole.EDIT, UserRole.SUBS})
    @Override
    public Response update(@PathParam("id")
            @NotNull Long id, @NotNull User entity) {
        User logged = (User) context.get(LOGGED_USER_KEY);
        if (UserRole.SUBSCRIBER.equals(logged.getRole()) && !logged.getId().equals(id)) {
            return ResponseFactory.createResponseContentNotAllowed();
        }

        entity.setPassword(DigestUtils.shaHex(entity.getPassword()));
        return super.update(id, entity);
    }

}
