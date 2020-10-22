package com.vironit.onlinepharmacy.dao.jdbc;

import com.vironit.onlinepharmacy.dao.DaoException;
import com.vironit.onlinepharmacy.dao.ProductDao;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.ProductCategory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class JdbcProductDao implements ProductDao {

    private static final String PRODUCTS_TABLE = "products";
    private static final String PRODUCT_CATEGORIES_TABLE = "product_categories";

    private final DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean update(Product product) {
        String sql = "UPDATE " + PRODUCTS_TABLE +
                " SET(name, price, recipe_required, product_category_id) " +
                "= (?,?,?,?) " +
                "WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setLong(2, product.getPrice()
                    .movePointRight(2)
                    .longValueExact());
            preparedStatement.setBoolean(3, product.isRecipeRequired());
            preparedStatement.setLong(4, product.getProductCategory()
                    .getId());
            preparedStatement.setLong(5, product.getId());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            throw new DaoException("Error updating product in database", sqlException);
        }
    }

    @Override
    public long add(Product product) {
        String sql = "INSERT INTO " + PRODUCTS_TABLE + "(name, price, recipe_required, product_category_id) " +
                "VALUES(?,?,?,?) " +
                "RETURNING id;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setLong(2, product.getPrice()
                    .movePointRight(2)
                    .longValueExact());
            preparedStatement.setBoolean(3, product.isRecipeRequired());
            preparedStatement.setLong(4, product.getProductCategory()
                    .getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                } else {
                    throw new DaoException("Error adding product to database");
                }
            }
        } catch (SQLException sqlException) {
            throw new DaoException("Error adding product to database", sqlException);
        }
    }

    @Override
    public Optional<Product> get(long id) {
        String sql = "SELECT pr.id, pr.name, pr.price, pr.recipe_required, pc.id, pc.name, pc.description " +
                "FROM " + PRODUCTS_TABLE + " AS pr " +
                "INNER JOIN " + PRODUCT_CATEGORIES_TABLE + " AS pc ON pr.product_category_id = pc.id " +
                "WHERE pr.id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(parseProduct(resultSet)) : Optional.empty();
            }
        } catch (SQLException sqlException) {
            throw new DaoException("Error getting product from database", sqlException);
        }
    }

    @Override
    public Collection<Product> getAll() {
        String sql = "SELECT pr.id, pr.name, pr.price, pr.recipe_required, pc.id, pc.name, pc.description " +
                "FROM " + PRODUCTS_TABLE + " AS pr " +
                "INNER JOIN " + PRODUCT_CATEGORIES_TABLE + " AS pc ON pr.product_category_id = pc.id;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            Collection<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = parseProduct(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException sqlException) {
            throw new DaoException("Error getting all products from database", sqlException);
        }
    }

    @Override
    public boolean remove(long id) {
        String sql = "DELETE FROM " + PRODUCTS_TABLE +
                " WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            throw new DaoException("Error removing product from database", sqlException);
        }
    }

    @Override
    public int getTotalElements() {
        String sql = "SELECT COUNT(*) FROM " + PRODUCTS_TABLE;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : -1;
        } catch (SQLException sqlException) {
            throw new DaoException("Error getting total product elements from database", sqlException);
        }
    }

    @Override
    public Collection<Product> getPage(int currentPage, int pageLimit) {
        String sql = "SELECT pr.id, pr.name, pr.price, pr.recipe_required, pc.id, pc.name, pc.description " +
                "FROM " + PRODUCTS_TABLE + " AS pr " +
                "INNER JOIN " + PRODUCT_CATEGORIES_TABLE + " AS pc ON pr.product_category_id = pc.id " +
                "ORDER BY pr.id LIMIT ? OFFSET ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pageLimit);
            preparedStatement.setInt(2, (currentPage - 1) * pageLimit);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Collection<Product> products = new ArrayList<>();
                while (resultSet.next()) {
                    Product product = parseProduct(resultSet);
                    products.add(product);
                }
                return products;
            }
        } catch (SQLException sqlException) {
            throw new DaoException("Error getting product page from database", sqlException);
        }
    }

    private Product parseProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong(1));
        product.setName(resultSet.getString(2));
        product.setPrice(BigDecimal.valueOf(resultSet.getLong(3))
                .movePointLeft(2));
        product.setRecipeRequired(resultSet.getBoolean(4));
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(resultSet.getLong(5));
        productCategory.setName(resultSet.getString(6));
        productCategory.setDescription(resultSet.getString(7));
        product.setProductCategory(productCategory);
        return product;
    }
}
