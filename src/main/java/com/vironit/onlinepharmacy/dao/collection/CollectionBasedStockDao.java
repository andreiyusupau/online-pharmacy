package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.StockDao;
import com.vironit.onlinepharmacy.model.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectionBasedStockDao implements StockDao {

    private final IdGenerator idGenerator;
    private final Collection<Position> stock = new ArrayList<>();

    public CollectionBasedStockDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Optional<Position> getByProductId(long productId) {
        return stock.stream()
                .filter(position -> position.getProduct().getId() == productId)
                .findFirst();
    }

    @Override
    public long add(Position position) {
        long id = idGenerator.getNextId();
        position.setId(id);
        boolean successfulAdd = stock.add(position);
        return successfulAdd ? id : -1L;
    }

    @Override
    public Optional<Position> get(long id) {
        return stock.stream()
                .filter(position -> position.getId() == id)
                .findFirst();
    }

    @Override
    public Collection<Position> getAll() {
        return stock;
    }

    @Override
    public boolean update(Position position) {
        return remove(position.getId()) && stock.add(position);
    }

    @Override
    public boolean remove(long id) {
        return stock.removeIf(position -> position.getId() == id);
    }

    @Override
    public int getTotalElements() {
        return stock.size();
    }

    @Override
    public Collection<Position> getPage(int currentPage, int pageLimit) {
        return stock.stream()
                .skip(currentPage*pageLimit)
                .limit(pageLimit)
                .collect(Collectors.toList());
    }
}
