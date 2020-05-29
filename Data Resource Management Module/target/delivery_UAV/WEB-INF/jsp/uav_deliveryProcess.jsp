<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript" >
window.onload =function(){
	
		var sh;
		sh=setInterval(function(){

			 if(${delivery_stage}==3||${delivery_stage}==4||${delivery_stage}==5){
				 clearInterval(sh);
			 }else{
				/* document.getElementById("startDelivery").style.visibility="hidden"; */
				window.location.href="../uav/saveDeliveryProcess.do?order_id=${order_id}&route=${route}&uav_id=${uav_id}";
				
				alert("i===="+i);
			 }
			
		},3000);  
		
	
};
</script>

<style type="text/css">
.main{
width:750px;height:100px;margin-top:200px;margin-left:500px;margin-right:200px;border:1px solid #960;

}
.left{
width:140px;height:100px;float:left;border:1px solid #960;

}
.right{

width:600px;height:100px;float:right;border:1px solid #960;

}

</style>

</head>
<body>
 <c:if test="${!empty requestScope.startFlag}">
	
<div class="main" >

<div class="left">
<div  height="60px">

</div>
<div  height="290px">
${time}
</div>
</div>

<div class="right">
<div  height="60px">
<p ><font weight="bold">【${deliveryState}】</font></p>
</div>
<div  height="290px">
无人机【${uav_id}】承载着包裹【${order_id}】开始配送
</div>
</div>

</div>
</c:if>


<c:if test="${!empty requestScope.flyFlag}">
	
<div class="main" >

<div class="left">
<div  height="60px">

</div>
<div  height="290px">
${time}
</div>
</div>

<div class="right">
<div  height="60px">
<p ><font weight="bold">【${deliveryState}】</font></p>
</div>
<div  height="290px">
无人机【${uav_id}】承载着包裹【${order_id}】飞行至【${currentLoc}】处
<c:if test="${deliveryState=='待取件'}">
<form action="../uav/uploadImage.do?order_id=${order_id}&currentLoc=${currentLoc}" method="post"  enctype="multipart/form-data">
上传取货凭证：<input type="file" name="image" /><button>上传</button>
</form>

</c:if>
</div>
</div>

</div>
</c:if>

</body>
</html>