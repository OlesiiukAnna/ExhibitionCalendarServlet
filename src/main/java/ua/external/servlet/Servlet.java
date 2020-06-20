package ua.external.servlet;

import ua.external.configuration.Configuration;
import ua.external.servlet.handler.ServletHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class Servlet extends HttpServlet {
    private Map<String, ServletHandler> getHandlers;
    private Map<String, ServletHandler> postHandlers;

    private static final String SERVLET_NAME = "/exhibition-calendar";

    @Override
    public void init() throws ServletException {
        Configuration configuration = new Configuration();
        getHandlers = configuration.getGetHandlers();
        postHandlers = configuration.getPostHandlers();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, getHandlers);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, postHandlers);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response,
                                Map<String, ServletHandler> requestHandler) throws IOException, ServletException {
        String path = request.getRequestURI();
        ServletHandler handler = requestHandler.get(path.replace(SERVLET_NAME, ""));
        String page = handler.handle(request, response);
        if (page.contains("redirect")) {
            if (path.contains(SERVLET_NAME)) {
                response.sendRedirect(page.replace("redirect:", SERVLET_NAME));
            } else {
                response.sendRedirect(page.replace("redirect:", ""));
            }
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
