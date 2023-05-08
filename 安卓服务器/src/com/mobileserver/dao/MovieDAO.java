package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Movie;
import com.mobileserver.util.DB;

public class MovieDAO {

	public List<Movie> QueryMovie(int movieTypeObj,String movieName,String director,String mainPerformer,int areaObj,String playTime,String recommendFlag) {
		List<Movie> movieList = new ArrayList<Movie>();
		DB db = new DB();
		String sql = "select * from Movie where 1=1";
		if (movieTypeObj != 0)
			sql += " and movieTypeObj=" + movieTypeObj;
		if (!movieName.equals(""))
			sql += " and movieName like '%" + movieName + "%'";
		if (!director.equals(""))
			sql += " and director like '%" + director + "%'";
		if (!mainPerformer.equals(""))
			sql += " and mainPerformer like '%" + mainPerformer + "%'";
		if (areaObj != 0)
			sql += " and areaObj=" + areaObj;
		if (!playTime.equals(""))
			sql += " and playTime like '%" + playTime + "%'";
		if (!recommendFlag.equals(""))
			sql += " and recommendFlag like '%" + recommendFlag + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieId(rs.getInt("movieId"));
				movie.setMovieTypeObj(rs.getInt("movieTypeObj"));
				movie.setMovieName(rs.getString("movieName"));
				movie.setMoviePhoto(rs.getString("moviePhoto"));
				movie.setDirector(rs.getString("director"));
				movie.setMainPerformer(rs.getString("mainPerformer"));
				movie.setDuration(rs.getString("duration"));
				movie.setAreaObj(rs.getInt("areaObj"));
				movie.setPlayTime(rs.getString("playTime"));
				movie.setPrice(rs.getFloat("price"));
				movie.setOpera(rs.getString("opera"));
				movie.setRecommendFlag(rs.getString("recommendFlag"));
				movie.setHitNum(rs.getInt("hitNum"));
				movieList.add(movie);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return movieList;
	}
	/* �����Ӱ���󣬽��е�Ӱ�����ҵ�� */
	public String AddMovie(Movie movie) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����µ�Ӱ */
			String sqlString = "insert into Movie(movieTypeObj,movieName,moviePhoto,director,mainPerformer,duration,areaObj,playTime,price,opera,recommendFlag,hitNum) values (";
			sqlString += movie.getMovieTypeObj() + ",";
			sqlString += "'" + movie.getMovieName() + "',";
			sqlString += "'" + movie.getMoviePhoto() + "',";
			sqlString += "'" + movie.getDirector() + "',";
			sqlString += "'" + movie.getMainPerformer() + "',";
			sqlString += "'" + movie.getDuration() + "',";
			sqlString += movie.getAreaObj() + ",";
			sqlString += "'" + movie.getPlayTime() + "',";
			sqlString += movie.getPrice() + ",";
			sqlString += "'" + movie.getOpera() + "',";
			sqlString += "'" + movie.getRecommendFlag() + "',";
			sqlString += movie.getHitNum();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��Ӱ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ӱ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����Ӱ */
	public String DeleteMovie(int movieId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Movie where movieId=" + movieId;
			db.executeUpdate(sqlString);
			result = "��Ӱɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ӱɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݵ�Ӱid��ȡ����Ӱ */
	public Movie GetMovie(int movieId) {
		Movie movie = null;
		DB db = new DB();
		String sql = "select * from Movie where movieId=" + movieId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				movie = new Movie();
				movie.setMovieId(rs.getInt("movieId"));
				movie.setMovieTypeObj(rs.getInt("movieTypeObj"));
				movie.setMovieName(rs.getString("movieName"));
				movie.setMoviePhoto(rs.getString("moviePhoto"));
				movie.setDirector(rs.getString("director"));
				movie.setMainPerformer(rs.getString("mainPerformer"));
				movie.setDuration(rs.getString("duration"));
				movie.setAreaObj(rs.getInt("areaObj"));
				movie.setPlayTime(rs.getString("playTime"));
				movie.setPrice(rs.getFloat("price"));
				movie.setOpera(rs.getString("opera"));
				movie.setRecommendFlag(rs.getString("recommendFlag"));
				movie.setHitNum(rs.getInt("hitNum"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return movie;
	}
	/* ���µ�Ӱ */
	public String UpdateMovie(Movie movie) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Movie set ";
			sql += "movieTypeObj=" + movie.getMovieTypeObj() + ",";
			sql += "movieName='" + movie.getMovieName() + "',";
			sql += "moviePhoto='" + movie.getMoviePhoto() + "',";
			sql += "director='" + movie.getDirector() + "',";
			sql += "mainPerformer='" + movie.getMainPerformer() + "',";
			sql += "duration='" + movie.getDuration() + "',";
			sql += "areaObj=" + movie.getAreaObj() + ",";
			sql += "playTime='" + movie.getPlayTime() + "',";
			sql += "price=" + movie.getPrice() + ",";
			sql += "opera='" + movie.getOpera() + "',";
			sql += "recommendFlag='" + movie.getRecommendFlag() + "',";
			sql += "hitNum=" + movie.getHitNum();
			sql += " where movieId=" + movie.getMovieId();
			db.executeUpdate(sql);
			result = "��Ӱ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ӱ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
