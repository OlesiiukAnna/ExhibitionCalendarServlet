package ua.external.servlet.handler.helpers;

import ua.external.service.interfaces.ExhibitionService;
import ua.external.service.interfaces.TicketService;
import ua.external.service.interfaces.UserService;
import ua.external.util.dto.ExhibitionDto;
import ua.external.util.dto.TicketDto;
import ua.external.util.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessTickets {

    public static List<TicketDto> getTicketDtos(HttpServletRequest request, TicketService<TicketDto> ticketService,
                                                UserService<UserDto> userService,
                                                ExhibitionService<ExhibitionDto> exhibitionService) {
        List<TicketDto> tickets = ticketService.getAll();
        tickets.sort((ticket1, ticket2) -> Boolean.compare(ticket1.isPaid(), ticket2.isPaid()));
        request.setAttribute("tickets", tickets);

        Map<Integer, UserDto> userDtos = new ConcurrentHashMap<>();
        for (TicketDto ticketDto : tickets) {
            userDtos.put(ticketDto.getUserId(), userService.getById(ticketDto.getUserId()).get());
        }
        request.setAttribute("ticketOwners", userDtos);

        Map<Integer, ExhibitionDto> exhibitionDtos = new ConcurrentHashMap<>();
        for (TicketDto ticketDto : tickets) {
            exhibitionDtos.put(ticketDto.getExhibitionId(),
                    exhibitionService.getById(ticketDto.getExhibitionId()).get());
        }
        request.setAttribute("ticketExhibitions", exhibitionDtos);
        return tickets;
    }
}
