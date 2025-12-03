package service;

import domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.CommentDAOImpl;
import repository.UserDAO;
import repository.UserDAOImpl;

public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDAO udao;

    public UserServiceImpl(){
        udao = new UserDAOImpl();
    }

    @Override
    public int insert(User user) {
        return udao.insert(user);
    }

    @Override
    public User getUser(User user) {
        return udao.getUser(user);
    }

    @Override
    public int lastLoginUpdate(User loginUser) {
        return udao.lastLoginUpdate(loginUser);
    }

    @Override
    public int update(User user) {
        return udao.update(user);
    }

    @Override
    public int remove(User user) {
        return udao.remove(user);
    }
}
