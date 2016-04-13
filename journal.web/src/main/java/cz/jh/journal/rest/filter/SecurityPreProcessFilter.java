package cz.jh.journal.rest.filter;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import cz.jh.journal.business.model.User;
import cz.jh.journal.business.service.UserService;
import cz.jh.journal.rest.util.ResponseFactory;
import cz.jh.journal.service.ConfigurationService;
import cz.jh.journal.util.EjbUtil;
import cz.jh.journal.util.ProcessingContext;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jan.horky
 */
@Provider
@Priority(Priorities.AUTHORIZATION)
public class SecurityPreProcessFilter extends ProcessingContext implements ContainerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(SecurityPreProcessFilter.class.getName());

    @Inject
    private ConfigurationService conf;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        try {
            ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");

            Method method = methodInvoker.getMethod();

            //Access allowed for all
            if (method.isAnnotationPresent(PermitAll.class)) {
                return;
            }
            //Access denied for all
            if (method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(ResponseFactory.createResponseAccessDenied());
                return;
            }

            final String jwtToken = parseToken(requestContext);
            if (jwtToken == null) {
                requestContext.abortWith(ResponseFactory.createResponseAccessDenied());
                return;
            }

            // check token, load user, setup context
            UserService userService = EjbUtil.findBean(UserService.class, "journal.business", conf);
            final User user = userService.verifyToken(jwtToken);

            // check user access for called method
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                final String[] allowedRoles = method.getAnnotation(RolesAllowed.class).value();
                if (allowedRoles.length > 0) {
                    if (!Lists.newArrayList(allowedRoles).contains(user.getRole().name())) {
                        requestContext.abortWith(ResponseFactory.createResponseAccessDenied());
                        return;
                    }
                }
            }

            context.put(LOGGED_USER_ID_KEY, user.getId());
            context.put(LOGGED_USER_KEY, user);

        } catch (Exception e) {
            log.error("Internal Error in Authentication", e);
            requestContext.abortWith(ResponseFactory.createResponseError(Response.Status.INTERNAL_SERVER_ERROR, "AuthFailure", "Unable to complete authentication proccess."));
        }
    }

    private String parseToken(ContainerRequestContext requestContext) {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader != null) {
            final List<String> auth = Splitter.on(" ").omitEmptyStrings().trimResults().splitToList(authHeader);
            if (auth.size() == 2 && "Bearer".equals(auth.get(0))) {
                return auth.get(1);
            }
        }
        requestContext.abortWith(ResponseFactory.createResponseAccessDenied());
        return null;
    }

    @PostConstruct
    public void init() {

    }
}
