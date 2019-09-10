package com.jackchen.utils;

import com.jackchen.pojo.Product;

import java.util.List;

public class Page {
    private Long totalImgs;//总图片
    private int totalPages;//总页数
    private int pageImgs;//当前页的图片
    private int pageNo;//当前页
    private int lastPage;//上一页
    private int nextPage;//下一页
    private List<Product> list = null;

    public Page() {
    }

    public Long getTotalImgs() {
        return totalImgs;
    }

    public void setTotalImgs(Long totalImgs) {
        this.totalImgs = totalImgs;
    }

    public int getTotalPages() {
        totalPages = (int)(totalImgs/pageImgs);
        if(totalImgs%pageImgs!=0){
            totalPages += 1;
        }
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageImgs() {
        return pageImgs;
    }

    public void setPageImgs(int pageImgs) {
        this.pageImgs = pageImgs;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getLastPage() {
        if(pageNo<=1){
            lastPage = 1;
        }else {
            lastPage = pageNo-1;
        }
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getNextPage() {
        if(nextPage>=getTotalPages()){
            nextPage = getTotalPages();
        }else {
            nextPage = pageNo+1;
        }
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }
}
