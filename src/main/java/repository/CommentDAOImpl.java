package repository;

import domain.Comment;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import orm.DatabaseBuilder;

import java.util.List;

public class CommentDAOImpl implements CommentDAO {

    private static final Logger log = LoggerFactory.getLogger(CommentDAOImpl.class);

    private SqlSession sql;

    public CommentDAOImpl(){
        new DatabaseBuilder();
        sql = DatabaseBuilder.getFactory().openSession();
    }


    @Override
    public int post(Comment newComment) {
        log.info("✅DAO post() 진입완료");
//        insert, update, delete => commit 필요
        int isOk = sql.insert("commentMapper.add", newComment);
        if(isOk>0) sql.commit();
        return isOk;
    }

    @Override
    public List<Comment> getList(int bno) {
        return sql.selectList("commentMapper.list", bno);
    }

    @Override
    public int remove(int cno) {
        int isOk = sql.delete("commentMapper.remove", cno);
        if(isOk>0) sql.commit();
        return isOk;
    }

    @Override
    public int update(Comment c) {
        int isOk = sql.update("commentMapper.update", c);
        if(isOk>0) sql.commit();
        return isOk;
    }

}
