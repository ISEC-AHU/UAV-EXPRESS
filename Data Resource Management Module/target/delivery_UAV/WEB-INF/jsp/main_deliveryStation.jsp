<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基于区块链的无人机配送系统</title>
<link href="../css/login.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all" />
</head>

<body>
<div id="tab">

  <div class="tab_box">

    <!-- <div class="sec_error_box"></div> -->
   <!--  <div style="display: none;" class="alert alert-success" id="msg"></div> -->
       <table>
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

  </div>
</div>

<div class="bottom"> <a href="javascript:;" target="_blank"></a> <span></span><img width="13" height="16" src="../images/copy_rignt_24.png" /></div>
<div class="screenbg">
  <ul>
    <li><a href="javascript:;"><img src="../images/uav.jpg"></a></li>
   <!--  <li><a href="javascript:;"><img src="images/1.jpg"></a></li>
    <li><a href="javascript:;"><img src="images/2.jpg"></a></li> -->
  </ul>
</div>
</body>
</html>
