package ua.external.servlet.handler.helpers;

import ua.external.service.interfaces.ExhibitionService;
import ua.external.service.interfaces.TicketService;
import ua.external.util.dto.ExhibitionDto;
import ua.external.util.dto.TicketDto;
import ua.external.util.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessHomePage {

    public static void processPage(HttpServletRequest request, TicketService<TicketDto> ticketService,
                                   ExhibitionService<ExhibitionDto> exhibitionService) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        List<TicketDto> tickets = ticketService.getAllTicketsByUserId(user.getId());
        tickets.sort((ticket1, ticket2) -> Boolean.compare(ticket1.isPaid(), ticket2.isPaid()));

        request.getSession().setAttribute("myTickets", tickets);

        Map<Integer, ExhibitionDto> exhibitionDtos = new ConcurrentHashMap<>();
        for (TicketDto ticketDto : tickets) {
            exhibitionDtos.put(ticketDto.getExhibitionId(),
                    exhibitionService.getById(ticketDto.getExhibitionId()).get());
        }
        request.getSession().setAttribute("ticketExhibitions", exhibitionDtos);
    }
}
