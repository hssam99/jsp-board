package service;

import domain.Board;
import domain.PagingVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.BoardDAO;
import repository.BoardDAOImpl;

import java.util.List;

public class BoardServiceImpl implements BoardService {

//    로그 객체
    private static final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);

//    BoardDAO 인터페이스 생성
    private BoardDAO bdao;

    public BoardServiceImpl() {
        bdao = new BoardDAOImpl();
    }

    @Override
    public int insert(Board b) {
        return bdao.insert(b);
    }

    @Override
    public List<Board> getList() {
        return bdao.getList();
    }

    @Override
    public Board getDetail(int bno) {
        return bdao.getDetail(bno);
    }

    @Override
    public int update(Board board) {
        return bdao.update(board);
    }

    @Override
    public int remove(int bno) {
        return bdao.remove(bno);
    }

    @Override
    public List<Board> getPageList(PagingVO pageVO) {
        return bdao.getPageList(pageVO);
    }

    @Override
    public int getTotal(PagingVO pageVO) {
        return bdao.getTotal(pageVO);
    }


}
