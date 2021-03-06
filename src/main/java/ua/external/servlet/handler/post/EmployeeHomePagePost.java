package ua.external.servlet.handler.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.external.exceptions.ticket.NoSuchTicketException;
import ua.external.exceptions.ticket.TicketIsAlreadyPaidException;
import ua.external.service.interfaces.ExhibitionService;
import ua.external.service.interfaces.TicketService;
import ua.external.servlet.handler.ServletHandler;
import ua.external.servlet.handler.helpers.ProcessHomePage;
import ua.external.util.dto.ExhibitionDto;
import ua.external.util.dto.TicketDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.external.servlet.handler.Paths.EMPLOYEE_HOME_PAGE_PAGE;

public class EmployeeHomePagePost implements ServletHandler {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeHomePagePost.class);

    private TicketService<TicketDto> ticketService;
    private ExhibitionService<ExhibitionDto> exhibitionService;

    public EmployeeHomePagePost(TicketService<TicketDto> ticketService,
                                ExhibitionService<ExhibitionDto> exhibitionService) {
        this.ticketService = ticketService;
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        ProcessHomePage.processPage(request, ticketService, exhibitionService);

        String ticketToDelete = request.getParameter("ticket-to-delete");
        if (ticketToDelete != null && !ticketToDelete.isBlank()) {
            try {
                int ticketId = Integer.parseInt(ticketToDelete);
                ticketService.delete(ticketId);
            } catch (TicketIsAlreadyPaidException e) {
                logger.warn("Tickets are already paid", e);
            } catch (NoSuchTicketException e) {
                logger.warn("There is no such ticket", e);
            }
        }
        return "redirect:" + EMPLOYEE_HOME_PAGE_PAGE;
    }
}
