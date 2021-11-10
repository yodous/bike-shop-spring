package com.example.mapper;

import com.example.dto.UserView;
import com.example.model.User;

public class UserViewMapper {
    private UserViewMapper() {
    }

    public static UserView mapToView(User user) {
        return new UserView(user.getId(), user.getUsername(), user.getFirstName() + user.getLastName());
    }


}