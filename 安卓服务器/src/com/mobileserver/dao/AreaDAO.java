package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Area;
import com.mobileserver.util.DB;

public class AreaDAO {

	public List<Area> QueryArea() {
		List<Area> areaList = new ArrayList<Area>();
		DB db = new DB();
		String sql = "select * from Area where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Area area = new Area();
				area.setAreaId(rs.getInt("areaId"));
				area.setAreaName(rs.getString("areaName"));
				areaList.add(area);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return areaList;
	}
	/* 传入地区对象，进行地区的添加业务 */
	public String AddArea(Area area) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新地区 */
			String sqlString = "insert into Area(areaName) values (";
			sqlString += "'" + area.getAreaName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "地区添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "地区添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除地区 */
	public String DeleteArea(int areaId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Area where areaId=" + areaId;
			db.executeUpdate(sqlString);
			result = "地区删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "地区删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据地区id获取到地区 */
	public Area GetArea(int areaId) {
		Area area = null;
		DB db = new DB();
		String sql = "select * from Area where areaId=" + areaId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				area = new Area();
				area.setAreaId(rs.getInt("areaId"));
				area.setAreaName(rs.getString("areaName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return area;
	}
	/* 更新地区 */
	public String UpdateArea(Area area) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Area set ";
			sql += "areaName='" + area.getAreaName() + "'";
			sql += " where areaId=" + area.getAreaId();
			db.executeUpdate(sql);
			result = "地区更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "地区更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
