<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>收件人主页面</title>

<style type="text/css">
.goods{
width:405px;height:200px;margin-left:500px;margin-right:200px;border:1px solid #960;
}
.image{
width:200px;height:200px;float:left;border:1px solid #960;

}
.description{
width:200px;height:200px;float:right;border:1px solid #960;
}

#state{
float:left;

}
#detail{
float:right;

}
</style>


</head>
<body>

<c:forEach items="${requestScope.orderList}" var="order">

<div class="goods">

<div class="image">
<img src="../images/huawei.jpg" alt="huawei phone" width="100%" height="100%">
</div>

<div class="description">
<div>
<p><a href="">华为P30降1850旗舰机HUAWEI HUAWEI P30全网通全新正品手机pro</a>&nbsp;&nbsp;&nbsp;</p>
</div>
<div>
&nbsp;&nbsp;&nbsp;
</div>
<div>
&nbsp;&nbsp;&nbsp;
</div>
<div>
&nbsp;&nbsp;&nbsp;
</div>
<div id="state">
【${order.state}】
</div>
<div id="deteil">
<a href="../receiver/deliveryDetails.do?order_id=${order.order_id}">物流详情</a>
</div>
</div>

</div>

</c:forEach>
</body>
</html>