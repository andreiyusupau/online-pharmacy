package com.vironit.onlinepharmacy.dao.jdbc;

import com.vironit.onlinepharmacy.dao.CreditCardDao;
import com.vironit.onlinepharmacy.dao.DaoException;
import com.vironit.onlinepharmacy.model.CreditCard;
import com.vironit.onlinepharmacy.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class JdbcCreditCardDao implements CreditCardDao {

    private static final String CREDIT_CARDS_TABLE = "credit_cards";

    private final DataSource dataSource;

    public JdbcCreditCardDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public long add(CreditCard creditCard) {
        String sql = "INSERT INTO " + CREDIT_CARDS_TABLE + "(card_number, owner_name, valid_thru, cvv, user_id) " +
                "VALUES(?,?,?,?,?) " +
                "RETURNING id;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, creditCard.getCardNumber());
            preparedStatement.setString(2, creditCard.getOwnerName());
            preparedStatement.setDate(3, Date.valueOf(creditCard.getValidThru()));
            preparedStatement.setInt(4, creditCard.getCvv());
            preparedStatement.setLong(5, creditCard.getOwner()
                    .getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                } else {
                    throw new DaoException("Error adding credit card to database");
                }
            }

        } catch (SQLException sqlException) {
            throw new DaoException("Error adding credit card to database", sqlException);
        }
    }

    @Override
    public Optional<CreditCard> get(long id) {
        String sql = "SELECT id, card_number, owner_name, valid_thru, cvv, user_id " +
                "FROM " + CREDIT_CARDS_TABLE + " " +
                "WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(parseCreditCard(resultSet)) : Optional.empty();
            }
        } catch (SQLException sqlException) {
            throw new DaoException("Error getting credit card from database", sqlException);
        }
    }

    @Override
    public Collection<CreditCard> getAll() {
        String sql = "SELECT id, card_number, owner_name, valid_thru, cvv, user_id " +
                "FROM " + CREDIT_CARDS_TABLE + ";";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            Collection<CreditCard> creditCards = new ArrayList<>();
            while (resultSet.next()) {
                CreditCard creditCard = parseCreditCard(resultSet);
                creditCards.add(creditCard);
            }
            return creditCards;
        } catch (SQLException sqlException) {
            throw new DaoException("Error getting all credit cards from database", sqlException);
        }
    }

    @Override
    public boolean remove(long id) {
        String sql = "DELETE FROM " + CREDIT_CARDS_TABLE +
                " WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            throw new DaoException("Error removing credit card from database", sqlException);
        }
    }

    @Override
    public boolean addAll(Collection<CreditCard> creditCards) {
        String sql = "INSERT INTO " + CREDIT_CARDS_TABLE + "(card_number, owner_name, valid_thru, cvv, user_id) " +
                " VALUES(?,?,?,?,?)" +
                ",(?,?,?,?,?)".repeat(creditCards.size() - 1) + ";";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int counter = 0;
            for (CreditCard creditCard : creditCards) {
                preparedStatement.setString(counter + 1, creditCard.getCardNumber());
                preparedStatement.setString(counter + 2, creditCard.getOwnerName());
                preparedStatement.setDate(counter + 3, Date.valueOf(creditCard.getValidThru()));
                preparedStatement.setInt(counter + 4, creditCard.getCvv());
                preparedStatement.setLong(counter + 5, creditCard.getOwner()
                        .getId());
                counter += 5;
            }
            return preparedStatement.executeUpdate() == creditCards.size();
        } catch (SQLException sqlException) {
            throw new DaoException("Error adding all credit cards to database", sqlException);
        }
    }

    @Override
    public Collection<CreditCard> getAllByOwnerId(long id) {
        String sql = "SELECT id, card_number, owner_name, valid_thru, cvv, user_id " +
                "FROM " + CREDIT_CARDS_TABLE + " " +
                "WHERE user_id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Collection<CreditCard> creditCards = new ArrayList<>();
                while (resultSet.next()) {
                    CreditCard creditCard = parseCreditCard(resultSet);
                    creditCards.add(creditCard);
                }
                return creditCards;
            }
        } catch (SQLException sqlException) {
            throw new DaoException("Error getting all credit cards by owner id from database", sqlException);
        }
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        String sql = "DELETE FROM " + CREDIT_CARDS_TABLE +
                " WHERE user_id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            throw new DaoException("Error removing all credit cards by owner id from database", sqlException);
        }
    }

    private CreditCard parseCreditCard(ResultSet resultSet) throws SQLException {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(resultSet.getLong(1));
        creditCard.setCardNumber(resultSet.getString(2));
        creditCard.setOwnerName(resultSet.getString(3));
        creditCard.setValidThru(resultSet.getDate(4)
                .toLocalDate());
        creditCard.setCvv(resultSet.getInt(5));
        User user = new User();
        user.setId(resultSet.getLong(6));
        creditCard.setOwner(user);
        return creditCard;
    }
}
