<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
<style type="text/css">
.news-list ul li {
	
}


</style>

<div class="newsList">
	<h2>新闻动态</h2>
	<ul>
	<%--<li><a href=""></a></li>--%>
	</ul>
	
	<script>
		$(function () {
			$.post("${pageContext.request.contextPath}/findNews",{

			},function (data) {
				var  news = data.news;
				//alert(news.length)
				var $ul = $(".newsList ul");

				for (var i = 0 ;i < news.length; i++){
					//var $dt = $("<dt><a href=''>" + productCategoriesAll[i].name + "</a></dt>");
					//$dl.append($dt);
					var $li = $("<li><a href='${pageContext.request.contextPath}/findNewsDetail?id="+news[i].id+"'>"+news[i].title+"</a> </li>");
					$ul.append($li);
				}
			},"json");
		})
	</script>
</div>
