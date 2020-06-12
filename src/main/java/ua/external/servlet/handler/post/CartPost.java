package ua.external.servlet.handler.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.external.exceptions.InvalidDataException;
import ua.external.exceptions.exhibition.NoSuchExhibitionException;
import ua.external.exceptions.user.NoSuchUserException;
import ua.external.exceptions.ticket.TicketsRunOutForTheDateException;
import ua.external.service.interfaces.TicketService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.util.dto.DataForTicketOrder;
import ua.external.util.dto.TicketDto;
import ua.external.util.dto.UserDto;
import ua.external.util.enums.TicketType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static ua.external.servlet.handler.Paths.CART_JSP;

public class CartPost implements ServletHandler {
    private final static Logger logger = LoggerFactory.getLogger(CartPost.class);

    private TicketService<TicketDto> ticketService;
    private List<DataForTicketOrder> dataForTicketOrders;

    public CartPost(TicketService<TicketDto> ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        dataForTicketOrders = (List<DataForTicketOrder>) request.getSession().getAttribute("dataForTicketsOrder");

        boolean ticketsAreOrdered = false;
        if (!dataForTicketOrders.isEmpty()) {
            ticketsAreOrdered = saveTickets(request);
        }
        if (ticketsAreOrdered) {
            request.setAttribute("ticketsOrdered", true);
            dataForTicketOrders.clear();
        }
        return CART_JSP;
    }

    private boolean saveTickets(HttpServletRequest request) {
        String[] ticketsIdTOSave = request.getParameterValues("cart-ticket-id");
        String[] ticketTypes = request.getParameterValues("ticket-type");
        if (ticketsIdTOSave.length > 0 && ticketTypes.length > 0) {
            UserDto user = (UserDto) request.getSession().getAttribute("user");
            List<TicketDto> ticketDtos = new CopyOnWriteArrayList<>();
            for (int i = 0; i < ticketsIdTOSave.length && i < ticketTypes.length && i < dataForTicketOrders.size(); i++) {
                DataForTicketOrder data = dataForTicketOrders.get(i);
                TicketDto ticketDto = new TicketDto();
                ticketDto.setVisitDate(data.getWantedVisitDate());
                ticketDto.setTicketType(TicketType.valueOf(ticketTypes[i]));
                ticketDto.setUserId(user.getId());
                ticketDto.setExhibitionId(data.getExhibitionId());
                ticketDtos.add(ticketDto);
            }

            try {
                ticketService.saveListOfTickets(ticketDtos);
                return true;
            } catch (InvalidDataException e) {
                request.setAttribute("isInvalidData", true);
                logger.warn("Invalid input data ", e);
            } catch (TicketsRunOutForTheDateException e) {
                request.setAttribute("areTicketsRunOut", true);
                logger.warn("Tickets run out for this day ", e);
            } catch (NoSuchExhibitionException e) {
                request.setAttribute("isExhibitionNotFound", true);
                logger.warn("Such exhibition is not exists ", e);
            } catch (NoSuchUserException e) {
                request.setAttribute("isUserInNotExists", true);
                logger.warn("Such user is not exists ", e);
            }
        }
        return false;
    }
}
