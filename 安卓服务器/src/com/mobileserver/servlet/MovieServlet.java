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

	/*�����Ӱҵ������*/
	private MovieDAO movieDAO = new MovieDAO();

	/*Ĭ�Ϲ��캯��*/
	public MovieServlet() {
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
			/*��ȡ��ѯ��Ӱ�Ĳ�����Ϣ*/
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

			/*����ҵ���߼���ִ�е�Ӱ��ѯ*/
			List<Movie> movieList = movieDAO.QueryMovie(movieTypeObj,movieName,director,mainPerformer,areaObj,playTime,recommendFlag);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӵ�Ӱ����ȡ��Ӱ�������������浽�½��ĵ�Ӱ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = movieDAO.AddMovie(movie);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����Ӱ����ȡ��Ӱ�ĵ�Ӱid*/
			int movieId = Integer.parseInt(request.getParameter("movieId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = movieDAO.DeleteMovie(movieId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���µ�Ӱ֮ǰ�ȸ���movieId��ѯĳ����Ӱ*/
			int movieId = Integer.parseInt(request.getParameter("movieId"));
			Movie movie = movieDAO.GetMovie(movieId);

			// �ͻ��˲�ѯ�ĵ�Ӱ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���µ�Ӱ����ȡ��Ӱ�������������浽�½��ĵ�Ӱ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = movieDAO.UpdateMovie(movie);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
