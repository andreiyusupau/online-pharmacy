package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.User;

public interface UserDao extends CrudDao<User>, AuthenticationDao, PaginationDao<User> {
}
