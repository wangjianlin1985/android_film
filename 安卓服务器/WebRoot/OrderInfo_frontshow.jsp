<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.OrderInfo" %>
<%@ page import="com.chengxusheji.domain.Movie" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.OrderState" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Movie��Ϣ
    List<Movie> movieList = (List<Movie>)request.getAttribute("movieList");
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //��ȡ���е�OrderState��Ϣ
    List<OrderState> orderStateList = (List<OrderState>)request.getAttribute("orderStateList");
    OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

%>
<HTML><HEAD><TITLE>�鿴����</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>�������:</td>
    <td width=70%><%=orderInfo.getOrderNo() %></td>
  </tr>

  <tr>
    <td width=30%>�µ���Ӱ:</td>
    <td width=70%>
      <%=orderInfo.getMovieObj().getMovieName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��Ӱ�۸�:</td>
    <td width=70%><%=orderInfo.getMoviePrice() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><%=orderInfo.getOrderNum() %></td>
  </tr>

  <tr>
    <td width=30%>�����ܼ�:</td>
    <td width=70%><%=orderInfo.getOrderPrice() %></td>
  </tr>

  <tr>
    <td width=30%>�µ��û�:</td>
    <td width=70%>
      <%=orderInfo.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�µ�ʱ��:</td>
    <td width=70%><%=orderInfo.getOrderTime() %></td>
  </tr>

  <tr>
    <td width=30%>�ջ���:</td>
    <td width=70%><%=orderInfo.getReceiveName() %></td>
  </tr>

  <tr>
    <td width=30%>�ջ��˵绰:</td>
    <td width=70%><%=orderInfo.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>�ջ��˵�ַ:</td>
    <td width=70%><%=orderInfo.getAddress() %></td>
  </tr>

  <tr>
    <td width=30%>����״̬:</td>
    <td width=70%>
      <%=orderInfo.getOrderStateObj().getOrderStateName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>������ע:</td>
    <td width=70%><%=orderInfo.getOrderMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
