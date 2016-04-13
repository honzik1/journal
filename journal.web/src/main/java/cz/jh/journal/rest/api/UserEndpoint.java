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
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    @Produces(JSON_MT)
    @PermitAll
    public Response login(@NotNull String username, @NotNull String password) {
        String passHash = DigestUtils.shaHex(password);
        final String jwt = service.login(username, passHash);
        if (jwt != null) {
            return ResponseFactory.createResponseOK(new Token(jwt));
        } else {
            return ResponseFactory.createResponseBadCredentials();
        }
    }

    @POST
    @Path("/logout")
    @RolesAllowed(value = {UserRole.AUTH})
    public Response logout() {
        service.logout();
        return ResponseFactory.createResponseOK();
    }

}
