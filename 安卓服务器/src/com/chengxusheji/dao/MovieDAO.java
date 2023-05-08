package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.MovieType;
import com.chengxusheji.domain.Area;
import com.chengxusheji.domain.Movie;

@Service @Transactional
public class MovieDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddMovie(Movie movie) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(movie);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Movie> QueryMovieInfo(MovieType movieTypeObj,String movieName,String director,String mainPerformer,Area areaObj,String playTime,String recommendFlag,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Movie movie where 1=1";
    	if(null != movieTypeObj && movieTypeObj.getTypeId()!=0) hql += " and movie.movieTypeObj.typeId=" + movieTypeObj.getTypeId();
    	if(!movieName.equals("")) hql = hql + " and movie.movieName like '%" + movieName + "%'";
    	if(!director.equals("")) hql = hql + " and movie.director like '%" + director + "%'";
    	if(!mainPerformer.equals("")) hql = hql + " and movie.mainPerformer like '%" + mainPerformer + "%'";
    	if(null != areaObj && areaObj.getAreaId()!=0) hql += " and movie.areaObj.areaId=" + areaObj.getAreaId();
    	if(!playTime.equals("")) hql = hql + " and movie.playTime like '%" + playTime + "%'";
    	if(!recommendFlag.equals("")) hql = hql + " and movie.recommendFlag like '%" + recommendFlag + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List movieList = q.list();
    	return (ArrayList<Movie>) movieList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Movie> QueryMovieInfo(MovieType movieTypeObj,String movieName,String director,String mainPerformer,Area areaObj,String playTime,String recommendFlag) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Movie movie where 1=1";
    	if(null != movieTypeObj && movieTypeObj.getTypeId()!=0) hql += " and movie.movieTypeObj.typeId=" + movieTypeObj.getTypeId();
    	if(!movieName.equals("")) hql = hql + " and movie.movieName like '%" + movieName + "%'";
    	if(!director.equals("")) hql = hql + " and movie.director like '%" + director + "%'";
    	if(!mainPerformer.equals("")) hql = hql + " and movie.mainPerformer like '%" + mainPerformer + "%'";
    	if(null != areaObj && areaObj.getAreaId()!=0) hql += " and movie.areaObj.areaId=" + areaObj.getAreaId();
    	if(!playTime.equals("")) hql = hql + " and movie.playTime like '%" + playTime + "%'";
    	if(!recommendFlag.equals("")) hql = hql + " and movie.recommendFlag like '%" + recommendFlag + "%'";
    	Query q = s.createQuery(hql);
    	List movieList = q.list();
    	return (ArrayList<Movie>) movieList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Movie> QueryAllMovieInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Movie";
        Query q = s.createQuery(hql);
        List movieList = q.list();
        return (ArrayList<Movie>) movieList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(MovieType movieTypeObj,String movieName,String director,String mainPerformer,Area areaObj,String playTime,String recommendFlag) {
        Session s = factory.getCurrentSession();
        String hql = "From Movie movie where 1=1";
        if(null != movieTypeObj && movieTypeObj.getTypeId()!=0) hql += " and movie.movieTypeObj.typeId=" + movieTypeObj.getTypeId();
        if(!movieName.equals("")) hql = hql + " and movie.movieName like '%" + movieName + "%'";
        if(!director.equals("")) hql = hql + " and movie.director like '%" + director + "%'";
        if(!mainPerformer.equals("")) hql = hql + " and movie.mainPerformer like '%" + mainPerformer + "%'";
        if(null != areaObj && areaObj.getAreaId()!=0) hql += " and movie.areaObj.areaId=" + areaObj.getAreaId();
        if(!playTime.equals("")) hql = hql + " and movie.playTime like '%" + playTime + "%'";
        if(!recommendFlag.equals("")) hql = hql + " and movie.recommendFlag like '%" + recommendFlag + "%'";
        Query q = s.createQuery(hql);
        List movieList = q.list();
        recordNumber = movieList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Movie GetMovieByMovieId(int movieId) {
        Session s = factory.getCurrentSession();
        Movie movie = (Movie)s.get(Movie.class, movieId);
        return movie;
    }

    /*����Movie��Ϣ*/
    public void UpdateMovie(Movie movie) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(movie);
    }

    /*ɾ��Movie��Ϣ*/
    public void DeleteMovie (int movieId) throws Exception {
        Session s = factory.getCurrentSession();
        Object movie = s.load(Movie.class, movieId);
        s.delete(movie);
    }

}
