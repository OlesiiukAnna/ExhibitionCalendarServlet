package ua.external.servlet.handler.post;

import ua.external.servlet.handler.helpers.CartStorage;
import ua.external.servlet.handler.ServletHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.external.servlet.handler.Paths.CART_PAGE;

public class CartPostDeleteTicketHandler implements ServletHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        String ticketIdToRemove = request.getParameter("ticket-id-to-delete");
        if (ticketIdToRemove != null) {
            CartStorage.removeFromCart(request, Integer.parseInt(ticketIdToRemove));
        }
        return "redirect:" + CART_PAGE;
    }
}
