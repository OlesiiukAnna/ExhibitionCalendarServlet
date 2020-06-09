package ua.external.servlet.handler.post;

import ua.external.servlet.handler.ServletHandler;
import ua.external.util.dto.DataForTicketOrder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static ua.external.servlet.handler.Paths.INDEX_PAGE;

public class Logout implements ServletHandler {

    private List<DataForTicketOrder> dataForTicketOrders;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        dataForTicketOrders = (List<DataForTicketOrder>) request.getSession().getAttribute("dataForTicketsOrder");
        dataForTicketOrders.clear();
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("email");
        request.getSession().removeAttribute("password");
        request.getSession().removeAttribute("role");
        request.getSession().removeAttribute("dataForTicketsOrder");
        request.getSession().setAttribute("userInSystem", false);
        request.getSession().invalidate();
        return "redirect:" + INDEX_PAGE;
    }
}
