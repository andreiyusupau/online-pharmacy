package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.AuthenticationDAO;
import com.vironit.onlinepharmacy.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionBasedAuthenticationDAO implements AuthenticationDAO {

    private final List<User> userList = new ArrayList<>();
    private long currentId = 0;

    @Override
    public long add(User user) {
        user.setId(currentId);
        currentId++;
        userList.add(user);
        return user.getId();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
