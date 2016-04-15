package cz.jh.journal.rest.filter;

import cz.jh.journal.rest.util.ResponseFactory;
import cz.jh.journal.service.ConfigurationService;
import static cz.jh.journal.service.ConfigurationServiceKey.REST_LOG_ENABLED;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.management.ServiceNotFoundException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jan.horky
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class CallPreFilter implements ContainerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(CallPreFilter.class.getName());
    @Context
    private UriInfo uriInfo;

    @EJB
    private ConfigurationService confMan;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
            boolean logEnabled = confMan.getBoolean(REST_LOG_ENABLED);
            if (logEnabled) {
                final String url = uriInfo.getRequestUri().toString();
                String inputData = reconstructInputData(requestContext);
                ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
                if (methodInvoker != null) {
                    log.debug("Method: {}.{}\n{}",
                            methodInvoker.getGenericReturnType().getClass().getSimpleName(),
                            methodInvoker.getMethod().getName(),
                            inputData
                    );
                } else {
                    ServiceNotFoundException e = new ServiceNotFoundException("No rest service found at: " + url);
                    throw e;
                }
            }
        } catch (Exception e) {
            log.error("Internal Error in CallPostFilter", e);
            requestContext.abortWith(ResponseFactory.createResponseError(Response.Status.INTERNAL_SERVER_ERROR, "ServiceFailure", "No rest service found at: " + uriInfo.getRequestUri().toString()));
        }
    }

    private String reconstructInputData(ContainerRequestContext requestContext) throws MalformedURLException {
        final StringBuilder inputData = new StringBuilder();
        inputData.append("URL: ").append(uriInfo.getRequestUri().toURL().toString()).append("\n");
        inputData.append("Headers: \n");
        appendHeadders(inputData, requestContext.getHeaders());
        return inputData.toString();
    }

    private void appendHeadders(StringBuilder sb, MultivaluedMap<String, String> headers) {
        for (String key : headers.keySet()) {
            boolean first = true;
            sb.append(key).append(" = ");
            for (String value : headers.get(key)) {
                if (!first) {
                    sb.append(", ").append(value);
                } else {
                    sb.append(value);
                    first = false;
                }
            }
            sb.append("\n");
        }
    }

    @PostConstruct
    public void init() {

    }

}
