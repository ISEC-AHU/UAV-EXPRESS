<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../Main/top.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AMD审核页面</title>
<link href="../../css/student.css" type=text/css rel=stylesheet>
<link href="../../css/button.css" type=text/css rel=stylesheet>
</head>
<body>
<div  style="margin-top:50px">
<div class="nomessage">
    <c:if test="${empty requestScope.uavList}">
		没有任何无人机信息.
	</c:if>
</div>
    <c:if test="${!empty requestScope.uavList}">
        <table border=1 cellpadding="10" cellspacing="0" class="a_list" width="1200px">
        <tr class="a_list1" height="30px">
       
        <th class="a_list1" width="60px">
                                    识别号
        </th>
        <th class="a_list1" width="60px">
                                    制造商
        </th>
         <th class="a_list1" width="60px">
                                  所属公司            
        </th>
        <th class="a_list1" width="60px">
                                    注册人
        </th>
        <th class="a_list1" width="60px">
                                    限飞高度
        </th>
        <th class="a_list1" width="60px">
                                    限制载重
        </th>
        
        <th class="a_list1" width="60px">
                                   操作                     
        </th>
       
        </tr>
        <c:forEach items="${requestScope.uavList}" var="uav">
        <tbody>
        <tr class="a_list2">
        <th class="a_list2">
             ${uav.uid}
        </th>
       
         <th class="a_list2">
             ${uav.manufacturer}
        </th>
        <th class="a_list2">
             ${uav.companyName}
        </th>
        <th class="a_list2">
             ${uav.registrant}
        </th>
      
        <th class="a_list2">
             ${uav.limitedHeight}
        </th>
        <th class="a_list2">
             ${uav.limitedLoad}
        </th>
        
       <th class="a_list2">
            <a href="../AMD/grantRight.do?uid=${uav.uid}&manufacturer=${uav.manufacturer}&companyAccount=${uav.companyAccount}&limitedHeight=${uav.limitedHeight}&limitedLoad=${uav.limitedLoad}"> <font color="blue">通过</font></a>  
            <a href="../AMD/denyUAV.do?uid=${uav.uid}"> <font color="red">拒绝</font></a>
        </th>
        
        </tr>
        </tbody>
        </c:forEach>
        </table>
        </c:if>
        
  
    </div>
</body>
</html>