package ua.external.servlet.handler.get;

import ua.external.service.interfaces.ExhibitionService;
import ua.external.service.interfaces.TicketService;
import ua.external.service.interfaces.UserService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.util.dto.ExhibitionDto;
import ua.external.util.dto.TicketDto;
import ua.external.util.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        List<TicketDto> tickets = ticketService.getAll();
        tickets.sort((ticket1, ticket2) -> Boolean.compare(ticket1.isPaid(), ticket2.isPaid()));
        request.getSession().setAttribute("tickets", tickets);

        Map<Integer, UserDto> userDtos = new ConcurrentHashMap<>();
        for (TicketDto ticketDto : tickets) {
            userDtos.put(ticketDto.getVisitorId(), userService.getById(ticketDto.getVisitorId()).get());
        }
        request.getSession().setAttribute("ticketOwners", userDtos);

        Map<Integer, ExhibitionDto> exhibitionDtos = new ConcurrentHashMap<>();
        for (TicketDto ticketDto : tickets) {
            exhibitionDtos.put(ticketDto.getExhibitionId(),
                    exhibitionService.getById(ticketDto.getExhibitionId()).get());
        }
        request.getSession().setAttribute("ticketExhibitions", exhibitionDtos);
        return CONFIRM_TICKETS_JSP;
    }
}
