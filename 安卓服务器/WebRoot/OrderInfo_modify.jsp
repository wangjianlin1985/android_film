<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.OrderInfo" %>
<%@ page import="com.chengxusheji.domain.Movie" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.OrderState" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Movie信息
    List<Movie> movieList = (List<Movie>)request.getAttribute("movieList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //获取所有的OrderState信息
    List<OrderState> orderStateList = (List<OrderState>)request.getAttribute("orderStateList");
    OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改订单</TITLE>
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
/*验证表单*/
function checkForm() {
    var orderNo = document.getElementById("orderInfo.orderNo").value;
    if(orderNo=="") {
        alert('请输入订单编号!');
        return false;
    }
    var receiveName = document.getElementById("orderInfo.receiveName").value;
    if(receiveName=="") {
        alert('请输入收货人!');
        return false;
    }
    var telephone = document.getElementById("orderInfo.telephone").value;
    if(telephone=="") {
        alert('请输入收货人电话!');
        return false;
    }
    var address = document.getElementById("orderInfo.address").value;
    if(address=="") {
        alert('请输入收货人地址!');
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
    <TD align="left" vAlign=top ><s:form action="OrderInfo/OrderInfo_ModifyOrderInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>订单编号:</td>
    <td width=70%><input id="orderInfo.orderNo" name="orderInfo.orderNo" type="text" value="<%=orderInfo.getOrderNo() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>下单电影:</td>
    <td width=70%>
      <select name="orderInfo.movieObj.movieId">
      <%
        for(Movie movie:movieList) {
          String selected = "";
          if(movie.getMovieId() == orderInfo.getMovieObj().getMovieId())
            selected = "selected";
      %>
          <option value='<%=movie.getMovieId() %>' <%=selected %>><%=movie.getMovieName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>电影价格:</td>
    <td width=70%><input id="orderInfo.moviePrice" name="orderInfo.moviePrice" type="text" size="8" value='<%=orderInfo.getMoviePrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>购买数量:</td>
    <td width=70%><input id="orderInfo.orderNum" name="orderInfo.orderNum" type="text" size="8" value='<%=orderInfo.getOrderNum() %>'/></td>
  </tr>

  <tr>
    <td width=30%>订单总价:</td>
    <td width=70%><input id="orderInfo.orderPrice" name="orderInfo.orderPrice" type="text" size="8" value='<%=orderInfo.getOrderPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>下单用户:</td>
    <td width=70%>
      <select name="orderInfo.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(orderInfo.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>下单时间:</td>
    <td width=70%><input id="orderInfo.orderTime" name="orderInfo.orderTime" type="text" size="20" value='<%=orderInfo.getOrderTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>收货人:</td>
    <td width=70%><input id="orderInfo.receiveName" name="orderInfo.receiveName" type="text" size="20" value='<%=orderInfo.getReceiveName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>收货人电话:</td>
    <td width=70%><input id="orderInfo.telephone" name="orderInfo.telephone" type="text" size="20" value='<%=orderInfo.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>收货人地址:</td>
    <td width=70%><input id="orderInfo.address" name="orderInfo.address" type="text" size="80" value='<%=orderInfo.getAddress() %>'/></td>
  </tr>

  <tr>
    <td width=30%>订单状态:</td>
    <td width=70%>
      <select name="orderInfo.orderStateObj.orderStateId">
      <%
        for(OrderState orderState:orderStateList) {
          String selected = "";
          if(orderState.getOrderStateId() == orderInfo.getOrderStateObj().getOrderStateId())
            selected = "selected";
      %>
          <option value='<%=orderState.getOrderStateId() %>' <%=selected %>><%=orderState.getOrderStateName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>订单备注:</td>
    <td width=70%><textarea id="orderInfo.orderMemo" name="orderInfo.orderMemo" rows=5 cols=50><%=orderInfo.getOrderMemo() %></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
