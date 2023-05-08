package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.OrderInfoDAO;
import com.mobileserver.domain.OrderInfo;

import org.json.JSONStringer;

public class OrderInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���충��ҵ������*/
	private OrderInfoDAO orderInfoDAO = new OrderInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public OrderInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ�����Ĳ�����Ϣ*/
			String orderNo = request.getParameter("orderNo");
			orderNo = orderNo == null ? "" : new String(request.getParameter(
					"orderNo").getBytes("iso-8859-1"), "UTF-8");
			int movieObj = 0;
			if (request.getParameter("movieObj") != null)
				movieObj = Integer.parseInt(request.getParameter("movieObj"));
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String orderTime = request.getParameter("orderTime");
			orderTime = orderTime == null ? "" : new String(request.getParameter(
					"orderTime").getBytes("iso-8859-1"), "UTF-8");
			String receiveName = request.getParameter("receiveName");
			receiveName = receiveName == null ? "" : new String(request.getParameter(
					"receiveName").getBytes("iso-8859-1"), "UTF-8");
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");
			int orderStateObj = 0;
			if (request.getParameter("orderStateObj") != null)
				orderStateObj = Integer.parseInt(request.getParameter("orderStateObj"));

			/*����ҵ���߼���ִ�ж�����ѯ*/
			List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfo(orderNo,movieObj,userObj,orderTime,receiveName,telephone,orderStateObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<OrderInfos>").append("\r\n");
			for (int i = 0; i < orderInfoList.size(); i++) {
				sb.append("	<OrderInfo>").append("\r\n")
				.append("		<orderNo>")
				.append(orderInfoList.get(i).getOrderNo())
				.append("</orderNo>").append("\r\n")
				.append("		<movieObj>")
				.append(orderInfoList.get(i).getMovieObj())
				.append("</movieObj>").append("\r\n")
				.append("		<moviePrice>")
				.append(orderInfoList.get(i).getMoviePrice())
				.append("</moviePrice>").append("\r\n")
				.append("		<orderNum>")
				.append(orderInfoList.get(i).getOrderNum())
				.append("</orderNum>").append("\r\n")
				.append("		<orderPrice>")
				.append(orderInfoList.get(i).getOrderPrice())
				.append("</orderPrice>").append("\r\n")
				.append("		<userObj>")
				.append(orderInfoList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<orderTime>")
				.append(orderInfoList.get(i).getOrderTime())
				.append("</orderTime>").append("\r\n")
				.append("		<receiveName>")
				.append(orderInfoList.get(i).getReceiveName())
				.append("</receiveName>").append("\r\n")
				.append("		<telephone>")
				.append(orderInfoList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<address>")
				.append(orderInfoList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("		<orderStateObj>")
				.append(orderInfoList.get(i).getOrderStateObj())
				.append("</orderStateObj>").append("\r\n")
				.append("		<orderMemo>")
				.append(orderInfoList.get(i).getOrderMemo())
				.append("</orderMemo>").append("\r\n")
				.append("	</OrderInfo>").append("\r\n");
			}
			sb.append("</OrderInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(OrderInfo orderInfo: orderInfoList) {
				  stringer.object();
			  stringer.key("orderNo").value(orderInfo.getOrderNo());
			  stringer.key("movieObj").value(orderInfo.getMovieObj());
			  stringer.key("moviePrice").value(orderInfo.getMoviePrice());
			  stringer.key("orderNum").value(orderInfo.getOrderNum());
			  stringer.key("orderPrice").value(orderInfo.getOrderPrice());
			  stringer.key("userObj").value(orderInfo.getUserObj());
			  stringer.key("orderTime").value(orderInfo.getOrderTime());
			  stringer.key("receiveName").value(orderInfo.getReceiveName());
			  stringer.key("telephone").value(orderInfo.getTelephone());
			  stringer.key("address").value(orderInfo.getAddress());
			  stringer.key("orderStateObj").value(orderInfo.getOrderStateObj());
			  stringer.key("orderMemo").value(orderInfo.getOrderMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��Ӷ�������ȡ�����������������浽�½��Ķ������� */ 
			OrderInfo orderInfo = new OrderInfo();
			String orderNo = new String(request.getParameter("orderNo").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderNo(orderNo);
			int movieObj = Integer.parseInt(request.getParameter("movieObj"));
			orderInfo.setMovieObj(movieObj);
			float moviePrice = Float.parseFloat(request.getParameter("moviePrice"));
			orderInfo.setMoviePrice(moviePrice);
			int orderNum = Integer.parseInt(request.getParameter("orderNum"));
			orderInfo.setOrderNum(orderNum);
			float orderPrice = Float.parseFloat(request.getParameter("orderPrice"));
			orderInfo.setOrderPrice(orderPrice);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setUserObj(userObj);
			String orderTime = new String(request.getParameter("orderTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderTime(orderTime);
			String receiveName = new String(request.getParameter("receiveName").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setReceiveName(receiveName);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setTelephone(telephone);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setAddress(address);
			int orderStateObj = Integer.parseInt(request.getParameter("orderStateObj"));
			orderInfo.setOrderStateObj(orderStateObj);
			String orderMemo = new String(request.getParameter("orderMemo").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderMemo(orderMemo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = orderInfoDAO.AddOrderInfo(orderInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����������ȡ�����Ķ������*/
			String orderNo = new String(request.getParameter("orderNo").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = orderInfoDAO.DeleteOrderInfo(orderNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¶���֮ǰ�ȸ���orderNo��ѯĳ������*/
			String orderNo = new String(request.getParameter("orderNo").getBytes("iso-8859-1"), "UTF-8");
			OrderInfo orderInfo = orderInfoDAO.GetOrderInfo(orderNo);

			// �ͻ��˲�ѯ�Ķ������󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("orderNo").value(orderInfo.getOrderNo());
			  stringer.key("movieObj").value(orderInfo.getMovieObj());
			  stringer.key("moviePrice").value(orderInfo.getMoviePrice());
			  stringer.key("orderNum").value(orderInfo.getOrderNum());
			  stringer.key("orderPrice").value(orderInfo.getOrderPrice());
			  stringer.key("userObj").value(orderInfo.getUserObj());
			  stringer.key("orderTime").value(orderInfo.getOrderTime());
			  stringer.key("receiveName").value(orderInfo.getReceiveName());
			  stringer.key("telephone").value(orderInfo.getTelephone());
			  stringer.key("address").value(orderInfo.getAddress());
			  stringer.key("orderStateObj").value(orderInfo.getOrderStateObj());
			  stringer.key("orderMemo").value(orderInfo.getOrderMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¶�������ȡ�����������������浽�½��Ķ������� */ 
			OrderInfo orderInfo = new OrderInfo();
			String orderNo = new String(request.getParameter("orderNo").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderNo(orderNo);
			int movieObj = Integer.parseInt(request.getParameter("movieObj"));
			orderInfo.setMovieObj(movieObj);
			float moviePrice = Float.parseFloat(request.getParameter("moviePrice"));
			orderInfo.setMoviePrice(moviePrice);
			int orderNum = Integer.parseInt(request.getParameter("orderNum"));
			orderInfo.setOrderNum(orderNum);
			float orderPrice = Float.parseFloat(request.getParameter("orderPrice"));
			orderInfo.setOrderPrice(orderPrice);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setUserObj(userObj);
			String orderTime = new String(request.getParameter("orderTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderTime(orderTime);
			String receiveName = new String(request.getParameter("receiveName").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setReceiveName(receiveName);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setTelephone(telephone);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setAddress(address);
			int orderStateObj = Integer.parseInt(request.getParameter("orderStateObj"));
			orderInfo.setOrderStateObj(orderStateObj);
			String orderMemo = new String(request.getParameter("orderMemo").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderMemo(orderMemo);

			/* ����ҵ���ִ�и��²��� */
			String result = orderInfoDAO.UpdateOrderInfo(orderInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
