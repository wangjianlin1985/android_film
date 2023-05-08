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
	/* 传入电影类型对象，进行电影类型的添加业务 */
	public String AddMovieType(MovieType movieType) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新电影类型 */
			String sqlString = "insert into MovieType(typeName) values (";
			sqlString += "'" + movieType.getTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "电影类型添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "电影类型添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除电影类型 */
	public String DeleteMovieType(int typeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from MovieType where typeId=" + typeId;
			db.executeUpdate(sqlString);
			result = "电影类型删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "电影类型删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据类型id获取到电影类型 */
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
	/* 更新电影类型 */
	public String UpdateMovieType(MovieType movieType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update MovieType set ";
			sql += "typeName='" + movieType.getTypeName() + "'";
			sql += " where typeId=" + movieType.getTypeId();
			db.executeUpdate(sql);
			result = "电影类型更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "电影类型更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
