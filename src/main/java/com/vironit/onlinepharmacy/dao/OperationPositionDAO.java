package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.dao.DAO;
import com.vironit.onlinepharmacy.dao.SlaveDAO;
import com.vironit.onlinepharmacy.model.OperationPosition;

public interface OperationPositionDAO extends DAO<OperationPosition>, SlaveDAO<OperationPosition> {
}
