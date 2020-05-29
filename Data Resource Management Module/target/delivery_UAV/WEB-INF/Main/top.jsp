<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../../css/navigation.css" type=text/css rel=stylesheet>
<link href="../../css/style.css" type=text/css rel=stylesheet>
<link href="../../css/button.css" type=text/css rel=stylesheet>
<script type="text/javascript">
 window.onload = function() {
  var show = document.getElementById("show");
  setInterval(function() {
   var time = new Date();
   // 程序计时的月从0开始取值后+1
   var m = time.getMonth() + 1;
   var t = time.getFullYear() + "-" + m + "-"
     + time.getDate() + " " + time.getHours() + ":"
     + time.getMinutes() + ":" + time.getSeconds();
   show.innerHTML= t;
  }, 0);

 };
</script>
</head>
<body  >
	<div class="SystemName">
	    <h1 class="hsl">基于区块链的无人机配送系统</h1>
	</div>
	<br>
	<div >
		<table cellSpacing=0 cellPadding=0 width="100%">
			<tbody>
				<tr>
					<td>
						<div class="welcome">
							<img src="../images/welcome.png" align="left" height="20" width="20" /> 欢迎【${sessionScope.user.username }】登录
						</div>
					</td>
					<td align=right >
					<span style="PADDING-RIGHT: 10px">
							<a href="javascript:history.go(-1);"> 
							  <img src="../images/back.png"  height="20" width="20" />后退
						    </a> 
						    <a href="javascript:history.go(1);">
						      <img src="../images/advance.png"   height="20" width="20" />前进
						    </a> 
						    <a href="../changePwd/changePassword.do" >
							  <img src="../images/return.png"   height="20" width="20" />修改密码
							</a>
						    <a href="../relo/reLogin.do" target=_top>
							  <img src="../images/return.png"   height="20" width="20" />重新登录
							</a>
							<a href="" target=mainFrame>
							  <img src="../images/help.png"   height="20" width="20" />帮助
							</a> 
							<img src="../images/time.png" height="20" width="20"> 
							<a id="show"></a>
					</span>
					
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	
	
</body>
</html>