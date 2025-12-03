package domain;

public class Board {
    private int bno;
    private String title;
    private String writer;
    private String content;
    private String regdate;
    private String moddate;
    private String imagefile;

    public Board() {
    }

    public Board(int bno) {
        this.bno = bno;
    }

//    insert 용 생성자
    public Board(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

//    update 용 생성자
    public Board(int bno, String title, String content) {
        this.bno = bno;
        this.title = title;
        this.content = content;
    }

    public Board(String title, String content, String imagefile, int bno) {
        this.title = title;
        this.content = content;
        this.imagefile = imagefile;
        this.bno = bno;
    }

    //    list
    public Board(int bno, String title, String writer, String regdate) {
        this.bno = bno;
        this.title = title;
        this.writer = writer;
        this.regdate = regdate;
    }

//    detail
    public Board(String title, String writer, String content, String regdate) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.regdate = regdate;
    }

//    전체
    public Board(int bno, String title, String writer, String content, String regdate, String moddate) {
        this.bno = bno;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.regdate = regdate;
        this.moddate = moddate;
    }

    public Board(int bno, String title, String writer, String content, String regdate, String moddate, String imagefile) {
        this.bno = bno;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.regdate = regdate;
        this.moddate = moddate;
        this.imagefile = imagefile;
    }

    @Override
    public String toString() {
        return "Board{" +
                "bno=" + bno +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                ", regdate='" + regdate + '\'' +
                ", moddate='" + moddate + '\'' +
                ", imagefile='" + imagefile + '\'' +
                '}';
    }

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getModdate() {
        return moddate;
    }

    public void setModdate(String moddate) {
        this.moddate = moddate;
    }

    public String getImagefile() {
        return imagefile;
    }

    public void setImagefile(String imagefile) {
        this.imagefile = imagefile;
    }
}
