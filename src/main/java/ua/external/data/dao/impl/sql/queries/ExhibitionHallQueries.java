package ua.external.data.dao.impl.sql.queries;

public interface ExhibitionHallQueries {
    String SELECT_EXHIBITION_HALL_BY_ID =
            "SELECT id, name, allowable_number_of_visitors_per_day FROM exhibition_halls WHERE id=?";
    String SELECT_EXHIBITION_HALL_BY_NAME =
            "SELECT id, name, allowable_number_of_visitors_per_day FROM exhibition_halls WHERE name=?";
    String SELECT_ALL_EXHIBITION_HALLS =
            "SELECT id, name, allowable_number_of_visitors_per_day FROM exhibition_halls";
    String INSERT_EXHIBITION_HALL = "INSERT INTO exhibition_halls " +
            "(name, allowable_number_of_visitors_per_day) VALUES (?, ?)";
    String UPDATE_EXHIBITION_HALL = "UPDATE exhibition_halls " +
            "SET name=?, allowable_number_of_visitors_per_day=? WHERE id=?";
    String DELETE_EXHIBITION_HALL = "DELETE FROM exhibition_halls WHERE id=?";
}
