package ua.external.data.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.external.data.dao.interfaces.TicketDao;
import ua.external.data.entity.Exhibition;
import ua.external.data.entity.ExhibitionHall;
import ua.external.data.entity.Ticket;
import ua.external.data.entity.User;
import ua.external.exceptions.DaoException;
import ua.external.exceptions.ticket.TicketsCountException;
import ua.external.util.enums.TicketType;
import ua.external.util.enums.Role;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.external.data.dao.impl.sql.queries.TicketQueries.*;

public class TicketDaoImpl implements TicketDao<Ticket> {
    private Logger logger = LoggerFactory.getLogger(TicketDaoImpl.class);

    private DataSource dataSource;

    public TicketDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Ticket> getById(int id) {
        Ticket ticket = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TICKET_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int ticketId = -1;
            while (resultSet.next() && id != ticketId) {
                ticketId = resultSet.getInt(1);
                ticket = getTicketFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error when get ticket ", e);
            throw new DaoException(e);
        }
        return Optional.ofNullable(ticket);
    }

    private Ticket getTicketFromResultSet(ResultSet resultSet) throws SQLException {
        int ticketId = resultSet.getInt("id");
        LocalDate visitDate = resultSet.getDate("visit_date").toLocalDate();
        LocalDate orderDate = resultSet.getDate("order_date").toLocalDate();
        TicketType ticketType = TicketType.valueOf(resultSet.getString("ticket_type"));
        int ticketPrice = resultSet.getInt("ticket_price");
        boolean isPaid = resultSet.getBoolean("is_paid");

        int userId = resultSet.getInt("user_id");
        String email = resultSet.getString("email");
        String pass = resultSet.getString("pass");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String phone = resultSet.getString("phone");
        Role role = Role.valueOf(resultSet.getString("role"));
        User user = new User(userId, email, pass, firstName, lastName, phone, role);

        int exhibitionId = resultSet.getInt("exhibition_id");
        String name = resultSet.getString("exhibition_name");
        String description = resultSet.getString("description");
        LocalDate beginDate = resultSet.getDate("begin_date").toLocalDate();
        LocalDate endDate = resultSet.getDate("end_date").toLocalDate();
        int fullTicketPrice = resultSet.getInt("full_ticket_price");

        int exhibitionHallId = resultSet.getInt("hall_id");
        String exhibitionHallName = resultSet.getString("hall_name");
        int allowableNumberOfVisitorsPerDay = resultSet.getInt("allowable_number_of_visitors_per_day");
        ExhibitionHall exhibitionHall = new ExhibitionHall(
                exhibitionHallId, exhibitionHallName, allowableNumberOfVisitorsPerDay);
        Exhibition exhibition = new Exhibition(exhibitionId, name, description,
                beginDate, endDate, fullTicketPrice, exhibitionHall);

        return new Ticket(ticketId, visitDate, orderDate,
                ticketType, ticketPrice, isPaid, user, exhibition);
    }

    @Override
    public List<Ticket> getAllTicketsByUserId(int id) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USER_TICKETS_BY_USER_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int userId;
            while (resultSet.next()) {
                userId = resultSet.getInt("user_id");
                if (userId == id) {
                    tickets.add(getTicketFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.error("Error when get user tickets ", e);
            throw new DaoException(e);
        }
        return tickets;
    }

    @Override
    public List<Ticket> getAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_TICKETS);
            while (resultSet.next()) {
                tickets.add(getTicketFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Error when get all tickets ", e);
            throw new DaoException(e);
        }
        return tickets;
    }

    @Override
    public int countTicketsForTheDate(LocalDate date) {
        int ticketsOrdered = -1;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_TICKETS_FOR_VISIT_DATE)) {
            statement.setDate(1, Date.valueOf(date));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ticketsOrdered = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error when calculate tickets for the date ", e);
            throw new TicketsCountException();
        }
        return ticketsOrdered;
    }

    @Override
    public boolean save(Ticket ticket) throws DaoException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_TICKET)) {
            setTicketFromResultSet(ticket, statement);
            statement.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Error when save ticket ", e);
            throw new DaoException();
        }
    }

    private void setTicketFromResultSet(Ticket ticket, PreparedStatement statement) throws SQLException {
        statement.setDate(1, Date.valueOf(ticket.getVisitDate()));
        statement.setDate(2, Date.valueOf(ticket.getOrderDate()));
        statement.setString(3, ticket.getTicketType().toString());
        statement.setInt(4, ticket.getTicketPrice());
        statement.setBoolean(5, ticket.isPaid());
        statement.setInt(6, ticket.getVisitor().getId());
        statement.setInt(7, ticket.getExhibition().getId());
    }

    @Override
    public boolean saveListOfTickets(List<Ticket> tickets) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(INSERT_TICKET);
            for (Ticket ticket : tickets) {
                setTicketFromResultSet(ticket, statement);
                statement.execute();
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            logger.error("Error when save list of tickets ", e);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                logger.error("Roll back exception");
                ex.printStackTrace();
            }
            throw new DaoException();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("Error while set autocommit true");
                e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<Ticket> update(Ticket ticket) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TICKET)) {
            setTicketFromResultSet(ticket, statement);
            statement.setInt(8, ticket.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 1) {
                return Optional.of(ticket);
            }
        } catch (SQLException e) {
            logger.error("Error when update ticket ", e);
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean updateListOfTickets(List<Ticket> tickets) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_TICKET);
            int updatedRows = 0;
            for (Ticket ticket : tickets) {
                setTicketFromResultSet(ticket, statement);
                statement.setInt(8, ticket.getId());
                updatedRows += statement.executeUpdate();
            }
            if (updatedRows == tickets.size()) {
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error when update list of tickets ", e);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                logger.error("Roll back exception");
                ex.printStackTrace();
            }
            throw new DaoException();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TICKET)) {
            statement.setInt(1, id);
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 1) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error when delete ticket ", e);
            throw new DaoException(e);
        }
        return false;
    }
}
