package service;

import domain.Comment;

import java.util.List;

public interface CommentService {
    int post(Comment newComment);

    List<Comment> getList(int bno);

    int remove(int cno);

    int update(Comment c);
}
