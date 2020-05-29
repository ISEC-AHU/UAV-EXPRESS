<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册无人机</title>
<link href="../../css/student.css" type=text/css rel=stylesheet>
<link href="../../css/button.css" type=text/css rel=stylesheet>
</head>
<body>
  <form action="../deliverystation/addUAV.do" method="post" name="student" onSubmit="return formcheck()">
  <table  class="addstudent">
  <tr class="addstudent1">
         <th class="addstudent1">识&nbsp;&nbsp;&nbsp;&nbsp;别&nbsp;&nbsp;&nbsp;&nbsp;号：</th><!-- 表头数据 -->
         <td><!-- 一列数据 -->
         <!--type="text" 文本框 -->
         <input type="text" id="uid" name="uid" >
         </td>
         </tr>
         
         <tr class="addstudent2">
         <th class="addstudent2">制&nbsp;&nbsp;&nbsp;&nbsp;造&nbsp;&nbsp;&nbsp;&nbsp;商:</th>
         <td><input type="text" id="manufacturer" name="manufacturer"></td>
         </tr>
         
         
         <tr class="addstudent3">
         <th class="addstudent3">所&nbsp;属&nbsp;公&nbsp;司:</th>
         <td><input type="text" id="companyName" name="companyName" ></td>
         </tr>
       
         <tr class="addstudent4">
         <th class="addstudent4">公&nbsp;司&nbsp;账&nbsp;户:</th>
         <td><input type="text" id="companyAccount" name="companyAccount"></td>
         </tr>
         
         
         <tr class="addstudent5">
         <th class="addstudent5">限&nbsp;飞&nbsp;高&nbsp;度:</th>
         <td><input type="text" id="limitedHeight" name="limitedHeight"></td>
         </tr>
         
          <tr class="addstudent6">
         <th class="addstudent6">限&nbsp;制&nbsp;载&nbsp;重:</th>
         <td><input type="text" id="limitedLoad" name="limitedLoad"></td>
         </tr>
         
   
         <input type="hidden" id="right" name="right" value="0">
          <input type="hidden" id="registrant" name="registrant" value= ${sessionScope.userName}>
         
         <%-- <tr class="addstudent8">
         <th class="addstudent8">专业名：</th>
         <td>
      <select name="classid" class="select1">
      <option value="0">---请选择专业--</option>
      <s:forEach items="${mcList}" var="d">
        <option value="${d.id}">${d.dname}${d.mname}</option>
     </s:forEach>   
   </select>
   </td>
   </tr> --%>
         

         <tr>
         <th>
         <!-- 按钮 -->
      	<input type="submit" class="submit" value="新增"></input>
         <td>
         <!-- 重置按钮 -->
         <input type="reset" class="submit1"  value="重置"></td>
         </tr>
</table>
</form>

</body>
</html>