package entity;


import java.util.List;

/**
 * @program: PageResult
 * @description: 返回数据
 * @author: Song
 * @create: Created in 2019-02-17 21:31
 * @Modified by:
 **/
public class PageResult<T> {
    private Long total;
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
