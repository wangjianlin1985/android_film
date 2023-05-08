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

	/*�������ҵ������*/
	private AreaDAO areaDAO = new AreaDAO();

	/*Ĭ�Ϲ��캯��*/
	public AreaServlet() {
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

			/*����ҵ���߼���ִ�е�����ѯ*/
			List<Area> areaList = areaDAO.QueryArea();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӵ�������ȡ�����������������浽�½��ĵ������� */ 
			Area area = new Area();
			int areaId = Integer.parseInt(request.getParameter("areaId"));
			area.setAreaId(areaId);
			String areaName = new String(request.getParameter("areaName").getBytes("iso-8859-1"), "UTF-8");
			area.setAreaName(areaName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = areaDAO.AddArea(area);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����������ȡ�����ĵ���id*/
			int areaId = Integer.parseInt(request.getParameter("areaId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = areaDAO.DeleteArea(areaId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���µ���֮ǰ�ȸ���areaId��ѯĳ������*/
			int areaId = Integer.parseInt(request.getParameter("areaId"));
			Area area = areaDAO.GetArea(areaId);

			// �ͻ��˲�ѯ�ĵ������󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���µ�������ȡ�����������������浽�½��ĵ������� */ 
			Area area = new Area();
			int areaId = Integer.parseInt(request.getParameter("areaId"));
			area.setAreaId(areaId);
			String areaName = new String(request.getParameter("areaName").getBytes("iso-8859-1"), "UTF-8");
			area.setAreaName(areaName);

			/* ����ҵ���ִ�и��²��� */
			String result = areaDAO.UpdateArea(area);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
