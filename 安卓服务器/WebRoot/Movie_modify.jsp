<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Movie" %>
<%@ page import="com.chengxusheji.domain.MovieType" %>
<%@ page import="com.chengxusheji.domain.Area" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�MovieType��Ϣ
    List<MovieType> movieTypeList = (List<MovieType>)request.getAttribute("movieTypeList");
    //��ȡ���е�Area��Ϣ
    List<Area> areaList = (List<Area>)request.getAttribute("areaList");
    Movie movie = (Movie)request.getAttribute("movie");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸ĵ�Ӱ</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*��֤��*/
function checkForm() {
    var movieName = document.getElementById("movie.movieName").value;
    if(movieName=="") {
        alert('������ӰƬ����!');
        return false;
    }
    var director = document.getElementById("movie.director").value;
    if(director=="") {
        alert('�����뵼��!');
        return false;
    }
    var mainPerformer = document.getElementById("movie.mainPerformer").value;
    if(mainPerformer=="") {
        alert('����������!');
        return false;
    }
    var duration = document.getElementById("movie.duration").value;
    if(duration=="") {
        alert('������ʱ��!');
        return false;
    }
    var playTime = document.getElementById("movie.playTime").value;
    if(playTime=="") {
        alert('�������ӳʱ��!');
        return false;
    }
    var opera = document.getElementById("movie.opera").value;
    if(opera=="") {
        alert('�����������!');
        return false;
    }
    var recommendFlag = document.getElementById("movie.recommendFlag").value;
    if(recommendFlag=="") {
        alert('�������Ƿ��Ƽ�!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="Movie/Movie_ModifyMovie.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��Ӱid:</td>
    <td width=70%><input id="movie.movieId" name="movie.movieId" type="text" value="<%=movie.getMovieId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>ӰƬ����:</td>
    <td width=70%>
      <select name="movie.movieTypeObj.typeId">
      <%
        for(MovieType movieType:movieTypeList) {
          String selected = "";
          if(movieType.getTypeId() == movie.getMovieTypeObj().getTypeId())
            selected = "selected";
      %>
          <option value='<%=movieType.getTypeId() %>' <%=selected %>><%=movieType.getTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>ӰƬ����:</td>
    <td width=70%><input id="movie.movieName" name="movie.movieName" type="text" size="40" value='<%=movie.getMovieName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>ӰƬͼƬ:</td>
    <td width=70%><img src="<%=basePath %><%=movie.getMoviePhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="movie.moviePhoto" value="<%=movie.getMoviePhoto() %>" />
    <input id="moviePhotoFile" name="moviePhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="movie.director" name="movie.director" type="text" size="20" value='<%=movie.getDirector() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="movie.mainPerformer" name="movie.mainPerformer" type="text" size="40" value='<%=movie.getMainPerformer() %>'/></td>
  </tr>

  <tr>
    <td width=30%>ʱ��:</td>
    <td width=70%><input id="movie.duration" name="movie.duration" type="text" size="20" value='<%=movie.getDuration() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%>
      <select name="movie.areaObj.areaId">
      <%
        for(Area area:areaList) {
          String selected = "";
          if(area.getAreaId() == movie.getAreaObj().getAreaId())
            selected = "selected";
      %>
          <option value='<%=area.getAreaId() %>' <%=selected %>><%=area.getAreaName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ӳʱ��:</td>
    <td width=70%><input id="movie.playTime" name="movie.playTime" type="text" size="30" value='<%=movie.getPlayTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>Ʊ��:</td>
    <td width=70%><input id="movie.price" name="movie.price" type="text" size="8" value='<%=movie.getPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><textarea id="movie.opera" name="movie.opera" rows=5 cols=50><%=movie.getOpera() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>�Ƿ��Ƽ�:</td>
    <td width=70%><input id="movie.recommendFlag" name="movie.recommendFlag" type="text" size="20" value='<%=movie.getRecommendFlag() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�����:</td>
    <td width=70%><input id="movie.hitNum" name="movie.hitNum" type="text" size="8" value='<%=movie.getHitNum() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
