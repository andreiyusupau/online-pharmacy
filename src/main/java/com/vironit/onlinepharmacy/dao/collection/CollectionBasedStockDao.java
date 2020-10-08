package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.StockDao;
import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectionBasedStockDao implements StockDao {

    private final IdGenerator idGenerator;
    private final Collection<Position> stock= new ArrayList<>();
    private final Collection<OperationPosition> reserved= new ArrayList<>();

    public CollectionBasedStockDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public boolean createAll(Collection<Position> positions) {
        for (Position createdPosition : positions) {
            boolean positionShouldBeCreated = true;
            for (Position stockPosition : stock) {
                if (createdPosition.getProduct().equals(stockPosition.getProduct())) {
                    stockPosition.setQuantity(stockPosition.getQuantity() + createdPosition.getQuantity());
                    positionShouldBeCreated = false;
                    break;
                }
            }
            if (positionShouldBeCreated) {
                long id=idGenerator.getNextId();
                createdPosition.setId(id);
                stock.add(createdPosition);
            }
        }
        return true;
    }

    @Override
    public boolean reserve(Collection<OperationPosition> operationPositions) {
        for (OperationPosition reservedPosition : operationPositions) {
            boolean positionIsInStock=false;
            for (Position stockPosition : stock) {
                if (reservedPosition.getProduct().equals(stockPosition.getProduct())) {
                 if(reservedPosition.getQuantity()>stockPosition.getQuantity()){
                     //TODO:throw
                 }
                 positionIsInStock=true;
                }
            }
            if(!positionIsInStock){
                //TODO:throw
            }
        }
        reserved.addAll(operationPositions);
        for (OperationPosition operationPosition:operationPositions){
           stock.removeIf(stockPosition -> stockPosition.getId()==operationPosition.getId());
        }
        return true;
    }

    @Override
    public boolean annul(long orderId) {
       Collection<OperationPosition> positionsToRemove= reserved.stream()
               .filter(position -> position.getId()==orderId)
               .collect(Collectors.toList());
       reserved.removeAll(positionsToRemove);
       //TODO:separate method
       Collection<Position> positions=positionsToRemove.stream()
               .map(operationPosition -> new Position(operationPosition.getId(),operationPosition.getQuantity(), operationPosition.getProduct()))
               .collect(Collectors.toList());
        createAll(positions);
        return true;
    }

    @Override
    public long add(Position position) {
        return 0;
    }

    @Override
    public Optional<Position> get(long id) {
        return Optional.empty();
    }

    @Override
    public Collection<Position> getAll() {
        return stock;
    }

    @Override
    public boolean update(Position position) {
        return false;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @Override
    public boolean addAll(Collection<OperationPosition> t) {
        return false;
    }

    @Override
    public Collection<OperationPosition> getAllByOwnerId(long id) {
        return reserved.stream()
                .filter(procurement -> procurement.getOperation().getId()==id)
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return reserved.removeIf(operationPosition -> operationPosition.getOperation().getId()==id);
    }
}
