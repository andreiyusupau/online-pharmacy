package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.OperationPosition;

public interface ReserveDao extends CrudDao<OperationPosition>, SlaveDao<OperationPosition> {

}
