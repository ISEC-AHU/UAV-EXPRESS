<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@include file="../Main/top.jsp"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/button.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all" />
<title>配送站管理员页面</title>

<style type="text/css">
html {
	overflow-y:scroll;
	vertical-align:baseline;
}
body {
	font-family:Microsoft YaHei, Segoe UI, Tahoma, Arial, Verdana, sans-serif, "新宋体";
	font-size:12px;
	color:#000000;
	height:100%;
	line-height:1;
	background:#999;
}
.screenbg a img {
	vertical-align:middle;
	display:inline;
	border:none;
	display:block;
	list-style:none;
	position:fixed;
	overflow:hidden;
	top:0;
	left:0;
	width:100%;
	height:100%;
	z-index:-1000;
	float:right;
}

#tab .tab_box {
   height:100%;
	width:630px;
	clear:both;
	top:0px;
	left:450px;
	position:relative;
	background-color:#FFFFFF;
	color:#000000;
	font-size:16px;
    filter:alpha(opacity:80);
	opacity:0.8;
}

.tab_box div {
	padding:10px;
}
</style>
</head>
<body>
<div id="tab">
<div class="tab_box">

<div class="SystemName">
	    <h1 class="hsl">基于区块链的无人机配送系统</h1>
	</div>

<div>
<a href="../deliverystation/add.do"><input type="button" class="t_add" value="UAV register" ></a>
<a href="../deliverystation/orderUAVSchedule.do"><input type="button" class="t_add" value="Order Allocation" ></a>
</div>
<div>

<p>注册失败的无人机</p>
<c:if test="${!empty requestScope.deniedUAVs}">
        <table border=1 cellpadding="10" cellspacing="0" class="a_list" width="600px">
        <tr >
       
        <th >
                                    识别号
        </th>
        <th >
                                    制造商
        </th>
         <th >
                                  所属公司            
        </th>
        <th >
                                    注册人
        </th>
        <th >
                                    限飞高度
        </th>
        <th>
                                    限制载重
        </th>
        </tr>
        
        <c:forEach items="${requestScope.deniedUAVs}" var="deniedUAV">
        <tbody>
        <tr >
        <th >
             ${deniedUAV.uid}
        </th>
       
         <th >
             ${deniedUAV.manufacturer}
        </th>
        <th >
             ${deniedUAV.companyName}
        </th>
        <th >
             ${deniedUAV.registrant}
        </th>
      
        <th >
             ${deniedUAV.limitedHeight}
        </th>
        <th >
             ${deniedUAV.limitedLoad}
        </th>
        </tr>
        </tbody>
        </c:forEach>
        </table>
        </c:if>
    </div>
    
  <div>
  <p>获得权限的无人机</p>
<c:if test="${!empty requestScope.certifiedUAVList}">
        <table border=1 cellpadding="10" cellspacing="0" class="a_list" width="600px">
        <tr >
       
        <th >
                                    识别号
        </th>
        <th >
                                    制造商
        </th>
         <th >
                                  所属公司            
        </th>
        <th >
                                    注册人
        </th>
        <th >
                                    限飞高度
        </th>
        <th >
                                    限制载重
        </th>
        </tr>
        <c:forEach items="${requestScope.certifiedUAVList}" var="certifiedUAV">
        <tbody>
        <tr >
        <th >
             ${certifiedUAV.uid}
        </th>
       
         <th >
             ${certifiedUAV.manufacturer}
        </th>
        <th >
             ${certifiedUAV.companyName}
        </th>
        <th >
             ${certifiedUAV.registrant}
        </th>
      
        <th >
             ${certifiedUAV.limitedHeight}
        </th>
        <th >
             ${certifiedUAV.limitedLoad}
        </th>
        </tr>
        </tbody>
        </c:forEach>
        </table>
        </c:if>
    </div>
    
    <div>
    <p>待审核的无人机</p>
<c:if test="${!empty requestScope.waitingVerifyUAVs}">
        <table border=1 cellpadding="10" cellspacing="0" class="a_list" width="600px">
        <tr >
       
        <th >
                                    识别号
        </th>
        <th >
                                    制造商
        </th>
         <th >
                                  所属公司            
        </th>
        <th>
                                    注册人
        </th>
        <th >
                                    限飞高度
        </th>
        <th >
                                    限制载重
        </th>
        </tr>
        <c:forEach items="${requestScope.waitingVerifyUAVs}" var="waitingVerifyUAV">
        <tbody>
        <tr >
        <th >
             ${waitingVerifyUAV.uid}
        </th>
       
         <th >
             ${waitingVerifyUAV.manufacturer}
        </th>
        <th >
             ${waitingVerifyUAV.companyName}
        </th>
        <th >
             ${waitingVerifyUAV.registrant}
        </th>
      
        <th >
             ${waitingVerifyUAV.limitedHeight}
        </th>
        <th >
             ${waitingVerifyUAV.limitedLoad}
        </th>
        </tr>
        </tbody>
        </c:forEach>
        </table>
        </c:if>
    </div>  
    </div>
</div>
  <div class="screenbg">
  <ul>
    <li><a href="javascript:;"><img src="../images/uav.jpg"></a></li>
  </ul>
</div>

</body>
</html>