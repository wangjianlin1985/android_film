package com.mobileserver.domain;

public class OrderInfo {
    /*�������*/
    private String orderNo;
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /*�µ���Ӱ*/
    private int movieObj;
    public int getMovieObj() {
        return movieObj;
    }
    public void setMovieObj(int movieObj) {
        this.movieObj = movieObj;
    }

    /*��Ӱ�۸�*/
    private float moviePrice;
    public float getMoviePrice() {
        return moviePrice;
    }
    public void setMoviePrice(float moviePrice) {
        this.moviePrice = moviePrice;
    }

    /*��������*/
    private int orderNum;
    public int getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    /*�����ܼ�*/
    private float orderPrice;
    public float getOrderPrice() {
        return orderPrice;
    }
    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }

    /*�µ��û�*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*�µ�ʱ��*/
    private String orderTime;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /*�ջ���*/
    private String receiveName;
    public String getReceiveName() {
        return receiveName;
    }
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    /*�ջ��˵绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*�ջ��˵�ַ*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*����״̬*/
    private int orderStateObj;
    public int getOrderStateObj() {
        return orderStateObj;
    }
    public void setOrderStateObj(int orderStateObj) {
        this.orderStateObj = orderStateObj;
    }

    /*������ע*/
    private String orderMemo;
    public String getOrderMemo() {
        return orderMemo;
    }
    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

}