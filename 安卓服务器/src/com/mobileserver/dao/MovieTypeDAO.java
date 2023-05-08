package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.MovieType;
import com.mobileserver.util.DB;

public class MovieTypeDAO {

	public List<MovieType> QueryMovieType() {
		List<MovieType> movieTypeList = new ArrayList<MovieType>();
		DB db = new DB();
		String sql = "select * from MovieType where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				MovieType movieType = new MovieType();
				movieType.setTypeId(rs.getInt("typeId"));
				movieType.setTypeName(rs.getString("typeName"));
				movieTypeList.add(movieType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return movieTypeList;
	}
	/* �����Ӱ���Ͷ��󣬽��е�Ӱ���͵����ҵ�� */
	public String AddMovieType(MovieType movieType) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����µ�Ӱ���� */
			String sqlString = "insert into MovieType(typeName) values (";
			sqlString += "'" + movieType.getTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��Ӱ������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ӱ�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����Ӱ���� */
	public String DeleteMovieType(int typeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from MovieType where typeId=" + typeId;
			db.executeUpdate(sqlString);
			result = "��Ӱ����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ӱ����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ��������id��ȡ����Ӱ���� */
	public MovieType GetMovieType(int typeId) {
		MovieType movieType = null;
		DB db = new DB();
		String sql = "select * from MovieType where typeId=" + typeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				movieType = new MovieType();
				movieType.setTypeId(rs.getInt("typeId"));
				movieType.setTypeName(rs.getString("typeName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return movieType;
	}
	/* ���µ�Ӱ���� */
	public String UpdateMovieType(MovieType movieType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update MovieType set ";
			sql += "typeName='" + movieType.getTypeName() + "'";
			sql += " where typeId=" + movieType.getTypeId();
			db.executeUpdate(sql);
			result = "��Ӱ���͸��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ӱ���͸���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
