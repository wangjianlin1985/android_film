package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.OrderInfo;
import com.mobileserver.util.DB;

public class OrderInfoDAO {

	public List<OrderInfo> QueryOrderInfo(String orderNo,int movieObj,String userObj,String orderTime,String receiveName,String telephone,int orderStateObj) {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		DB db = new DB();
		String sql = "select * from OrderInfo where 1=1";
		if (!orderNo.equals(""))
			sql += " and orderNo like '%" + orderNo + "%'";
		if (movieObj != 0)
			sql += " and movieObj=" + movieObj;
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!orderTime.equals(""))
			sql += " and orderTime like '%" + orderTime + "%'";
		if (!receiveName.equals(""))
			sql += " and receiveName like '%" + receiveName + "%'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		if (orderStateObj != 0)
			sql += " and orderStateObj=" + orderStateObj;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderNo(rs.getString("orderNo"));
				orderInfo.setMovieObj(rs.getInt("movieObj"));
				orderInfo.setMoviePrice(rs.getFloat("moviePrice"));
				orderInfo.setOrderNum(rs.getInt("orderNum"));
				orderInfo.setOrderPrice(rs.getFloat("orderPrice"));
				orderInfo.setUserObj(rs.getString("userObj"));
				orderInfo.setOrderTime(rs.getString("orderTime"));
				orderInfo.setReceiveName(rs.getString("receiveName"));
				orderInfo.setTelephone(rs.getString("telephone"));
				orderInfo.setAddress(rs.getString("address"));
				orderInfo.setOrderStateObj(rs.getInt("orderStateObj"));
				orderInfo.setOrderMemo(rs.getString("orderMemo"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderInfoList;
	}
	/* 传入订单对象，进行订单的添加业务 */
	public String AddOrderInfo(OrderInfo orderInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新订单 */
			String sqlString = "insert into OrderInfo(orderNo,movieObj,moviePrice,orderNum,orderPrice,userObj,orderTime,receiveName,telephone,address,orderStateObj,orderMemo) values (";
			sqlString += "'" + orderInfo.getOrderNo() + "',";
			sqlString += orderInfo.getMovieObj() + ",";
			sqlString += orderInfo.getMoviePrice() + ",";
			sqlString += orderInfo.getOrderNum() + ",";
			sqlString += orderInfo.getOrderPrice() + ",";
			sqlString += "'" + orderInfo.getUserObj() + "',";
			sqlString += "'" + orderInfo.getOrderTime() + "',";
			sqlString += "'" + orderInfo.getReceiveName() + "',";
			sqlString += "'" + orderInfo.getTelephone() + "',";
			sqlString += "'" + orderInfo.getAddress() + "',";
			sqlString += orderInfo.getOrderStateObj() + ",";
			sqlString += "'" + orderInfo.getOrderMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "订单添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "订单添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除订单 */
	public String DeleteOrderInfo(String orderNo) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from OrderInfo where orderNo='" + orderNo + "'";
			db.executeUpdate(sqlString);
			result = "订单删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "订单删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据订单编号获取到订单 */
	public OrderInfo GetOrderInfo(String orderNo) {
		OrderInfo orderInfo = null;
		DB db = new DB();
		String sql = "select * from OrderInfo where orderNo='" + orderNo + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				orderInfo = new OrderInfo();
				orderInfo.setOrderNo(rs.getString("orderNo"));
				orderInfo.setMovieObj(rs.getInt("movieObj"));
				orderInfo.setMoviePrice(rs.getFloat("moviePrice"));
				orderInfo.setOrderNum(rs.getInt("orderNum"));
				orderInfo.setOrderPrice(rs.getFloat("orderPrice"));
				orderInfo.setUserObj(rs.getString("userObj"));
				orderInfo.setOrderTime(rs.getString("orderTime"));
				orderInfo.setReceiveName(rs.getString("receiveName"));
				orderInfo.setTelephone(rs.getString("telephone"));
				orderInfo.setAddress(rs.getString("address"));
				orderInfo.setOrderStateObj(rs.getInt("orderStateObj"));
				orderInfo.setOrderMemo(rs.getString("orderMemo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderInfo;
	}
	/* 更新订单 */
	public String UpdateOrderInfo(OrderInfo orderInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update OrderInfo set ";
			sql += "movieObj=" + orderInfo.getMovieObj() + ",";
			sql += "moviePrice=" + orderInfo.getMoviePrice() + ",";
			sql += "orderNum=" + orderInfo.getOrderNum() + ",";
			sql += "orderPrice=" + orderInfo.getOrderPrice() + ",";
			sql += "userObj='" + orderInfo.getUserObj() + "',";
			sql += "orderTime='" + orderInfo.getOrderTime() + "',";
			sql += "receiveName='" + orderInfo.getReceiveName() + "',";
			sql += "telephone='" + orderInfo.getTelephone() + "',";
			sql += "address='" + orderInfo.getAddress() + "',";
			sql += "orderStateObj=" + orderInfo.getOrderStateObj() + ",";
			sql += "orderMemo='" + orderInfo.getOrderMemo() + "'";
			sql += " where orderNo='" + orderInfo.getOrderNo() + "'";
			db.executeUpdate(sql);
			result = "订单更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "订单更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
