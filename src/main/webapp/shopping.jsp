<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>亚马逊- 购物车</title>
<link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/adv.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/scripts/jquery-2.1.0.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/scripts/adv.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/function.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/index.js"></script>
<script type="text/javascript" src="scripts/shopping.js"></script>
</head>

<script type="text/javascript">
	function check(){
		var singles=$("[name=single]");
		var k=-1;
		for(var i=0;i<singles.length;i++){
			if(singles[i].checked==true){
				k=2;
				document.forms[0].submit();
				break;
			}
		}

		if(k==-1){
			alert("请先选择要结算的选项");
		}
	}
	$(function(){
		$("[name=all]").click(function(){
			//alert($(this)[0].checked);
			var singles=$("[name=single]");
			for(var i=0;i<singles.length;i++){
				singles[i].checked=$(this)[0].checked;
			}
		});


	})
</script>
<body>
<%@ include file="index_top.jsp"  %>

<div id="position" class="wrap">
	您现在的位置：<a href="index.jsp">亚马逊</a> &gt; 购物车
</div>
<div class="wrap">
	<div id="shopping">
		<form action="/doBuy" method="post">
			<table>
				<tr>
					<th><input name="all" type="checkbox"/></th>
					<th>商品名称</th>
					<th>商品价格</th>
					<th>购买数量</th>
					<th>操作</th>
				</tr>


				<!-- 根据用户购物车生成列表 -->
				<c:forEach items="${shopCart}" var="m">
				<tr id="product_id_1">
					<td><input type="checkbox" value="${m.id}" name="single"/></td>
					<td class="thumb"><img style="width: 100px; height: 100px;" src="${m.product.imgSource}" /><a href="/findProductDetail?id=${m.product.id}"></a></td>
					<td class="price" id="price_id_1">
						￥<span id="span_1"></span>
						<input name="money" value="${m.product.price}" readonly/>
						<%--<input type="hidden" id="subPrice" value="" name="sumPrice"/>--%>
						<input type="hidden"  value="${m.product.id}" name="pid"/> <%--产品id--%>
						<%--<input type="hidden"  value="" name="hpStock" id=""/>--%>
					</td>
					<td class="number">
							<c:set var="hcid" value=""></c:set>
							
							<input type="button" id="minus"value=" - " width="3px" name="minusButton">
							<input id="" type="text" name="quantity" value="${m.pnum}" maxlength="5"
							size="1"  style="text-align:center; vertical-align:middle" onblur="checkStock()"/>
							<input type="button" id="add" value=" + " width="2px" name="addButton">		
					</td>
					<td class="delete"><a href="/deleteById?id=${m.product.id}">删除</a></td>
				</tr>
				</c:forEach>

			</table>
			<div class="button"><input type="button" value="" onclick="check()" /></div>
		</form>
	</div>
</div>
<div id="footer">
	Copyright &copy; 2018 上海海文 All Rights Reserved.
</div>
</body>
</html>

