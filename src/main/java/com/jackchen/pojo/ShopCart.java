package com.jackchen.pojo;

import java.util.List;

public class ShopCart {
    private Long id;

    private Long pid;

    private Integer pnum;

    private Long uid;

    private Product product;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getPnum() {
        return pnum;
    }

    public void setPnum(Integer pnum) {
        this.pnum = pnum;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "id=" + id +
                ", pid=" + pid +
                ", pnum=" + pnum +
                ", uid=" + uid +
                ", product=" + product +
                ", user=" + user +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}