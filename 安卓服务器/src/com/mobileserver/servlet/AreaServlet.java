package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.AreaDAO;
import com.mobileserver.domain.Area;

import org.json.JSONStringer;

public class AreaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造地区业务层对象*/
	private AreaDAO areaDAO = new AreaDAO();

	/*默认构造函数*/
	public AreaServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询地区的参数信息*/

			/*调用业务逻辑层执行地区查询*/
			List<Area> areaList = areaDAO.QueryArea();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Areas>").append("\r\n");
			for (int i = 0; i < areaList.size(); i++) {
				sb.append("	<Area>").append("\r\n")
				.append("		<areaId>")
				.append(areaList.get(i).getAreaId())
				.append("</areaId>").append("\r\n")
				.append("		<areaName>")
				.append(areaList.get(i).getAreaName())
				.append("</areaName>").append("\r\n")
				.append("	</Area>").append("\r\n");
			}
			sb.append("</Areas>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Area area: areaList) {
				  stringer.object();
			  stringer.key("areaId").value(area.getAreaId());
			  stringer.key("areaName").value(area.getAreaName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加地区：获取地区参数，参数保存到新建的地区对象 */ 
			Area area = new Area();
			int areaId = Integer.parseInt(request.getParameter("areaId"));
			area.setAreaId(areaId);
			String areaName = new String(request.getParameter("areaName").getBytes("iso-8859-1"), "UTF-8");
			area.setAreaName(areaName);

			/* 调用业务层执行添加操作 */
			String result = areaDAO.AddArea(area);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除地区：获取地区的地区id*/
			int areaId = Integer.parseInt(request.getParameter("areaId"));
			/*调用业务逻辑层执行删除操作*/
			String result = areaDAO.DeleteArea(areaId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新地区之前先根据areaId查询某个地区*/
			int areaId = Integer.parseInt(request.getParameter("areaId"));
			Area area = areaDAO.GetArea(areaId);

			// 客户端查询的地区对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("areaId").value(area.getAreaId());
			  stringer.key("areaName").value(area.getAreaName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新地区：获取地区参数，参数保存到新建的地区对象 */ 
			Area area = new Area();
			int areaId = Integer.parseInt(request.getParameter("areaId"));
			area.setAreaId(areaId);
			String areaName = new String(request.getParameter("areaName").getBytes("iso-8859-1"), "UTF-8");
			area.setAreaName(areaName);

			/* 调用业务层执行更新操作 */
			String result = areaDAO.UpdateArea(area);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
