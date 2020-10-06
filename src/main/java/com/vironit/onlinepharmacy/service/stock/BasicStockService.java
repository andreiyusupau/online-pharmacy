package com.vironit.onlinepharmacy.service.stock;

import com.vironit.onlinepharmacy.dao.StockDAO;
import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Position;
import com.vironit.onlinepharmacy.service.stock.exception.StockException;

import java.util.Collection;

public class BasicStockService implements StockService {

    private final StockDAO stockDAO;

    public BasicStockService(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    @Override
    public long add(Position position) {
        return stockDAO.add(position);
    }

    @Override
    public Position get(long id) {
        return stockDAO.get(id).orElseThrow(()->new StockException("Can't get stock position. Position with id "+id+" not found."));
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
    public boolean put(Collection<Position> positions) {
    return stockDAO.createAll(positions);
    }

    @Override
    public boolean reserve(Collection<OperationPosition> positions) {
       return stockDAO.reserve(positions);
    }

    @Override
    public boolean take(long orderId) {
return stockDAO.removeAllByOwnerId(orderId);
    }

    @Override
    public boolean annul(long orderId) {
       return stockDAO.annul(orderId);
    }
}
