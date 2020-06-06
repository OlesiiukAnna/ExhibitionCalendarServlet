package ua.external.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface ServletHandler {

    String handle(HttpServletRequest request, HttpServletResponse response);

}
