package ua.external.servlet.handler.get;

import ua.external.servlet.handler.ServletHandler;
import ua.external.util.enums.TicketType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.external.servlet.handler.Paths.CART_JSP;

public class CartGet implements ServletHandler {

    public CartGet() {
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        TicketType [] ticketTypes = TicketType.values();
        request.getSession().setAttribute("ticketTypes", ticketTypes);
        return CART_JSP;
    }
}
