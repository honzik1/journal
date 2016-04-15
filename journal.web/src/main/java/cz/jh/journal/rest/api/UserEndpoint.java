/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.rest.api;

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
import javax.ws.rs.Path;
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
        String passHash = DigestUtils.shaHex(password);
        final String jwt = service.login(email, passHash);
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

}
