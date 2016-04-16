/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.rest.util;

import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 *
 * @author jan.horky
 */
public class ResponseFactory {

    public static Response createResponseError(Status status, String code, String message) {
        Response response = Response
                .status(status)
                .entity(createErrorJSON(code, message))
                .type(MediaType.APPLICATION_JSON)
                .build();
        return response;
    }

    public static Response createResponseError(Status status, String code, Map<String, String> message) {
        Response response = Response
                .status(status)
                .entity(createErrorJSON(code, message))
                .type(MediaType.APPLICATION_JSON)
                .build();
        return response;
    }

    public static Response createResponseException(Status status, Exception e, String message) {
        return createResponseError(status, e.getClass().getSimpleName().replace("Exception", ""), message);
    }

    public static Response createResponseAccessDenied() {
        return createResponseError(Status.UNAUTHORIZED, "UserUnauthorized", "Check your permissions or contact your administrator.");
    }

    public static Response createResponseContentNotAllowed() {
        return createResponseError(Status.UNAUTHORIZED, "ContentNotAllowed", "You are probably not owner of requested content.");
    }

    public static Response createResponseNotFound() {
        return createResponseError(Status.NOT_FOUND, "NotFound", "Entity was not found.");
    }

    public static String createErrorJSON(String code, String message) {
        org.codehaus.jackson.node.ObjectNode n = new ObjectNode(JsonNodeFactory.instance);
        n.put("code", code);
        n.put("message", message);
        return n.toString();
    }

    public static String createErrorJSON(String code, Map<String, String> messages) {
        org.codehaus.jackson.node.ObjectNode n = new ObjectNode(JsonNodeFactory.instance);
        n.put("code", code);
        if (!messages.isEmpty()) {
            final ObjectNode msgs = n.objectNode();
            n.put("messages", msgs);
            for (Map.Entry<String, String> message : messages.entrySet()) {
                msgs.put(message.getKey(), message.getValue());
            }
        }
        return n.toString();
    }

    public static Response createResponseOK() {
        return createResponseOK(null);
    }

    public static Response createResponseCreated(Object obj, String location) {
        return Response
                .status(Response.Status.CREATED)
                .entity(obj)
                .type(MediaType.APPLICATION_JSON)
                .header("Location", location)
                .build();
    }

    public static Response createResponseOK(Object obj, String mediaTypeString) {
        final Response response;
        if (obj != null) {
            response = Response.status(Response.Status.OK).entity(obj).type(mediaTypeString).build();
        } else {
            response = Response.status(Response.Status.NO_CONTENT).build();
        }
        return response;
    }

    public static Response createResponseOK(Object obj) {
        return createResponseOK(obj, MediaType.APPLICATION_JSON);
    }

    public static Response createResponseBadCredentials() {
        return createResponseError(Status.UNAUTHORIZED, "UserUnauthorized", "Invalid credentials.");
    }

}
