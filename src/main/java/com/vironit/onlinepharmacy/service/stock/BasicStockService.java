package com.vironit.onlinepharmacy.service.stock;

import com.vironit.onlinepharmacy.dao.ReserveDao;
import com.vironit.onlinepharmacy.dao.StockDao;
import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Position;
import com.vironit.onlinepharmacy.service.stock.exception.StockException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BasicStockService implements StockService {

    private final StockDao stockDAO;
    private final ReserveDao reserveDao;

    public BasicStockService(StockDao stockDAO, ReserveDao reserveDao) {
        this.stockDAO = stockDAO;
        this.reserveDao = reserveDao;
    }

    @Override
    public long add(Position position) {
        long productId = position.getProduct()
                .getId();
        Optional<Position> stockPosition = stockDAO.getByProductId(productId);
        if (stockPosition.isPresent()) {
            Position updatedPosition = stockPosition.get();
            updatedPosition.setQuantity(updatedPosition.getQuantity() + position.getQuantity());
            stockDAO.update(updatedPosition);
            return updatedPosition.getId();
        } else {
            return stockDAO.add(position);
        }
    }

    @Override
    public Position get(long id) {
        return stockDAO.get(id).orElseThrow(() -> new StockException("Can't get stock position. Position with id " + id + " not found."));
    }

    @Override
    public Collection<Position> getAll() {
        return stockDAO.getAll();
    }

    @Override
    public void update(Position position) {
        stockDAO.update(position);
    }

    @Override
    public void remove(long id) {
        stockDAO.remove(id);
    }

    @Override
    public boolean addAll(Collection<Position> positions) {
        positions.forEach(this::add);
        return true;
    }

    @Override
    public boolean reserve(Collection<OperationPosition> operationPositions) {
        for (OperationPosition operationPosition : operationPositions) {
            long productId = operationPosition.getProduct()
                    .getId();
            Position stockPosition = stockDAO.getByProductId(productId)
                    .orElseThrow(()-> new StockException("Can't reserve position "+operationPosition+", because it's not in stock."));
            System.out.println(operationPosition);
            System.out.println(stockPosition);
            int reservedPositionQuantity = operationPosition.getQuantity();
                int stockPositionQuantity = stockPosition.getQuantity();
                if (stockPositionQuantity >= reservedPositionQuantity) {
                    stockPosition.setQuantity(stockPositionQuantity + reservedPositionQuantity);
                    stockDAO.update(stockPosition);
                    reserveDao.add(operationPosition);
                } else {
                    throw new StockException("Can't reserve position "+operationPosition+". Desired quantity "+
                            reservedPositionQuantity+", quantity in stock "+stockPositionQuantity+".");
                }
        }
        return true;
    }

    @Override
    public boolean take(long orderId) {
        return reserveDao.removeAllByOwnerId(orderId);
    }

    @Override
    public boolean annul(long orderId) {
        List<Position> positions = reserveDao.getAllByOwnerId(orderId)
                .stream()
                .map(operationPosition -> new Position(operationPosition.getId(), operationPosition.getQuantity(), operationPosition.getProduct()))
                .collect(Collectors.toList());
        reserveDao.removeAllByOwnerId(orderId);
        return addAll(positions);
    }
}
