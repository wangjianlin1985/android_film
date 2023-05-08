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
	/* ����������󣬽��е��������ҵ�� */
	public String AddArea(Area area) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����µ��� */
			String sqlString = "insert into Area(areaName) values (";
			sqlString += "'" + area.getAreaName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������ */
	public String DeleteArea(int areaId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Area where areaId=" + areaId;
			db.executeUpdate(sqlString);
			result = "����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݵ���id��ȡ������ */
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
	/* ���µ��� */
	public String UpdateArea(Area area) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Area set ";
			sql += "areaName='" + area.getAreaName() + "'";
			sql += " where areaId=" + area.getAreaId();
			db.executeUpdate(sql);
			result = "�������³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
