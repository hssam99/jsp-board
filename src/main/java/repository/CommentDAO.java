package repository;

import domain.Comment;

import java.util.List;

public interface CommentDAO {
    int post(Comment newComment);

    List<Comment> getList(int bno);

    int remove(int cno);

    int update(Comment c);
}
