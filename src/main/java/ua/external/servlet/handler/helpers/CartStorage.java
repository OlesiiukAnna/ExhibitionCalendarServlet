package ua.external.servlet.handler.helpers;

import ua.external.exceptions.InvalidDataException;
import ua.external.util.dto.DataForTicketOrder;
import ua.external.util.dto.ExhibitionDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

public class CartStorage {
    private static List<DataForTicketOrder> dataForTicketOrders;
    private static int idGenerator;

    public static void addToCart(HttpServletRequest request,
                                 List<ExhibitionDto> exhibitions) throws InvalidDataException {
        request.setAttribute("exhibitions", exhibitions);

        String exhibitionId =request.getParameter("exhibition-id");
        String exhibitionName = request.getParameter("exhibition-name");
        String exhibitionHallId = request.getParameter("exhibition-hall-id");
        String wantedVisitDate = request.getParameter("visit-date");
        String ticketsQuantity = request.getParameter("wanted-tickets-quantity");
        String fullTicketPrice = request.getParameter("full-ticket-price");

        if (exhibitionId == null || exhibitionName == null || exhibitionName.isBlank() ||
                exhibitionHallId == null || wantedVisitDate == null || wantedVisitDate.isBlank() ||
                ticketsQuantity == null || fullTicketPrice == null) {
            throw new InvalidDataException();
        }

        dataForTicketOrders = (List<DataForTicketOrder>) request.getSession().getAttribute("dataForTicketsOrder");

        for (int i = 0; i < Integer.parseInt(ticketsQuantity); i++) {
            DataForTicketOrder dataForTicketOrder = new DataForTicketOrder();
            dataForTicketOrder.setIdInCart(idGenerator);
            dataForTicketOrder.setExhibitionId(Integer.parseInt(exhibitionId));
            dataForTicketOrder.setExhibitionName(exhibitionName);
            dataForTicketOrder.setExhibitionHallId(Integer.parseInt(exhibitionHallId));
            dataForTicketOrder.setWantedVisitDate(LocalDate.parse(wantedVisitDate));
            dataForTicketOrder.setTicketPrice(Integer.parseInt(fullTicketPrice));
            dataForTicketOrders.add(dataForTicketOrder);
            idGenerator++;
        }
    }

    public static void removeFromCart(HttpServletRequest request, int id) {
        dataForTicketOrders = (List<DataForTicketOrder>) request.getSession().getAttribute("dataForTicketsOrder");
        dataForTicketOrders.removeIf(data -> data.getIdInCart() == id);
//        request.getSession().removeAttribute("wanted-ticket-id");
    }
}
