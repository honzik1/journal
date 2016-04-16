/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.rest.exception;

import cz.jh.journal.rest.util.ResponseFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author jan.horky
 */
public class RootExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        return ResponseFactory.createResponseException(Response.Status.INTERNAL_SERVER_ERROR, e, "Internal Error: " + e.getMessage());
    }

}
