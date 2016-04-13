package cz.jh.journal.rest.filter;

import cz.jh.journal.rest.JacksonConfig;
import cz.jh.journal.rest.util.ResponseFactory;
import cz.jh.journal.service.ConfigurationService;
import static cz.jh.journal.service.ConfigurationServiceKey.REST_LOG_ENABLED;
import cz.jh.journal.util.ProcessingContext;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.management.ServiceNotFoundException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jan.horky
 */
@Provider
public class CallPostFilter extends ProcessingContext implements ContainerResponseFilter {

    private static final Logger log = LoggerFactory.getLogger(CallPostFilter.class.getName());

    @Context
    private UriInfo uriInfo;

    @EJB
    private ConfigurationService confMan;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext response) {
        try {
            final String url = uriInfo.getRequestUri().toString();
            if (SUCCESSFUL.equals(response.getStatusInfo().getFamily())) {
                boolean logEnabled = confMan.getBoolean(REST_LOG_ENABLED);
                if (logEnabled) {
                    ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
                    String outputData = reconstructResponse(response.getEntity());
                    if (methodInvoker != null) {
                        log.debug("Method: {}.{}\nURL: {}\nOutData: {}",
                                methodInvoker.getClass().getSimpleName(),
                                methodInvoker.getMethod().getName(),
                                url,
                                outputData
                        );
                    } else {
                        ServiceNotFoundException e = new ServiceNotFoundException("No rest service found at: " + url);
                        throw e;
                    }
                }
            } else {
                log.info("NOK({}) - URL: {}",
                        response.getStatus(),
                        url
                );
            }
        } catch (Exception e) {
            log.error("Internal Error in CallPostFilter", e);
            requestContext.abortWith(ResponseFactory.createResponseError(Response.Status.INTERNAL_SERVER_ERROR, "ServiceFailure", "No rest service found at: " + uriInfo.getRequestUri().toString()));
        } finally {
            // clean all contextual data for this thread
            this.context.cleanupThread();
        }
    }

    private String reconstructResponse(Object entity) throws IOException {
        if (entity == null) {
            return "null";
        }
        if (entity instanceof String) {
            return (String) entity;
        }
        ObjectMapper mapper = null;
        try {
            mapper = new JacksonConfig().getContext(entity.getClass());
        } catch (Exception ex) {
            log.info(ex.getMessage());
            mapper = new ObjectMapper();
        }
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(entity);
    }

    @PostConstruct
    public void init() {

    }

}
