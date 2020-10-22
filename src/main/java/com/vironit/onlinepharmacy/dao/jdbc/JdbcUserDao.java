package com.vironit.onlinepharmacy.dao.jdbc;

import com.vironit.onlinepharmacy.dao.DaoException;
import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class JdbcUserDao implements UserDao {

    private static final String USERS_TABLE = "users";
    private static final String ROLES_TABLE = "roles";

    private final DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        String sql = "SELECT u.id, u.first_name, u.middle_name, u.last_name, u.date_of_birth, u.email, u.password, r.name " +
                "FROM " + USERS_TABLE + " AS u " +
                "INNER JOIN " + ROLES_TABLE + " AS r ON u.role_id=r.id " +
                "WHERE u.email=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(parseUser(resultSet)) : Optional.empty();
            }
        } catch (SQLException sqlException) {
            throw new DaoException("Error getting user by email from database", sqlException);
        }
    }

    @Override
    public boolean update(User user) {
        String sql = "UPDATE " + USERS_TABLE +
                " SET(first_name, middle_name, last_name, date_of_birth, email, password, role_id) " +
                "= (?,?,?,?,?,?," +
                "(SELECT id " +
                "FROM " + ROLES_TABLE + " " +
                "WHERE name=?)) " +
                "WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getMiddleName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setString(7, user.getRole().name());
            preparedStatement.setLong(8, user.getId());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            throw new DaoException("Error updating user in database", sqlException);
        }
    }

    @Override
    public long add(User user) {
        String sql = "INSERT INTO " + USERS_TABLE + "(first_name, middle_name, last_name, date_of_birth, email, password, role_id) " +
                "VALUES(?,?,?,?,?,?," +
                "(SELECT id " +
                "FROM " + ROLES_TABLE + " " +
                "WHERE name=?)) " +
                "RETURNING id;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getMiddleName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setString(7, user.getRole().name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                } else {
                    throw new DaoException("Error adding user to database");
                }
            }
        } catch (SQLException sqlException) {
            throw new DaoException("Error adding user to database", sqlException);
        }
    }

    @Override
    public Optional<User> get(long id) {
        String sql = "SELECT u.id, u.first_name, u.middle_name, u.last_name, u.date_of_birth, u.email, u.password, r.name " +
                "FROM " + USERS_TABLE + " AS u " +
                "INNER JOIN " + ROLES_TABLE + " AS r ON u.role_id=r.id " +
                "WHERE u.id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(parseUser(resultSet)) : Optional.empty();
            }
        } catch (SQLException sqlException) {
            throw new DaoException("Error getting user from database", sqlException);
        }
    }

    @Override
    public Collection<User> getAll() {
        String sql = "SELECT u.id, u.first_name, u.middle_name, u.last_name, u.date_of_birth, u.email, u.password, r.name " +
                "FROM " + USERS_TABLE + " AS u " +
                "INNER JOIN " + ROLES_TABLE + " AS r ON u.role_id = r.id";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            Collection<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = parseUser(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException sqlException) {
            throw new DaoException("Error getting all users from database", sqlException);
        }
    }

    @Override
    public boolean remove(long id) {
        String sql = "DELETE FROM " + USERS_TABLE +
                " WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            throw new DaoException("Error removing user from database", sqlException);
        }
    }

    @Override
    public int getTotalElements() {
        String sql = "SELECT COUNT(*) FROM " + USERS_TABLE;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : -1;
        } catch (SQLException sqlException) {
            throw new DaoException("Error getting total users from database", sqlException);
        }
    }

    @Override
    public Collection<User> getPage(int currentPage, int pageLimit) {
        String sql = "SELECT u.id, u.first_name, u.middle_name, u.last_name, u.date_of_birth, u.email, u.password, r.name " +
                "FROM " + USERS_TABLE + " AS u " +
                "INNER JOIN " + ROLES_TABLE + " AS r ON u.role_id = r.id " +
                "ORDER BY id LIMIT ? OFFSET ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pageLimit);
            preparedStatement.setInt(2, (currentPage - 1) * pageLimit);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Collection<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    User user = parseUser(resultSet);
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException sqlException) {
            throw new DaoException("Error getting user page from database", sqlException);
        }
    }

    private User parseUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(1));
        user.setFirstName(resultSet.getString(2));
        user.setMiddleName(resultSet.getString(3));
        user.setLastName(resultSet.getString(4));
        user.setDateOfBirth(resultSet.getDate(5).toLocalDate());
        user.setEmail(resultSet.getString(6));
        user.setPassword(resultSet.getString(7));
        user.setRole(Role.valueOf(resultSet.getString(8)));
        return user;
    }
}
