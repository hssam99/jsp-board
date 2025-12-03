package repository;

import domain.Board;
import domain.PagingVO;

import java.util.List;

public interface BoardDAO {
    int insert(Board b);


    List<Board> getList();

    Board getDetail(int bno);

    int update(Board board);

    int remove(int bno);

    List<Board> getPageList(PagingVO pageVO);

    int getTotal(PagingVO pageVO);
}
