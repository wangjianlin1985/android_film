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
import com.chengxusheji.dao.MovieTypeDAO;
import com.chengxusheji.domain.MovieType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class MovieTypeAction extends BaseAction {

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

    private int typeId;
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public int getTypeId() {
        return typeId;
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

    /*��������MovieType����*/
    private MovieType movieType;
    public void setMovieType(MovieType movieType) {
        this.movieType = movieType;
    }
    public MovieType getMovieType() {
        return this.movieType;
    }

    /*��ת�����MovieType��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���MovieType��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddMovieType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            movieTypeDAO.AddMovieType(movieType);
            ctx.put("message",  java.net.URLEncoder.encode("MovieType��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MovieType���ʧ��!"));
            return "error";
        }
    }

    /*��ѯMovieType��Ϣ*/
    public String QueryMovieType() {
        if(currentPage == 0) currentPage = 1;
        List<MovieType> movieTypeList = movieTypeDAO.QueryMovieTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        movieTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = movieTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = movieTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("movieTypeList",  movieTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryMovieTypeOutputToExcel() { 
        List<MovieType> movieTypeList = movieTypeDAO.QueryMovieTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "MovieType��Ϣ��¼"; 
        String[] headers = { "����id","��������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<movieTypeList.size();i++) {
        	MovieType movieType = movieTypeList.get(i); 
        	dataset.add(new String[]{movieType.getTypeId() + "",movieType.getTypeName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"MovieType.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯMovieType��Ϣ*/
    public String FrontQueryMovieType() {
        if(currentPage == 0) currentPage = 1;
        List<MovieType> movieTypeList = movieTypeDAO.QueryMovieTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        movieTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = movieTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = movieTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("movieTypeList",  movieTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�MovieType��Ϣ*/
    public String ModifyMovieTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������typeId��ȡMovieType����*/
        MovieType movieType = movieTypeDAO.GetMovieTypeByTypeId(typeId);

        ctx.put("movieType",  movieType);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�MovieType��Ϣ*/
    public String FrontShowMovieTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������typeId��ȡMovieType����*/
        MovieType movieType = movieTypeDAO.GetMovieTypeByTypeId(typeId);

        ctx.put("movieType",  movieType);
        return "front_show_view";
    }

    /*�����޸�MovieType��Ϣ*/
    public String ModifyMovieType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            movieTypeDAO.UpdateMovieType(movieType);
            ctx.put("message",  java.net.URLEncoder.encode("MovieType��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MovieType��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��MovieType��Ϣ*/
    public String DeleteMovieType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            movieTypeDAO.DeleteMovieType(typeId);
            ctx.put("message",  java.net.URLEncoder.encode("MovieTypeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MovieTypeɾ��ʧ��!"));
            return "error";
        }
    }

}
