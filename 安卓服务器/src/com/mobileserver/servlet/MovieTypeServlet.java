package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.MovieTypeDAO;
import com.mobileserver.domain.MovieType;

import org.json.JSONStringer;

public class MovieTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����Ӱ����ҵ������*/
	private MovieTypeDAO movieTypeDAO = new MovieTypeDAO();

	/*Ĭ�Ϲ��캯��*/
	public MovieTypeServlet() {
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
			/*��ȡ��ѯ��Ӱ���͵Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ�е�Ӱ���Ͳ�ѯ*/
			List<MovieType> movieTypeList = movieTypeDAO.QueryMovieType();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<MovieTypes>").append("\r\n");
			for (int i = 0; i < movieTypeList.size(); i++) {
				sb.append("	<MovieType>").append("\r\n")
				.append("		<typeId>")
				.append(movieTypeList.get(i).getTypeId())
				.append("</typeId>").append("\r\n")
				.append("		<typeName>")
				.append(movieTypeList.get(i).getTypeName())
				.append("</typeName>").append("\r\n")
				.append("	</MovieType>").append("\r\n");
			}
			sb.append("</MovieTypes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(MovieType movieType: movieTypeList) {
				  stringer.object();
			  stringer.key("typeId").value(movieType.getTypeId());
			  stringer.key("typeName").value(movieType.getTypeName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӵ�Ӱ���ͣ���ȡ��Ӱ���Ͳ������������浽�½��ĵ�Ӱ���Ͷ��� */ 
			MovieType movieType = new MovieType();
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			movieType.setTypeId(typeId);
			String typeName = new String(request.getParameter("typeName").getBytes("iso-8859-1"), "UTF-8");
			movieType.setTypeName(typeName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = movieTypeDAO.AddMovieType(movieType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����Ӱ���ͣ���ȡ��Ӱ���͵�����id*/
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = movieTypeDAO.DeleteMovieType(typeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���µ�Ӱ����֮ǰ�ȸ���typeId��ѯĳ����Ӱ����*/
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			MovieType movieType = movieTypeDAO.GetMovieType(typeId);

			// �ͻ��˲�ѯ�ĵ�Ӱ���Ͷ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("typeId").value(movieType.getTypeId());
			  stringer.key("typeName").value(movieType.getTypeName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���µ�Ӱ���ͣ���ȡ��Ӱ���Ͳ������������浽�½��ĵ�Ӱ���Ͷ��� */ 
			MovieType movieType = new MovieType();
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			movieType.setTypeId(typeId);
			String typeName = new String(request.getParameter("typeName").getBytes("iso-8859-1"), "UTF-8");
			movieType.setTypeName(typeName);

			/* ����ҵ���ִ�и��²��� */
			String result = movieTypeDAO.UpdateMovieType(movieType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
