package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.dao.StockDAO;
import com.vironit.onlinepharmacy.model.Position;
import com.vironit.onlinepharmacy.model.Procurement;

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
    public void put(Procurement procurement) {
        stockDAO.getAllByOwnerId(procurement.getId())
        Position existingPosition= stockDAO.get(position.getId()).orElse(new Position());
    }

    @Override
    public void take(Position position) {

    }
}
