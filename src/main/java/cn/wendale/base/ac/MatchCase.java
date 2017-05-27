package cn.wendale.base.ac;

/**
 * Created with IntelliJ IDEA.
 * User: yang.wenjun
 * Date: 2017/5/27
 * Time: 下午4:43
 * Description:
 */


public class MatchCase<T> {

    private int start;

    private int end;

    private T keyword;

    public MatchCase(int start, int end, T keyword) {
        this.start = start;
        this.end = end;
        this.keyword = keyword;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public T getKeyword() {
        return keyword;
    }

    public void setKeyword(T keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "MatchCase{" +
                "start=" + start +
                ", end=" + end +
                ", keyword=" + keyword +
                '}';
    }
}
