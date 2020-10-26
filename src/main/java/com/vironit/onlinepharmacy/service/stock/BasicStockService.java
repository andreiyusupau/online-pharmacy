package com.vironit.onlinepharmacy.service.stock;

import com.vironit.onlinepharmacy.dao.StockDao;
import com.vironit.onlinepharmacy.dto.PositionData;
import com.vironit.onlinepharmacy.model.OrderPosition;
import com.vironit.onlinepharmacy.model.Position;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.StockPosition;
import com.vironit.onlinepharmacy.service.exception.StockServiceException;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.util.Converter;

import java.util.Collection;
import java.util.Optional;

public class BasicStockService implements StockService {

    private final StockDao stockDAO;
    private final ProductService productService;
    private final Converter<StockPosition, PositionData> positionDataToStockPositionConverter;

    public BasicStockService(StockDao stockDAO, ProductService productService, Converter<StockPosition, PositionData> positionDataToStockPositionConverter) {
        this.stockDAO = stockDAO;
        this.productService = productService;
        this.positionDataToStockPositionConverter = positionDataToStockPositionConverter;
    }

    @Override
    public long add(PositionData positionData) {
        long productId = positionData.getProductId();
        Optional<StockPosition> existingStockPosition = stockDAO.getByProductId(productId);
        if (existingStockPosition.isPresent()) {
            StockPosition updatedPosition = existingStockPosition.get();
            updatedPosition.setQuantity(updatedPosition.getQuantity() + positionData.getQuantity());
            stockDAO.update(updatedPosition);
            return updatedPosition.getId();
        } else {
            StockPosition stockPosition = positionDataToStockPositionConverter.convert(positionData);
            Product product = new Product();
            product.setId(productId);
            return stockDAO.add(stockPosition);
        }
    }

    @Override
    public StockPosition get(long id) {
        return stockDAO.get(id)
                .orElseThrow(() -> new StockServiceException("Can't get stock position. Position with id " + id + " not found."));
    }

    @Override
    public Collection<StockPosition> getAll() {
        return stockDAO.getAll();
    }

    @Override
    public void update(PositionData positionData) {
        stockDAO.update(positionDataToStockPositionConverter.convert(positionData));
    }

    @Override
    public void remove(long id) {
        stockDAO.remove(id);
    }

    @Override
    public boolean addAll(Collection<PositionData> positionData) {
        positionData.forEach(this::add);
        return true;
    }

    @Override
    public boolean reserveInStock(Collection<OrderPosition> positions) {
        for (Position position : positions) {
            long productId = position.getProduct()
                    .getId();
            StockPosition stockPosition = stockDAO.getByProductId(productId)
                    .orElseThrow(() -> new StockServiceException("Can't reserveInStock position " + position + ", because it's not in stock."));
            int desiredPositionQuantity = position.getQuantity();
            int reservedStockPositionQuantity = stockPosition.getReservedQuantity();
            int totalStockQuantity = stockPosition.getQuantity();
            int availableStockPositionQuantity = totalStockQuantity - reservedStockPositionQuantity;
            if (availableStockPositionQuantity >= desiredPositionQuantity) {
                stockPosition.setReservedQuantity(reservedStockPositionQuantity + desiredPositionQuantity);
                stockDAO.update(stockPosition);
            } else {
                throw new StockServiceException("Can't reserveInStock position " + position + ". Desired quantity " +
                        desiredPositionQuantity + ", quantity in stock " + availableStockPositionQuantity + ".");
            }
        }
        return true;
    }

    @Override
    public boolean takeFromStock(Collection<OrderPosition> positions) {
        for (Position position : positions) {
            long productId = position.getProduct()
                    .getId();
            StockPosition stockPosition = stockDAO.getByProductId(productId)
                    .orElseThrow(() -> new StockServiceException("Can't takeFromStock position " + position + ", because it's not in stock."));
            int desiredPositionQuantity = position.getQuantity();
            int reservedStockPositionQuantity = stockPosition.getReservedQuantity();
            int totalStockQuantity = stockPosition.getQuantity();
            if (totalStockQuantity >= desiredPositionQuantity
                    && reservedStockPositionQuantity >= desiredPositionQuantity) {
                stockPosition.setQuantity(totalStockQuantity - desiredPositionQuantity);
                stockPosition.setReservedQuantity(reservedStockPositionQuantity - desiredPositionQuantity);
                stockDAO.update(stockPosition);
            } else {
                throw new StockServiceException("Can't take position " + position + " from stock. Desired quantity " +
                        desiredPositionQuantity + ", quantity in stock " + totalStockQuantity + ", total reserved quantity"
                        + reservedStockPositionQuantity + ".");
            }
        }
        return true;
    }

    @Override
    public boolean annulReservationInStock(Collection<OrderPosition> positions) {
        for (Position position : positions) {
            long productId = position.getProduct()
                    .getId();
            StockPosition stockPosition = stockDAO.getByProductId(productId)
                    .orElseThrow(() -> new StockServiceException("Can't annulReservationInStock position " + position + " reservation, because it's not in stock."));
            int desiredPositionQuantity = position.getQuantity();
            int reservedStockPositionQuantity = stockPosition.getReservedQuantity();
            if (reservedStockPositionQuantity >= desiredPositionQuantity) {
                stockPosition.setReservedQuantity(reservedStockPositionQuantity - desiredPositionQuantity);
                stockDAO.update(stockPosition);
            } else {
                throw new StockServiceException("Can't takeFromStock position " + position + " from stock. Desired quantity " +
                        desiredPositionQuantity + ", total reserved quantity"
                        + reservedStockPositionQuantity + ".");
            }
        }
        return true;
    }
}
