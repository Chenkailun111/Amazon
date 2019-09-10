<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
	<script src="${pageContext.request.contextPath}/scripts/jquery-2.1.0.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/scripts/adv.js"
			type="text/javascript"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/scripts/function.js"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/scripts/index.js"></script>
</head>

<div class="p_category">
	<h2 id="category">商品分类</h2>
		<dl>
			<dt>
				<a href=""></a>
			</dt>
				<dd>
					<a href=""></a>
				</dd>
		</dl>

	<script>
		$(function () {
			$.post("${pageContext.request.contextPath}/findCategory1", function (data) {
				var productCategoriesAll =data.menuList;

				var $dl = $(".p_category dl");
				for (var i = 0; i < productCategoriesAll.length; i++) {
					//alert(productCategoriesAll[i].name + "0000")
					var $dt = $("<dt><a href='#'>" + productCategoriesAll[i].name + "</a></dt>");
					$dl.append($dt);

					//获取list子菜单
					var productCategories = productCategoriesAll[i].childlist;
					//alert(productCategories.length + "kkkkk")
					for (var j = 0; j < productCategories.length; j++) {
						//alert(productCategories[j].name+"11111")
						var $dd = $("<dd><a href='/findSecondCategory?id="+productCategories[j].id+"'>"+productCategories[j].name+"</a></dd>");
						$dl.append($dd);
					}
				}
			}, "json")
		})
	</script>

</div>

