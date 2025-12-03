package repository;

import domain.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import orm.DatabaseBuilder;

public class UserDAOImpl implements UserDAO {
    private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);

    private SqlSession sql;

    public UserDAOImpl(){
        new DatabaseBuilder();
        sql = DatabaseBuilder.getFactory().openSession();
    }

    @Override
    public int insert(User user) {
        log.info(">>> UserDAO insert() 호출됨");
        int isOk = sql.insert("userMapper.insert", user);
        if(isOk > 0) sql.commit();
        return isOk;
    }

    @Override
    public User getUser(User user) {
        log.info(">>> UserDAO getUser() 호출됨");
        return sql.selectOne("userMapper.getUser", user);
    }

    @Override
    public int lastLoginUpdate(User loginUser) {
        log.info(">>> UserDAO lastLoginUpdate() 호출됨");
        int isOk = sql.update("userMapper.updateLastLogin", loginUser);
        if(isOk > 0) sql.commit();
        return isOk;
    }

    @Override
    public int update(User user) {
        log.info(">>> UserDAO update() 호출됨");
        int isOk = sql.update("userMapper.update", user);
        if(isOk > 0) sql.commit();
        return isOk;
    }

    @Override
    public int remove(User user) {
        log.info(">>> UserDAO remove() 호출됨");
        int isOk = sql.delete("userMapper.delete", user);
        if(isOk > 0) sql.commit();
        return isOk;
    }
}
