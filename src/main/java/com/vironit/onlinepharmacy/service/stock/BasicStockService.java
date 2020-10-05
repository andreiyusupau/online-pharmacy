package com.vironit.onlinepharmacy.service.stock;

import com.vironit.onlinepharmacy.dao.StockDAO;
import com.vironit.onlinepharmacy.model.Position;

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
        //TODO:add nice exception
        return stockDAO.get(id).orElseThrow();
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
    public boolean reserve(Collection<Position> positions) {
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
