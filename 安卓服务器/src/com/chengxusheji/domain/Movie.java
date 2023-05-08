package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Movie {
    /*��Ӱid*/
    private int movieId;
    public int getMovieId() {
        return movieId;
    }
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    /*ӰƬ����*/
    private MovieType movieTypeObj;
    public MovieType getMovieTypeObj() {
        return movieTypeObj;
    }
    public void setMovieTypeObj(MovieType movieTypeObj) {
        this.movieTypeObj = movieTypeObj;
    }

    /*ӰƬ����*/
    private String movieName;
    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    /*ӰƬͼƬ*/
    private String moviePhoto;
    public String getMoviePhoto() {
        return moviePhoto;
    }
    public void setMoviePhoto(String moviePhoto) {
        this.moviePhoto = moviePhoto;
    }

    /*����*/
    private String director;
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }

    /*����*/
    private String mainPerformer;
    public String getMainPerformer() {
        return mainPerformer;
    }
    public void setMainPerformer(String mainPerformer) {
        this.mainPerformer = mainPerformer;
    }

    /*ʱ��*/
    private String duration;
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /*����*/
    private Area areaObj;
    public Area getAreaObj() {
        return areaObj;
    }
    public void setAreaObj(Area areaObj) {
        this.areaObj = areaObj;
    }

    /*��ӳʱ��*/
    private String playTime;
    public String getPlayTime() {
        return playTime;
    }
    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    /*Ʊ��*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*������*/
    private String opera;
    public String getOpera() {
        return opera;
    }
    public void setOpera(String opera) {
        this.opera = opera;
    }

    /*�Ƿ��Ƽ�*/
    private String recommendFlag;
    public String getRecommendFlag() {
        return recommendFlag;
    }
    public void setRecommendFlag(String recommendFlag) {
        this.recommendFlag = recommendFlag;
    }

    /*�����*/
    private int hitNum;
    public int getHitNum() {
        return hitNum;
    }
    public void setHitNum(int hitNum) {
        this.hitNum = hitNum;
    }

}