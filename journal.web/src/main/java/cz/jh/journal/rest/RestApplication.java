package cz.jh.journal.rest;

import static cz.jh.journal.Const.API_BASE_V1;
import cz.jh.journal.rest.api.ArticleEndpoint;
import cz.jh.journal.rest.api.JournalEndpoint;
import cz.jh.journal.rest.api.UserEndpoint;
import cz.jh.journal.rest.exception.ConstraintViolationExceptionMapper;
import cz.jh.journal.rest.exception.EntityNotFoundExceptionMapper;
import cz.jh.journal.rest.exception.OptimisticLockExceptionMapper;
import cz.jh.journal.rest.exception.RootExceptionMapper;
import cz.jh.journal.rest.filter.CallPostFilter;
import cz.jh.journal.rest.filter.CallPreFilter;
import cz.jh.journal.rest.filter.SecurityPreProcessFilter;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jboss.resteasy.plugins.providers.multipart.MimeMultipartProvider;

@ApplicationPath(API_BASE_V1)
public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(JacksonConfig.class);
        resources.add(SecurityPreProcessFilter.class);
        resources.add(CallPreFilter.class);
        resources.add(CallPostFilter.class);
        resources.add(JournalEndpoint.class);
        resources.add(UserEndpoint.class);
        resources.add(ArticleEndpoint.class);
        resources.add(EntityNotFoundExceptionMapper.class);
        resources.add(OptimisticLockExceptionMapper.class);
        resources.add(ConstraintViolationExceptionMapper.class);
        resources.add(RootExceptionMapper.class);
        resources.add(org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider.class);
        resources.add(org.codehaus.jackson.jaxrs.JacksonJsonProvider.class);
        resources.add(MimeMultipartProvider.class);

        return resources;
    }

}
