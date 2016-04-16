/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.rest.exception;

import com.google.common.collect.Maps;
import cz.jh.journal.rest.util.ResponseFactory;
import java.util.Map;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author jan.horky
 */
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        Map<String, String> messages = Maps.newHashMapWithExpectedSize(e.getConstraintViolations().size());
        for (ConstraintViolation c : e.getConstraintViolations()) {
            messages.put(c.getPropertyPath().toString(), c.getMessage());
        }
        return ResponseFactory.createResponseException(Response.Status.BAD_REQUEST, e, "Internal Error: " + e.getMessage());
    }

}
