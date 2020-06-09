package ua.external.servlet.handler.get;

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

import static ua.external.servlet.handler.Paths.CONFIRM_TICKETS_JSP;

public class TicketsGet implements ServletHandler {
    private TicketService<TicketDto> ticketService;
    private UserService<UserDto> userService;
    private ExhibitionService<ExhibitionDto> exhibitionService;

    public TicketsGet(TicketService<TicketDto> ticketService,
                      UserService<UserDto> userService,
                      ExhibitionService<ExhibitionDto> exhibitionService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        ProcessTickets.getTicketDtos(request, ticketService, userService, exhibitionService);
        return CONFIRM_TICKETS_JSP;
    }
}
