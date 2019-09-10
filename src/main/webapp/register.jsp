<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>亚马逊 - 注册页</title>
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
		$("#userName").blur(function () {
			// alert("光标移除")
			var  LoginDiv = $("#LoginDiv");
				var userName = $("[name=uname]").val();
				var errMsg = $("#errMsg");
			$.get("/CheckUserName",{
				userName:userName
			},function (data) {
				var code = data.code;
				var  msg = data.msg;
				if (code == 0){

					LoginDiv.html("可以使用");
					//errMsg.innerHTML("可以使用");
				} else {
					LoginDiv.html(msg);
				}

			});

		})


		$("#submitAdd").click(function () {
			var dataUrl = $("#regForm").attr("action");
			var data = $("#regForm").serialize();
			$.ajax({
				url: dataUrl,
				type: "post",
				dataType: "json",
				data: data,
				success:function (data) {
					if(data.code == 0){//登录成功
						alert('success');
						parent.location.href ='login.jsp';
					}else {
						var code = data.code;
						var msg = data.msg;
						alert(code+"失败"+msg)
						location.href = "/register.jsp";
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
			<h1>欢迎注册亚马逊</h1>
			<ul class="steps clearfix">
				<li class="current"><em></em>填写注册信息</li>
				<li class="last"><em></em>注册成功</li>
			</ul>
			<form id="regForm" method="post" action="/registerUser" onsubmit="return checkForm(this)">
				<table>
					<tr>
						<td class="field">用户名：</td>
						<td><input id="userName" class="text" type="text" name="uname" onfocus="FocusItem(this)"   maxlength="15"/>
							<div id="LoginDiv">

							</div>
						</td>
					</tr>
					<tr>
						<td class="field">登录密码：</td>
						<td><input class="text" type="password" id="passWord" name="pwd" onfocus="FocusItem(this)" onblur="CheckItem(this);" /><span></span></td>
					</tr>
					<tr>
						<td class="field">确认密码：</td>
						<td><input class="text" type="password" name="rePassWord" onfocus="FocusItem(this)" onblur="CheckItem(this);" /><span></span></td>
					</tr>
					<tr>
						<td class="field">性别：</td>
						<td ><input type="radio" name="sex"  style="border:0px;" checked="checked" value="0" />男<input type="radio" name="sex" style="border:0px;" value="1"/>女<span></span></td>
					</tr>
					<%--&lt;%&ndash;<tr>
						<td class="field">出生日期：</td>
						<td><input class="text" type="text" name="birthday" onfocus="FocusItem(this)" onblur="CheckItem(this);" /><span></span></td>
					</tr>
					<tr>
						<td class="field">身份证：</td>
						<td><input class="text" type="text" name="identity" onfocus="FocusItem(this)" onblur="CheckItem(this);" /><span></span></td>
					</tr>
					<tr>
						<td class="field">电子邮件：</td>
						<td><input class="text" type="text" name="email" onfocus="FocusItem(this)" onblur="CheckItem(this);" onmouseout="checkEmail()"/><span id="uemail"></span></td>
					</tr>--%>
					<tr>
						<td class="field">手机：</td>
						<td><input class="text" type="text" id="mobile" name="mobile" onfocus="FocusItem(this)" onblur="CheckItem(this);" /><span></span></td>
					</tr>
					<tr>
						<td class="field">地址：</td>
						<td><input class="text" type="text" name="address" onfocus="FocusItem(this)" onblur="CheckItem(this);" /><span></span></td>
					</tr>
					<tr>
						<td class="field">验证码：</td>
						<td><input type="text" name="veryCode" maxlength="6"/>
							<input type="button" onclick="sendCode()" value="发送" />
							
							<script>
								function sendCode() {
									var mobile = $("#mobile").val();
									if (mobile.length == 0){
										alert("手机号为空")
										return;
									}else {
										$.get("/sendCode",
												{
													"mobile":mobile
												},
												function (data) {
													var msg = data.msg;
													alert(msg)
													location.href="register.jsp";
												},"json");
									}
								}
							</script>
						</td>

							<%--<img id="veryCode" src="code.jsp" />--%>

							<%--<span id="Code"></span></td>--%>

					</tr>
					<tr>
						<td></td>
						<td><label class="ui-green"><input type="button" id="submitAdd" name="submit" value="提交注册" /></label></td>

					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div id="footer">
	Copyright &copy; 2018 上海海文 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>

