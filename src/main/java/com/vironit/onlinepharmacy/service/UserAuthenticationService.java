package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.dao.DAO;
import com.vironit.onlinepharmacy.model.User;

public class UserAuthenticationService implements AuthenticationService{

    private final DAO<User> userDAO;

    public UserAuthenticationService(DAO<User> userDAO) {
        this.userDAO = userDAO;
    }
@Override
    public boolean login(User user){
//TODO:getUserByEmail
    //TODO:if(ok) compare passwords
    return false;
    }
@Override
    public boolean register(User user){
        //TODO:hashPass
        long id=userDAO.add(user);
    System.out.println("registeredUserWithId");
    return false;
    }
}
