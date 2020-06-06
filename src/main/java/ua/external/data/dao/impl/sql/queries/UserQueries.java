package ua.external.data.dao.impl.sql.queries;

public interface UserQueries {
    String SELECT_USER_BY_ID =
            "SELECT id, email, pass, first_name, last_name, phone, role " +
                    "FROM exhibition_users WHERE id=?";
    String SELECT_USER_BY_EMAIL =
            "SELECT id, email, pass, first_name, last_name, phone, role " +
                    "FROM exhibition_users WHERE email=?";
    String SELECT_ALL_USERS =
            "SELECT id, email, pass, first_name, last_name, phone, role " +
                    "FROM exhibition_users";
    String INSERT_USER = "INSERT INTO exhibition_users " +
            "(email, pass, first_name, last_name, phone, role) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    String UPDATE_USER = "UPDATE exhibition_users " +
            "SET email=?, pass=?, first_name=?, last_name=?, phone=?, role=? WHERE id=?";
    String DELETE_USER = "DELETE FROM exhibition_users WHERE id=?";
}
