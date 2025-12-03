package repository;

import domain.User;

public interface UserDAO {
    int insert(User user);

    User getUser(User user);

    int lastLoginUpdate(User loginUser);

    int update(User user);

    int remove(User user);
}
