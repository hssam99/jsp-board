package service;

import domain.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.CommentDAO;
import repository.CommentDAOImpl;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    private CommentDAO cdao;

    public CommentServiceImpl(){
        cdao = new CommentDAOImpl();
    }


    @Override
    public int post(Comment newComment) {
        return cdao.post(newComment);
    }

    @Override
    public List<Comment> getList(int bno) {
        return cdao.getList(bno);
    }

    @Override
    public int remove(int cno) {
        return cdao.remove(cno);
    }

    @Override
    public int update(Comment c) {
        return cdao.update(c);
    }
}
