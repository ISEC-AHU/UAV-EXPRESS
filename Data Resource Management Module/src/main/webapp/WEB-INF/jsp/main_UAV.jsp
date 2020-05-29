<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../Main/top.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UAV home page</title>
<link href="../../css/student.css" type=text/css rel=stylesheet>
<link href="../../css/button.css" type=text/css rel=stylesheet>
</head>
<body>
<div  style="margin-top:50px">
<div class="nomessage">
    <c:if test="${empty requestScope.waitingOrders}">
		There is no task to be delivered.
	</c:if>
</div>
    <c:if test="${!empty requestScope.waitingOrders}">
        <table border=1 cellpadding="10" cellspacing="0" class="a_list" width="1200px">
        <tr class="a_list1" height="30px">
       
        <th class="a_list1" width="60px">
                   order_id
        </th>
        <th class="a_list1" width="60px">
                                   recipient
        </th>
         <th class="a_list1" width="60px">
                                  destination     
        </th>
        <th class="a_list1" width="60px">
                                   load
        </th>
      
        <th class="a_list1" width="60px">
                                 operation                     
        </th>
       
        </tr>
        <c:forEach items="${requestScope.waitingOrders}" var="order">
        <tbody>
        <tr class="a_list2">
        <th class="a_list2">
             ${order.order_id}
        </th>
       
         <th class="a_list2">
             ${order.receiverName}
        </th>
        <th class="a_list2">
             ${order.receriverAddr}
        </th>
        <th class="a_list2">
             ${order.packageLoad}
        </th>
      
       <th class="a_list2">
            <a href="../uav/startDelivery.do?order_id=${order.order_id}"> <font color="blue">start Delivery</font></a>  
        </th>
        
        </tr>
        </tbody>
        </c:forEach>
        </table>
        </c:if>
        
  
    </div>
</body>
</html>