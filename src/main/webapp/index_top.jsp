
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	double num = Math.random();
%>

<div id="header">
	<div class="login_menu">
		<div class="login_container">
			<c:set value="${sessionScope.user }" var="user"></c:set>
			<ul class="m_left">
				<li><a href="login.jsp" class="c_red">请登录</a>&nbsp;&nbsp;&nbsp;</li>
				<li><a href="register.jsp">请注册</a></li>
			</ul>

			<ul class="m_right">
				<li><img src="images/icon_3.png"><a
					href="/showCar" class="c_red">购物车</a></li>
				<li><img src="images/icon_4.png"><a
					href="javascript:AddFavorite('我的网站',location.href)">收藏</a></li>
				<li><img src="images/icon_2.png"><a href="/guestbook">留言</a></li>
				<li><img src="images/icon_1.png"><a href="index.jsp">首页</a></li>
				<%--<li><img src="images/icon_1.png"><a href="/findOrderDetail">查看个人订单明细</a></li>--%>
			</ul>
		</div>
	</div>
	<div class="logo_search">
		<div class="logo">
			<img src="images/LOGO.png">
		</div>
		<div class="search">

			<form id="form1" action="/findSecondCategory" method="post">
			<input type="text" placeholder="输入宝贝" id="qname" name="productName" />
			<button class="query_button" onclick="search()">搜索</button>
			</form>
		</div>
	</div>

	<script>
		function search() {
			document.forms[0].submit();
		}
	</script>
	<%--<div class="nav_bar">
		<div class="nav_bar_container">
			<ul>
				<li><a href="#">商品名称</a></li>
				<li><a href="#">商品名称</a></li>
				<li><a href="#">商品名称</a></li>
				<li><a href="#">商品名称</a></li>
			</ul>
		</div>
	</div>--%>
</div>
