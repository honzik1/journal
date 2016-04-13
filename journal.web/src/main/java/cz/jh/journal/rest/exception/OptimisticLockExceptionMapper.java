/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.rest.exception;

import cz.jh.journal.rest.util.ResponseFactory;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author jan.horky
 */
public class OptimisticLockExceptionMapper implements ExceptionMapper<OptimisticLockException> {

    @Override
    public Response toResponse(OptimisticLockException e) {
        return ResponseFactory.createResponseException(Response.Status.CONFLICT, e, "Mid-Air collision. Someone else changed the data you edit.");
    }

}
