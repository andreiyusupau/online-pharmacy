package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.User;

public interface UserDao extends Dao<User>, AuthenticationDao, PaginationDao<User> {
}
