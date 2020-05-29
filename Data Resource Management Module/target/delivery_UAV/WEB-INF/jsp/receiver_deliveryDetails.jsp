<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>物流详情</title>
<style type="text/css">
.main{
width:750px;height:100px;margin-left:350px;margin-right:200px;border:1px solid #960;

}
.left{
width:140px;height:100px;float:left;

}
.right{

width:600px;height:100px;float:right;

}

</style>
</head>
<body>


<c:forEach items="${requestScope.processOfDelivery}" var="deliveryProcess">
<div class="main" >
<div class="left">
<div  height="60px">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<div  height="290px">
${deliveryProcess.time}
</div>
</div>

<div class="right">
<div  height="60px">
<p ><font weight="bold">【${deliveryProcess.state}】</font></p>
</div>
<div  height="290px">
<c:if test="${deliveryProcess.state=='开始配送'}">
您的包裹已由无人机【${uav_id}】装箱，并开始配送。当前处于【${deliveryProcess.location}】处

</c:if>
<c:if test="${deliveryProcess.state=='运输中'}">
包裹已到达【${deliveryProcess.location}】处，配送无人机为：【${uav_id}】
</c:if>
<c:if test="${deliveryProcess.state=='包裹丢失'}">
您的包裹于【${deliveryProcess.location}】处丢失，配送无人机为：【${uav_id}】
</c:if>
<c:if test="${deliveryProcess.state=='无人机故障'}">
配送无人机【${uav_id}】在【${deliveryProcess.location}】处发生故障，包裹暂停配送
</c:if>
<c:if test="${deliveryProcess.state=='待取件'}">
配送无人机【${uav_id}】已到达【${deliveryProcess.location}】，并您到达指定位置后输入验证码
</c:if>
<c:if test="${deliveryProcess.state=='配送完成'}">
您已于【${deliveryProcess.location}】处成功取到包裹，并完成了支付
</c:if>
</div>
</div>
</div>
</c:forEach>
</body>
</html>