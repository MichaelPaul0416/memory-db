package com.paul.base.sync.oracle;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/29
 * @Description:
 * @Resource:
 */
public class Pager {

    private int start;

    private int end;

    public int getEnd() {
        return start + PAGE_SIZE;
    }

    public static final int PAGE_SIZE = 100000;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
