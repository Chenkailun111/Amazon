<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>亚马逊 - 登录</title>
<link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/adv.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/scripts/jquery-2.1.0.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/scripts/adv.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/function.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/index.js"></script>
</head>

<script>
	$(function () {
		$("#submitAdd").click(function(){
			var targetUrl = $("#loginForm").attr("action");
			var data = $("#loginForm").serialize();
			$.ajax({
				type:'post',
				url:targetUrl,
				//cache: false,
				data:data,
				dataType:'json',
				success:function(data){
					if(data.code == 0){//登录成功
						alert('success');
						parent.location.href ='index.jsp';
					}else {
						var msg = data.msg;
						alert("失败"+msg)
						location.href = "/login.jsp";
					}
				}
			})

		})
	})
</script>
<body>
<%@ include file="index_top.jsp"  %>
<div id="register" class="wrap">
	<div class="shadow">
		<em class="corner lb"></em>
		<em class="corner rt"></em>
		<div class="box">
			<h1>欢迎回到亚马逊</h1>
			<form id="loginForm" method="post" action="/login" onsubmit="return loginCheck()">
				<table>
					<tr>
						<td class="field">用户名：</td>
						<td><input class="text" type="text" name="username" onfocus="FocusItem(this)" onblur="CheckItem(this);" /><span></span></td>
					</tr>
					<tr>
						<td class="field">登录密码：</td>
						<td><input class="text" type="password" id="passWord" name="password" onfocus="FocusItem(this)" onblur="CheckItem(this);" /><a href="retrieve_password.jsp">忘记密码</a></td>
		
					</tr>
					<tr>           
						<td class="field">验证码：</td>
						<%--<td><input class="text verycode" type="text" name="veryCode" onfocus="FocusItem(this)" onblur="CheckItem(this);" maxlength="4"/><img id="veryCode" src="code.jsp" /><span id="Code"></span></td>--%>
							<td><img id="img" src="/getyanzheng" onclick="b()" alt=""/>
								<input type="text" name="yanzheng">
							</td>

						<script type="text/javascript">
							function b(){
								document.getElementById("img").src="/getyanzheng?num="+Math.random();
							}
						</script>
					</tr>
					<tr>
						<td></td>
						<td><label class="ui-green"><input type="submit" id="submitAdd" name="submit" value="立即登录" /></label></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div id="footer">
	Copyright &copy; 2018  上海海文 All Rights Reserved
</div>
</body>
</html>
