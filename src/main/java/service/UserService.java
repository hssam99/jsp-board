package service;

import domain.User;

public interface UserService {

    int insert(User user);

    User getUser(User user);

    int lastLoginUpdate(User loginUser);

    int update(User user);

    int remove(User user);
}
