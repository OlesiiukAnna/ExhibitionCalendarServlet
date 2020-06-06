package ua.external.data.dao.impl.sql.queries;

public interface TicketQueries {
    String SELECT_TICKET_BY_ID =
            "SELECT tickets.id, visit_date, order_date, ticket_type, ticket_price, is_paid," +
            "user_id, u.email, u.pass, u.first_name, u.last_name, u.phone, u.role, " +
            "exhibition_id, e.name exhibition_name, e.description, e.begin_date, e.end_date, e.full_ticket_price, " +
            "hall.id hall_id, hall.name hall_name, hall.allowable_number_of_visitors_per_day " +
            "FROM tickets " +
            "join exhibition_users u ON tickets.user_id = u.id " +
            "join exhibitions e on tickets.exhibition_id = e.id " +
            "join exhibition_halls hall on e.exhibition_hall_id = hall.id where tickets.id=?";
    String SELECT_ALL_USER_TICKETS_BY_USER_ID =
            "SELECT tickets.id, visit_date, order_date, ticket_type, ticket_price, is_paid," +
            "user_id, u.email, u.pass, u.first_name, u.last_name, u.phone, u.role, " +
            "exhibition_id, e.name exhibition_name, e.description, e.begin_date, e.end_date, e.full_ticket_price, " +
            "hall.id hall_id, hall.name hall_name, hall.allowable_number_of_visitors_per_day " +
            "FROM tickets " +
            "join exhibition_users u ON tickets.user_id = u.id " +
            "join exhibitions e on tickets.exhibition_id = e.id " +
            "join exhibition_halls hall on e.exhibition_hall_id = hall.id " +
            "where user_id=?" +
            "order by id";
    String SELECT_ALL_TICKETS =
            "SELECT tickets.id, visit_date, order_date, ticket_type, ticket_price, is_paid," +
            "user_id, u.email, u.pass, u.first_name, u.last_name, u.phone, u.role, " +
            "exhibition_id, e.name exhibition_name, e.description, e.begin_date, e.end_date, e.full_ticket_price, " +
            "hall.id hall_id, hall.name hall_name, hall.allowable_number_of_visitors_per_day " +
            "FROM tickets " +
            "join exhibition_users u ON tickets.user_id = u.id " +
            "join exhibitions e on tickets.exhibition_id = e.id " +
            "join exhibition_halls hall on e.exhibition_hall_id = hall.id " +
            "order by id";
    String COUNT_TICKETS_FOR_VISIT_DATE = "SELECT COUNT(id) FROM tickets WHERE visit_date=?";
    String INSERT_TICKET = "INSERT INTO tickets " +
            "(visit_date, order_date, ticket_type, ticket_price, is_paid, " +
            "user_id, exhibition_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_TICKET = "UPDATE tickets " +
            "SET visit_date=?, order_date=?, ticket_type=?, ticket_price=?, is_paid=?, " +
            "user_id=?, exhibition_id=? WHERE id=?";
    String DELETE_TICKET = "DELETE FROM tickets WHERE id=?";
}
