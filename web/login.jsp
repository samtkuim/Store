<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>客戶登錄</title>
  <link rel="stylesheet" type="text/css" href="css/public.css">
</head>

<body>
<div class="header">智捷網上電腦商城</div>
<hr width="100%" />

<ul>
  <c:forEach var="i" items="${requestScope.errors}">
    <li class="error">${i}</li>
  </c:forEach>
</ul>

<form action="controller" id="form1" name="form1" method="post" >
  <input type="hidden" name="action" value="login">
  <table width="100%" align="center" >
    <tr height="40" >
      <td colspan="2" align="center"><strong>請您登錄</strong></td>
    </tr>
    <tr height="40" >
      <td width="50%" align="right" ><img src="images/3.jpg" align="absmiddle"/>&nbsp;&nbsp;客戶帳號：</td>
      <td><input type="text" name="userid" class="textfield" /></td>
    </tr>
    <tr height="40" >
      <td width="50%" align="right"><img src="images/2.jpg" align="absmiddle"/>&nbsp;&nbsp;客戶密碼：</td>
      <td><input type="password" name="password" class="textfield" /></td>
    </tr>
    <tr height="40" >
      <td align="right">&nbsp;</td>
      <td ><input type="image" src="images/login_button.jpg" onclick="document.forms[0].fn.value='login'" />
        &nbsp;&nbsp;&nbsp;&nbsp; <a href="controller?action=reg_init"><img src="images/reg_button.jpg" border="0" /></a></td>
    </tr>
  </table>
</form>
<%@include file="footer.jsp"%>
</body>
</html>