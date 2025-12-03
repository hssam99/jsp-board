package handler;

import domain.PagingVO;

public class PagingHandler {
//    DB나 서버에서는 필요하지 않지만 다른쪽에서 처리가 필요한 경우 사용하는 핸들러 클래스
//    DB에서 필요한 객체 VO -> domain
//    데이터 전달용 객체 DTO -> domain
//    그 외 모든 처리는 Handler

//    list 하단에 페이지네이션의 핸들링을 위한 클래스
//    < 1 2 3 4 5 6 7 8 9 10 >
    final int pageGroupSize = 8; // 한 화면에 출력할 페이지네이션 개수
    private int startPage; //mapper 에서 limit 시작 번지
    private int endPage;   //mapper 에서 limit 개수
    private boolean prev; // < 이전 페이지의 존재여부
    private boolean next; // > 다음 페이지의 존재여부
    private int realEndPage; // 실제 마지막 페이지 번호

    private PagingVO pageVO; // 서버(컨트롤러)에서 전달받은 페이지네이션 정보
    private int totalCount; // DB에서 조회한 전체 게시글 수

    public PagingHandler(PagingVO pageVO, int totalCount) {
        this.pageVO = pageVO;
        this.totalCount = totalCount;

        // pageNo 1~10 => endPage 10
        // pageNo 11~20 => endPage 20
        // pageNo / 10 + 1 * 10
        this.endPage = (int)(Math.ceil(this.pageVO.getPageNo() / (double)pageGroupSize)) * pageGroupSize;
        this.startPage = this.endPage - (pageGroupSize-1);

//        realEndPage 205 / 10 = 20.5 => 21
//        소수점에 값이 남으면 무조건 한페이지가 더 필요 (5개의 게시글을 출력하기 위해서 한페이지가 필요)
        this.realEndPage = (int)(Math.ceil(this.totalCount / (double)this.pageVO.getQty()));

//        실제 마지막 페이지 번호가 endPage 보다 작으면 endPage 값을 변경
        if(this.realEndPage < this.endPage){
            this.endPage = this.realEndPage;
        }

        // 이전 다음 유무
        this.prev = this.startPage > 1;
        this.next = this.endPage < this.realEndPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isPrev() {
        return prev;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public PagingVO getPageVO() {
        return pageVO;
    }

    public void setPageVO(PagingVO pageVO) {
        this.pageVO = pageVO;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int pageGroupSize() {
        return pageGroupSize;
    }

    public int getRealEndPage() {
        return realEndPage;
    }

    public void setRealEndPage(int realEndPage) {
        this.realEndPage = realEndPage;
    }

    @Override
    public String toString() {
        return "PagingHandler{" +
                "pageNationSize=" + pageGroupSize +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                ", prev=" + prev +
                ", next=" + next +
                ", realEndPage=" + realEndPage +
                ", pageVO=" + pageVO +
                ", totalCount=" + totalCount +
                '}';
    }
}
