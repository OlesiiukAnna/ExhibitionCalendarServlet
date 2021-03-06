package ua.external.servlet.handler.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.external.exceptions.InvalidDataException;
import ua.external.exceptions.ticket.NoSuchTicketException;
import ua.external.service.interfaces.ExhibitionService;
import ua.external.service.interfaces.TicketService;
import ua.external.service.interfaces.UserService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.servlet.handler.helpers.ProcessTickets;
import ua.external.util.dto.ExhibitionDto;
import ua.external.util.dto.TicketDto;
import ua.external.util.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static ua.external.servlet.handler.Paths.CONFIRM_TICKETS_PAGE;

public class TicketsPost implements ServletHandler {
    private final static Logger logger = LoggerFactory.getLogger(TicketsPost.class);

    private TicketService<TicketDto> ticketService;
    private UserService<UserDto> userService;
    private ExhibitionService<ExhibitionDto> exhibitionService;

    public TicketsPost(TicketService<TicketDto> ticketService,
                       UserService<UserDto> userService,
                       ExhibitionService<ExhibitionDto> exhibitionService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        List<TicketDto> tickets = ProcessTickets.getTicketDtos(request, ticketService, userService, exhibitionService);

        int[] ticketIdsToConfirm = List.of(request.getParameterValues("ticketIdsToConfirm"))
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        if (ticketIdsToConfirm.length == 1) {
            try {
                ticketService.update(ticketIdsToConfirm[0], true);
            } catch (InvalidDataException e) {
                logger.warn("Invalid input data", e);
            } catch (NoSuchTicketException e) {
                logger.warn("There is no such ticket", e);
            }
        } else {
            List<TicketDto> ticketsToUpdate = new CopyOnWriteArrayList<>();
            for (TicketDto ticketDto : tickets) {
                for (int ticketId : ticketIdsToConfirm) {
                    if (ticketDto.getId() == ticketId) {
                        ticketDto.setPaid(true);
                        ticketsToUpdate.add(ticketDto);
                    }
                }
            }
            try {
                ticketService.updateListOfTickets(ticketsToUpdate);
            } catch (NoSuchTicketException e) {
                logger.warn("There is no such ticket", e);
            }
        }
        return "redirect:" + CONFIRM_TICKETS_PAGE;
    }
}
