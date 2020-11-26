package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.User;

/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository.UserRepository}
 */
@Deprecated
public interface UserDao extends CrudDao<User>, AuthenticationDao, PaginationDao<User> {
}
