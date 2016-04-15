/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.rest;

import static cz.jh.journal.Const.XML_DATE_TIME_PATTERN;
import java.text.SimpleDateFormat;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author jan.horky
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper;

    public JacksonConfig() throws Exception {

        this.objectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(XML_DATE_TIME_PATTERN);
        simpleDateFormat.setLenient(false);
        this.objectMapper.setDateFormat(simpleDateFormat);
        this.objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);
        this.objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return this.objectMapper;
    }
}
