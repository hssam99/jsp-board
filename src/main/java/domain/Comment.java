package domain;

public class Comment {
    private int cno;
    private int bno;
    private String commenter;
    private String content;
    private String regdate;

    public Comment() {
    }

    public Comment(int cno, String content) {
        this.cno = cno;
        this.content = content;
    }

    public Comment(int bno, String commenter, String content) {
        this.bno = bno;
        this.commenter = commenter;
        this.content = content;
    }

    public Comment(String commenter, String content) {
        this.commenter = commenter;
        this.content = content;
    }

    public Comment(int cno, int bno, String commenter, String content, String regdate) {
        this.cno = cno;
        this.bno = bno;
        this.commenter = commenter;
        this.content = content;
        this.regdate = regdate;
    }

    public int getCno() {
        return cno;
    }

    public void setCno(int cno) {
        this.cno = cno;
    }

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cno=" + cno +
                ", bno=" + bno +
                ", commenter='" + commenter + '\'' +
                ", content='" + content + '\'' +
                ", regdate='" + regdate + '\'' +
                '}';
    }
}




