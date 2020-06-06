package ua.external.data.dao.impl.sql.queries;

public interface ExhibitionQueries {
    String SELECT_EXHIBITION_BY_ID =
            "SELECT exhibitions.id, exhibitions.name, description, begin_date, end_date, " +
                    "full_ticket_price, exhibition_hall_id, " +
                    "exhibition_halls.name, exhibition_halls.allowable_number_of_visitors_per_day " +
                    "FROM exhibitions JOIN exhibition_halls " +
                    "ON exhibitions.exhibition_hall_id=exhibition_halls.id where exhibitions.id=?";
    String SELECT_EXHIBITION_BY_NAME =
            "SELECT exhibitions.id, exhibitions.name, description, begin_date, end_date, " +
            "full_ticket_price, exhibition_hall_id, " +
            "exhibition_halls.name, exhibition_halls.allowable_number_of_visitors_per_day " +
            "FROM exhibitions JOIN exhibition_halls " +
            "ON exhibitions.exhibition_hall_id=exhibition_halls.id where exhibitions.name=?";
    String SELECT_EXHIBITIONS_BY_HALL_ID =
            "SELECT exhibitions.id, exhibitions.name, description, begin_date, end_date, " +
            "full_ticket_price, exhibition_hall_id, " +
            "exhibition_halls.name, exhibition_halls.allowable_number_of_visitors_per_day " +
            "FROM exhibitions JOIN exhibition_halls " +
            "ON exhibitions.exhibition_hall_id=exhibition_halls.id where exhibition_hall_id=?";
    String SELECT_ALL_EXHIBITIONS =
            "SELECT exhibitions.id, exhibitions.name, description, begin_date, end_date, " +
                    "full_ticket_price, exhibition_hall_id, " +
                    "exhibition_halls.name, exhibition_halls.allowable_number_of_visitors_per_day " +
                    "FROM exhibitions JOIN exhibition_halls " +
                    "ON exhibitions.exhibition_hall_id=exhibition_halls.id";
    String INSERT_EXHIBITION = "INSERT INTO exhibitions " +
            "(name, description, begin_date, end_date, " +
            "full_ticket_price, exhibition_hall_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    String UPDATE_EXHIBITION = "UPDATE exhibitions " +
            "SET name=?, description=?, begin_date=?, end_date=?, " +
            "full_ticket_price=?, exhibition_hall_id=? WHERE id=?";
    String DELETE_EXHIBITION = "DELETE FROM exhibitions WHERE id=?";
}
