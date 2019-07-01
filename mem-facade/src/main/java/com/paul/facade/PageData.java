package com.paul.facade;

import java.io.Serializable;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:
 * @Resource:
 */
public class PageData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int PAGE_SIZE = 10000;

    private int page;

    private int start;

    public PageData(){}

    public PageData(int start,int page){
        this.page = page;
        this.start = start;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "page=" + page +
                ", start=" + start +
                '}';
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
