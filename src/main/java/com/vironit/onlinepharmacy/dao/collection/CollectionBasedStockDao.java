package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.StockDao;
import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import com.vironit.onlinepharmacy.model.StockPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectionBasedStockDao implements StockDao {

    private final IdGenerator idGenerator;
    private final Collection<StockPosition> stock = new ArrayList<>();

    public CollectionBasedStockDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Optional<StockPosition> getByProductId(long productId) {
        return stock.stream()
                .filter(stockPosition -> stockPosition.getProduct().getId() == productId)
                .findFirst();
    }

    @Override
    public long add(StockPosition stockPosition) {
        long id = idGenerator.getNextId();
        stockPosition.setId(id);
        boolean successfulAdd = stock.add(stockPosition);
        return successfulAdd ? id : -1L;
    }

    @Override
    public Optional<StockPosition> get(long id) {
        return stock.stream()
                .filter(stockPosition -> stockPosition.getId() == id)
                .findFirst();
    }

    @Override
    public Collection<StockPosition> getAll() {
        return stock;
    }

    @Override
    public boolean update(StockPosition stockPosition) {
        return remove(stockPosition.getId()) && stock.add(stockPosition);
    }

    @Override
    public boolean remove(long id) {
        return stock.removeIf(stockPosition -> stockPosition.getId() == id);
    }

    @Override
    public int getTotalElements() {
        return stock.size();
    }

    @Override
    public Collection<StockPosition> getPage(int currentPage, int pageLimit) {
        return stock.stream()
                .skip((currentPage-1)*pageLimit)
                .limit(pageLimit)
                .collect(Collectors.toList());
    }
}
