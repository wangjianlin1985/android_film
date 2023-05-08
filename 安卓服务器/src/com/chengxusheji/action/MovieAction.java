package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.MovieDAO;
import com.chengxusheji.domain.Movie;
import com.chengxusheji.dao.MovieTypeDAO;
import com.chengxusheji.domain.MovieType;
import com.chengxusheji.dao.AreaDAO;
import com.chengxusheji.domain.Area;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class MovieAction extends BaseAction {

	/*ͼƬ���ļ��ֶ�moviePhoto��������*/
	private File moviePhotoFile;
	private String moviePhotoFileFileName;
	private String moviePhotoFileContentType;
	public File getMoviePhotoFile() {
		return moviePhotoFile;
	}
	public void setMoviePhotoFile(File moviePhotoFile) {
		this.moviePhotoFile = moviePhotoFile;
	}
	public String getMoviePhotoFileFileName() {
		return moviePhotoFileFileName;
	}
	public void setMoviePhotoFileFileName(String moviePhotoFileFileName) {
		this.moviePhotoFileFileName = moviePhotoFileFileName;
	}
	public String getMoviePhotoFileContentType() {
		return moviePhotoFileContentType;
	}
	public void setMoviePhotoFileContentType(String moviePhotoFileContentType) {
		this.moviePhotoFileContentType = moviePhotoFileContentType;
	}
    /*�������Ҫ��ѯ������: ӰƬ����*/
    private MovieType movieTypeObj;
    public void setMovieTypeObj(MovieType movieTypeObj) {
        this.movieTypeObj = movieTypeObj;
    }
    public MovieType getMovieTypeObj() {
        return this.movieTypeObj;
    }

    /*�������Ҫ��ѯ������: ӰƬ����*/
    private String movieName;
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getMovieName() {
        return this.movieName;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String director;
    public void setDirector(String director) {
        this.director = director;
    }
    public String getDirector() {
        return this.director;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String mainPerformer;
    public void setMainPerformer(String mainPerformer) {
        this.mainPerformer = mainPerformer;
    }
    public String getMainPerformer() {
        return this.mainPerformer;
    }

    /*�������Ҫ��ѯ������: ����*/
    private Area areaObj;
    public void setAreaObj(Area areaObj) {
        this.areaObj = areaObj;
    }
    public Area getAreaObj() {
        return this.areaObj;
    }

    /*�������Ҫ��ѯ������: ��ӳʱ��*/
    private String playTime;
    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }
    public String getPlayTime() {
        return this.playTime;
    }

    /*�������Ҫ��ѯ������: �Ƿ��Ƽ�*/
    private String recommendFlag;
    public void setRecommendFlag(String recommendFlag) {
        this.recommendFlag = recommendFlag;
    }
    public String getRecommendFlag() {
        return this.recommendFlag;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int movieId;
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public int getMovieId() {
        return movieId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource MovieTypeDAO movieTypeDAO;
    @Resource AreaDAO areaDAO;
    @Resource MovieDAO movieDAO;

    /*��������Movie����*/
    private Movie movie;
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public Movie getMovie() {
        return this.movie;
    }

    /*��ת�����Movie��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�MovieType��Ϣ*/
        List<MovieType> movieTypeList = movieTypeDAO.QueryAllMovieTypeInfo();
        ctx.put("movieTypeList", movieTypeList);
        /*��ѯ���е�Area��Ϣ*/
        List<Area> areaList = areaDAO.QueryAllAreaInfo();
        ctx.put("areaList", areaList);
        return "add_view";
    }

    /*���Movie��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddMovie() {
        ActionContext ctx = ActionContext.getContext();
        try {
            MovieType movieTypeObj = movieTypeDAO.GetMovieTypeByTypeId(movie.getMovieTypeObj().getTypeId());
            movie.setMovieTypeObj(movieTypeObj);
            Area areaObj = areaDAO.GetAreaByAreaId(movie.getAreaObj().getAreaId());
            movie.setAreaObj(areaObj);
            /*����ӰƬͼƬ�ϴ�*/
            String moviePhotoPath = "upload/noimage.jpg"; 
       	 	if(moviePhotoFile != null)
       	 		moviePhotoPath = photoUpload(moviePhotoFile,moviePhotoFileContentType);
       	 	movie.setMoviePhoto(moviePhotoPath);
            movieDAO.AddMovie(movie);
            ctx.put("message",  java.net.URLEncoder.encode("Movie��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Movie���ʧ��!"));
            return "error";
        }
    }

    /*��ѯMovie��Ϣ*/
    public String QueryMovie() {
        if(currentPage == 0) currentPage = 1;
        if(movieName == null) movieName = "";
        if(director == null) director = "";
        if(mainPerformer == null) mainPerformer = "";
        if(playTime == null) playTime = "";
        if(recommendFlag == null) recommendFlag = "";
        List<Movie> movieList = movieDAO.QueryMovieInfo(movieTypeObj, movieName, director, mainPerformer, areaObj, playTime, recommendFlag, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        movieDAO.CalculateTotalPageAndRecordNumber(movieTypeObj, movieName, director, mainPerformer, areaObj, playTime, recommendFlag);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = movieDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = movieDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("movieList",  movieList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("movieTypeObj", movieTypeObj);
        List<MovieType> movieTypeList = movieTypeDAO.QueryAllMovieTypeInfo();
        ctx.put("movieTypeList", movieTypeList);
        ctx.put("movieName", movieName);
        ctx.put("director", director);
        ctx.put("mainPerformer", mainPerformer);
        ctx.put("areaObj", areaObj);
        List<Area> areaList = areaDAO.QueryAllAreaInfo();
        ctx.put("areaList", areaList);
        ctx.put("playTime", playTime);
        ctx.put("recommendFlag", recommendFlag);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryMovieOutputToExcel() { 
        if(movieName == null) movieName = "";
        if(director == null) director = "";
        if(mainPerformer == null) mainPerformer = "";
        if(playTime == null) playTime = "";
        if(recommendFlag == null) recommendFlag = "";
        List<Movie> movieList = movieDAO.QueryMovieInfo(movieTypeObj,movieName,director,mainPerformer,areaObj,playTime,recommendFlag);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Movie��Ϣ��¼"; 
        String[] headers = { "��Ӱid","ӰƬ����","ӰƬ����","ӰƬͼƬ","����","����","ʱ��","����","��ӳʱ��","Ʊ��","�Ƿ��Ƽ�","�����"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<movieList.size();i++) {
        	Movie movie = movieList.get(i); 
        	dataset.add(new String[]{movie.getMovieId() + "",movie.getMovieTypeObj().getTypeName(),
movie.getMovieName(),movie.getMoviePhoto(),movie.getDirector(),movie.getMainPerformer(),movie.getDuration(),movie.getAreaObj().getAreaName(),
movie.getPlayTime(),movie.getPrice() + "",movie.getRecommendFlag(),movie.getHitNum() + ""});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Movie.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯMovie��Ϣ*/
    public String FrontQueryMovie() {
        if(currentPage == 0) currentPage = 1;
        if(movieName == null) movieName = "";
        if(director == null) director = "";
        if(mainPerformer == null) mainPerformer = "";
        if(playTime == null) playTime = "";
        if(recommendFlag == null) recommendFlag = "";
        List<Movie> movieList = movieDAO.QueryMovieInfo(movieTypeObj, movieName, director, mainPerformer, areaObj, playTime, recommendFlag, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        movieDAO.CalculateTotalPageAndRecordNumber(movieTypeObj, movieName, director, mainPerformer, areaObj, playTime, recommendFlag);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = movieDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = movieDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("movieList",  movieList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("movieTypeObj", movieTypeObj);
        List<MovieType> movieTypeList = movieTypeDAO.QueryAllMovieTypeInfo();
        ctx.put("movieTypeList", movieTypeList);
        ctx.put("movieName", movieName);
        ctx.put("director", director);
        ctx.put("mainPerformer", mainPerformer);
        ctx.put("areaObj", areaObj);
        List<Area> areaList = areaDAO.QueryAllAreaInfo();
        ctx.put("areaList", areaList);
        ctx.put("playTime", playTime);
        ctx.put("recommendFlag", recommendFlag);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Movie��Ϣ*/
    public String ModifyMovieQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������movieId��ȡMovie����*/
        Movie movie = movieDAO.GetMovieByMovieId(movieId);

        List<MovieType> movieTypeList = movieTypeDAO.QueryAllMovieTypeInfo();
        ctx.put("movieTypeList", movieTypeList);
        List<Area> areaList = areaDAO.QueryAllAreaInfo();
        ctx.put("areaList", areaList);
        ctx.put("movie",  movie);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Movie��Ϣ*/
    public String FrontShowMovieQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������movieId��ȡMovie����*/
        Movie movie = movieDAO.GetMovieByMovieId(movieId);

        List<MovieType> movieTypeList = movieTypeDAO.QueryAllMovieTypeInfo();
        ctx.put("movieTypeList", movieTypeList);
        List<Area> areaList = areaDAO.QueryAllAreaInfo();
        ctx.put("areaList", areaList);
        ctx.put("movie",  movie);
        return "front_show_view";
    }

    /*�����޸�Movie��Ϣ*/
    public String ModifyMovie() {
        ActionContext ctx = ActionContext.getContext();
        try {
            MovieType movieTypeObj = movieTypeDAO.GetMovieTypeByTypeId(movie.getMovieTypeObj().getTypeId());
            movie.setMovieTypeObj(movieTypeObj);
            Area areaObj = areaDAO.GetAreaByAreaId(movie.getAreaObj().getAreaId());
            movie.setAreaObj(areaObj);
            /*����ӰƬͼƬ�ϴ�*/
            if(moviePhotoFile != null) {
            	String moviePhotoPath = photoUpload(moviePhotoFile,moviePhotoFileContentType);
            	movie.setMoviePhoto(moviePhotoPath);
            }
            movieDAO.UpdateMovie(movie);
            ctx.put("message",  java.net.URLEncoder.encode("Movie��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Movie��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Movie��Ϣ*/
    public String DeleteMovie() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            movieDAO.DeleteMovie(movieId);
            ctx.put("message",  java.net.URLEncoder.encode("Movieɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Movieɾ��ʧ��!"));
            return "error";
        }
    }

}
