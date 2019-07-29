package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.MovieDAO;
import com.mobileserver.domain.Movie;

import org.json.JSONStringer;

public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造电影业务层对象*/
	private MovieDAO movieDAO = new MovieDAO();

	/*默认构造函数*/
	public MovieServlet() {
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
			/*获取查询电影的参数信息*/
			int movieTypeObj = 0;
			if (request.getParameter("movieTypeObj") != null)
				movieTypeObj = Integer.parseInt(request.getParameter("movieTypeObj"));
			String movieName = request.getParameter("movieName");
			movieName = movieName == null ? "" : new String(request.getParameter(
					"movieName").getBytes("iso-8859-1"), "UTF-8");
			String director = request.getParameter("director");
			director = director == null ? "" : new String(request.getParameter(
					"director").getBytes("iso-8859-1"), "UTF-8");
			String mainPerformer = request.getParameter("mainPerformer");
			mainPerformer = mainPerformer == null ? "" : new String(request.getParameter(
					"mainPerformer").getBytes("iso-8859-1"), "UTF-8");
			int areaObj = 0;
			if (request.getParameter("areaObj") != null)
				areaObj = Integer.parseInt(request.getParameter("areaObj"));
			String playTime = request.getParameter("playTime");
			playTime = playTime == null ? "" : new String(request.getParameter(
					"playTime").getBytes("iso-8859-1"), "UTF-8");
			String recommendFlag = request.getParameter("recommendFlag");
			recommendFlag = recommendFlag == null ? "" : new String(request.getParameter(
					"recommendFlag").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行电影查询*/
			List<Movie> movieList = movieDAO.QueryMovie(movieTypeObj,movieName,director,mainPerformer,areaObj,playTime,recommendFlag);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Movies>").append("\r\n");
			for (int i = 0; i < movieList.size(); i++) {
				sb.append("	<Movie>").append("\r\n")
				.append("		<movieId>")
				.append(movieList.get(i).getMovieId())
				.append("</movieId>").append("\r\n")
				.append("		<movieTypeObj>")
				.append(movieList.get(i).getMovieTypeObj())
				.append("</movieTypeObj>").append("\r\n")
				.append("		<movieName>")
				.append(movieList.get(i).getMovieName())
				.append("</movieName>").append("\r\n")
				.append("		<moviePhoto>")
				.append(movieList.get(i).getMoviePhoto())
				.append("</moviePhoto>").append("\r\n")
				.append("		<director>")
				.append(movieList.get(i).getDirector())
				.append("</director>").append("\r\n")
				.append("		<mainPerformer>")
				.append(movieList.get(i).getMainPerformer())
				.append("</mainPerformer>").append("\r\n")
				.append("		<duration>")
				.append(movieList.get(i).getDuration())
				.append("</duration>").append("\r\n")
				.append("		<areaObj>")
				.append(movieList.get(i).getAreaObj())
				.append("</areaObj>").append("\r\n")
				.append("		<playTime>")
				.append(movieList.get(i).getPlayTime())
				.append("</playTime>").append("\r\n")
				.append("		<price>")
				.append(movieList.get(i).getPrice())
				.append("</price>").append("\r\n")
				.append("		<opera>")
				.append(movieList.get(i).getOpera())
				.append("</opera>").append("\r\n")
				.append("		<recommendFlag>")
				.append(movieList.get(i).getRecommendFlag())
				.append("</recommendFlag>").append("\r\n")
				.append("		<hitNum>")
				.append(movieList.get(i).getHitNum())
				.append("</hitNum>").append("\r\n")
				.append("	</Movie>").append("\r\n");
			}
			sb.append("</Movies>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Movie movie: movieList) {
				  stringer.object();
			  stringer.key("movieId").value(movie.getMovieId());
			  stringer.key("movieTypeObj").value(movie.getMovieTypeObj());
			  stringer.key("movieName").value(movie.getMovieName());
			  stringer.key("moviePhoto").value(movie.getMoviePhoto());
			  stringer.key("director").value(movie.getDirector());
			  stringer.key("mainPerformer").value(movie.getMainPerformer());
			  stringer.key("duration").value(movie.getDuration());
			  stringer.key("areaObj").value(movie.getAreaObj());
			  stringer.key("playTime").value(movie.getPlayTime());
			  stringer.key("price").value(movie.getPrice());
			  stringer.key("opera").value(movie.getOpera());
			  stringer.key("recommendFlag").value(movie.getRecommendFlag());
			  stringer.key("hitNum").value(movie.getHitNum());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加电影：获取电影参数，参数保存到新建的电影对象 */ 
			Movie movie = new Movie();
			int movieId = Integer.parseInt(request.getParameter("movieId"));
			movie.setMovieId(movieId);
			int movieTypeObj = Integer.parseInt(request.getParameter("movieTypeObj"));
			movie.setMovieTypeObj(movieTypeObj);
			String movieName = new String(request.getParameter("movieName").getBytes("iso-8859-1"), "UTF-8");
			movie.setMovieName(movieName);
			String moviePhoto = new String(request.getParameter("moviePhoto").getBytes("iso-8859-1"), "UTF-8");
			movie.setMoviePhoto(moviePhoto);
			String director = new String(request.getParameter("director").getBytes("iso-8859-1"), "UTF-8");
			movie.setDirector(director);
			String mainPerformer = new String(request.getParameter("mainPerformer").getBytes("iso-8859-1"), "UTF-8");
			movie.setMainPerformer(mainPerformer);
			String duration = new String(request.getParameter("duration").getBytes("iso-8859-1"), "UTF-8");
			movie.setDuration(duration);
			int areaObj = Integer.parseInt(request.getParameter("areaObj"));
			movie.setAreaObj(areaObj);
			String playTime = new String(request.getParameter("playTime").getBytes("iso-8859-1"), "UTF-8");
			movie.setPlayTime(playTime);
			float price = Float.parseFloat(request.getParameter("price"));
			movie.setPrice(price);
			String opera = new String(request.getParameter("opera").getBytes("iso-8859-1"), "UTF-8");
			movie.setOpera(opera);
			String recommendFlag = new String(request.getParameter("recommendFlag").getBytes("iso-8859-1"), "UTF-8");
			movie.setRecommendFlag(recommendFlag);
			int hitNum = Integer.parseInt(request.getParameter("hitNum"));
			movie.setHitNum(hitNum);

			/* 调用业务层执行添加操作 */
			String result = movieDAO.AddMovie(movie);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除电影：获取电影的电影id*/
			int movieId = Integer.parseInt(request.getParameter("movieId"));
			/*调用业务逻辑层执行删除操作*/
			String result = movieDAO.DeleteMovie(movieId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新电影之前先根据movieId查询某个电影*/
			int movieId = Integer.parseInt(request.getParameter("movieId"));
			Movie movie = movieDAO.GetMovie(movieId);

			// 客户端查询的电影对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("movieId").value(movie.getMovieId());
			  stringer.key("movieTypeObj").value(movie.getMovieTypeObj());
			  stringer.key("movieName").value(movie.getMovieName());
			  stringer.key("moviePhoto").value(movie.getMoviePhoto());
			  stringer.key("director").value(movie.getDirector());
			  stringer.key("mainPerformer").value(movie.getMainPerformer());
			  stringer.key("duration").value(movie.getDuration());
			  stringer.key("areaObj").value(movie.getAreaObj());
			  stringer.key("playTime").value(movie.getPlayTime());
			  stringer.key("price").value(movie.getPrice());
			  stringer.key("opera").value(movie.getOpera());
			  stringer.key("recommendFlag").value(movie.getRecommendFlag());
			  stringer.key("hitNum").value(movie.getHitNum());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新电影：获取电影参数，参数保存到新建的电影对象 */ 
			Movie movie = new Movie();
			int movieId = Integer.parseInt(request.getParameter("movieId"));
			movie.setMovieId(movieId);
			int movieTypeObj = Integer.parseInt(request.getParameter("movieTypeObj"));
			movie.setMovieTypeObj(movieTypeObj);
			String movieName = new String(request.getParameter("movieName").getBytes("iso-8859-1"), "UTF-8");
			movie.setMovieName(movieName);
			String moviePhoto = new String(request.getParameter("moviePhoto").getBytes("iso-8859-1"), "UTF-8");
			movie.setMoviePhoto(moviePhoto);
			String director = new String(request.getParameter("director").getBytes("iso-8859-1"), "UTF-8");
			movie.setDirector(director);
			String mainPerformer = new String(request.getParameter("mainPerformer").getBytes("iso-8859-1"), "UTF-8");
			movie.setMainPerformer(mainPerformer);
			String duration = new String(request.getParameter("duration").getBytes("iso-8859-1"), "UTF-8");
			movie.setDuration(duration);
			int areaObj = Integer.parseInt(request.getParameter("areaObj"));
			movie.setAreaObj(areaObj);
			String playTime = new String(request.getParameter("playTime").getBytes("iso-8859-1"), "UTF-8");
			movie.setPlayTime(playTime);
			float price = Float.parseFloat(request.getParameter("price"));
			movie.setPrice(price);
			String opera = new String(request.getParameter("opera").getBytes("iso-8859-1"), "UTF-8");
			movie.setOpera(opera);
			String recommendFlag = new String(request.getParameter("recommendFlag").getBytes("iso-8859-1"), "UTF-8");
			movie.setRecommendFlag(recommendFlag);
			int hitNum = Integer.parseInt(request.getParameter("hitNum"));
			movie.setHitNum(hitNum);

			/* 调用业务层执行更新操作 */
			String result = movieDAO.UpdateMovie(movie);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
