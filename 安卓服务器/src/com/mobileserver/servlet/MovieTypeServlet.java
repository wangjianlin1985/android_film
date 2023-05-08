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

	/*构造电影类型业务层对象*/
	private MovieTypeDAO movieTypeDAO = new MovieTypeDAO();

	/*默认构造函数*/
	public MovieTypeServlet() {
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
			/*获取查询电影类型的参数信息*/

			/*调用业务逻辑层执行电影类型查询*/
			List<MovieType> movieTypeList = movieTypeDAO.QueryMovieType();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加电影类型：获取电影类型参数，参数保存到新建的电影类型对象 */ 
			MovieType movieType = new MovieType();
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			movieType.setTypeId(typeId);
			String typeName = new String(request.getParameter("typeName").getBytes("iso-8859-1"), "UTF-8");
			movieType.setTypeName(typeName);

			/* 调用业务层执行添加操作 */
			String result = movieTypeDAO.AddMovieType(movieType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除电影类型：获取电影类型的类型id*/
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = movieTypeDAO.DeleteMovieType(typeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新电影类型之前先根据typeId查询某个电影类型*/
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			MovieType movieType = movieTypeDAO.GetMovieType(typeId);

			// 客户端查询的电影类型对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新电影类型：获取电影类型参数，参数保存到新建的电影类型对象 */ 
			MovieType movieType = new MovieType();
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			movieType.setTypeId(typeId);
			String typeName = new String(request.getParameter("typeName").getBytes("iso-8859-1"), "UTF-8");
			movieType.setTypeName(typeName);

			/* 调用业务层执行更新操作 */
			String result = movieTypeDAO.UpdateMovieType(movieType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
