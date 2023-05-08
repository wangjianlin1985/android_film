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

@Service @Transactional
public class MovieTypeDAO {

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
    public void AddMovieType(MovieType movieType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(movieType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<MovieType> QueryMovieTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From MovieType movieType where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List movieTypeList = q.list();
    	return (ArrayList<MovieType>) movieTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<MovieType> QueryMovieTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From MovieType movieType where 1=1";
    	Query q = s.createQuery(hql);
    	List movieTypeList = q.list();
    	return (ArrayList<MovieType>) movieTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<MovieType> QueryAllMovieTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From MovieType";
        Query q = s.createQuery(hql);
        List movieTypeList = q.list();
        return (ArrayList<MovieType>) movieTypeList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From MovieType movieType where 1=1";
        Query q = s.createQuery(hql);
        List movieTypeList = q.list();
        recordNumber = movieTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public MovieType GetMovieTypeByTypeId(int typeId) {
        Session s = factory.getCurrentSession();
        MovieType movieType = (MovieType)s.get(MovieType.class, typeId);
        return movieType;
    }

    /*����MovieType��Ϣ*/
    public void UpdateMovieType(MovieType movieType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(movieType);
    }

    /*ɾ��MovieType��Ϣ*/
    public void DeleteMovieType (int typeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object movieType = s.load(MovieType.class, typeId);
        s.delete(movieType);
    }

}
