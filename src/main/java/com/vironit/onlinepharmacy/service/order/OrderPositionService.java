package com.vironit.onlinepharmacy.service.order;

import com.vironit.onlinepharmacy.model.OrderPosition;
import com.vironit.onlinepharmacy.service.CrudService;
import com.vironit.onlinepharmacy.service.SlaveService;

public interface OrderPositionService extends CrudService<OrderPosition,OrderPosition,OrderPosition>, SlaveService<OrderPosition> {

}
