package repository;

import domain.Board;
import domain.PagingVO;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import orm.DatabaseBuilder;
import service.BoardServiceImpl;

import java.util.List;

public class BoardDAOImpl implements BoardDAO {

    //    로그 객체
    private static final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);
    private SqlSession sql;

    public BoardDAOImpl() {
        new DatabaseBuilder();
        sql = DatabaseBuilder.getFactory().openSession(true);
    }
    public BoardDAOImpl(BoardServiceImpl boardService) {}

    @Override
    public int insert(Board b) {
//        sql.메소드("매퍼이름.아이디", 전달객체);
//        sql.insert("BoardMapper.insert", b);
//        메소드 -> select, insert, update, delete
        log.info(">>> BoardDAOImpl insert() 호출됨");
        int isOk = sql.insert("boardMapper.insert", b);
        //        insert, update, delete DB 자체의 값이 변경되는 구문 > commit 필요
        if(isOk > 0){
            sql.commit();
            log.info(">>> insert 성공");
        } else {
            log.info(">>> insert 실패");
        }

        return isOk;
    }

    @Override
    public List<Board> getList() {
        log.info(">>> BoardDAOImpl getList() 호출됨");
        List<Board> list = sql.selectList("boardMapper.list");
        return list;
    }

    @Override
    public Board getDetail(int bno) {
        return sql.selectOne("boardMapper.detail", bno);
    }

    @Override
    public int update(Board board) {
        int isOk = sql.update("boardMapper.update", board);
        if(isOk > 0){
            sql.commit();
            log.info(">>> update 성공");
        } else {
            log.info(">>> update 실패");
        }
        return isOk;
    }

    @Override
    public int remove(int bno) {
        log.info(">>> BoardDAOImpl remove() 호출됨");
        int isOk = sql.delete("boardMapper.remove", bno);  // 👈 delete로 변경!

        if(isOk > 0) {
            sql.commit();
            log.info(">>> delete 성공");
        } else {
            log.info(">>> delete 실패");
        }

        return isOk;
    }

    @Override
    public List<Board> getPageList(PagingVO pageVO) {
        return sql.selectList("boardMapper.getPageList", pageVO);
    }

    @Override
    public int getTotal(PagingVO pageVO) {
        return sql.selectOne("boardMapper.getTotal", pageVO);
    }

}
