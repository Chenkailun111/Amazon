<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fy" uri="http://java.sun.com/jsp/fenye/fy" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>亚马逊-首页</title>

<link href="${pageContext.request.contextPath}/css/index.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/adv.css"
	rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/scripts/jquery-2.1.0.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/scripts/adv.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/function.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/index.js"></script>
</head>



<body>
	<%@ include file="index_top.jsp"%>
	<div id="middle">
		<div class="p_left">
			<!--商品分类-->
			<%@ include file="index_product_sort.jsp"%>
			<!--商品分类结束-->

			<div class="pre_look">
				<h3>最近浏览</h3>
				<dl>
					<%--<dt>
						<img style="width: 54px; height: 54px;" src="${m.value}" />
					</dt>

					<dd>
						<a href="">${m.name}</a>
					</dd>--%>
				</dl>
			</div>
			
			<script>
				$(function () {
					$.get("/getCookie",{},function (data) {
						//获取cookie信息
						var cookie = data;
						// alert(data.length+"长度")
						$dl = $(".pre_look dl");
						for (var i=0; i< cookie.length;i++){
							//alert(cookie[i].name+"------"+cookie[i].imgSource)
							var $dt = $("<dt ><a href='/findProductDetail?id="+cookie[i].id+"'><img style='width: 54px; height: 54px;' src='"+cookie[i].imgSource+"' /></a> </dt >");
							var $dd = $("<dt><a href='"+cookie[i].name+"'></a> </dt>")
							$dl.append($dt);
							$dl.append($dd);
						}
					},"json");
				})
			</script>
		</div>

		<div class="p_center">
			<div id="wrapper">
				<div id="focus">
					<ul>
						<li><a href="#"><img src="images/T1.jpg" /></a></li>
						<li><a href="#"><img src="images/T2.jpg" /></a></li>
						<li><a href="#"><img src="images/T3.jpg" /></a></li>
						<li><a href="#"><img src="images/T4.jpg" /></a></li>
						<li><a href="#"><img src="images/T5.jpg" /></a></li>
					</ul>
				</div>
			</div>
			<div class="p_list">
				<div class="p_info">
					<img src="images/icon_sale.png" />商品列表
				</div>
			</div>

			<ul class="product2">
					<li>
						<dl>

						</dl>
						<%--<dl class="dl1">
							<dt>
								<a href="" target="_self">
								<img src="" /></a>
							</dt>
							<dd class="title">
								<a href="" target="_self"></a>
							</dd>
							<dd class="price">￥</dd>
							&lt;%&ndash;<td colspan="20" style="text-align: center;">
									<fy:fy url="/user/selectCar?carNumber=${carNumber}&color=${color}&price=${price}"
										   pageInfo="${pi}"></fy:fy>

									</td>&ndash;%&gt;
						</dl>--%>
					</li>

			</ul>
			<div align="center" style="margin-top:10px" id="btn">
				<a href="">首页</a>&nbsp;&nbsp;&nbsp;
				<a href="">上一页</a>&nbsp;&nbsp;
				<a href="">下一页</a>&nbsp;&nbsp;
				<span></span>/<span></span>&nbsp;
				<a href="">最后一页</a>
			</div>


			<%--获取商品列表--%>
			<script>
				$(function () {
					showImg(1,12);
				})
				
				function showImg(index,size) {
					$.get("/findList",{
						"index":index,
						"size":size
					},function (data) {
						var $ul = $(".product2");
						$ul.empty();
						//取出map数据
						var map = data.map;
						var prolist = map.prolist;
						var pi = map.pi;
						//alert(prolist.length+"@@@@@@@")
						for(var i=0;i<prolist.length;i++){
							var img = prolist[i];
							var $li = $("<li></li>");
							var $dl = $("<dl></dl>");
							var $dt = $("<dt></dt>");
							var $dd1 = $("<dd class='title'>"+img.name+"<dd>");
							var $dd2 = $("<dd class='price'>￥"+img.price+"<dd>");
							var $a1 = $("<a href='/findProductDetail?id="+img.id+"' target='_self'></a>");
							var $a2 = $("<a href='' target='_self'></a>");
							var $img = $("<img src='"+img.imgSource+"'>");
							$a1.append($img);
							$dt.append($a1);
							$dd1.append($a2);
							$dl.append($dt);
							$dl.append($dd1);
							$dl.append($dd2);
							$li.append($dl);
							$ul.append($li);
						}
						var $a = $("#btn a");
						$a.eq(0).attr("href","javascript:showImg('" + 1 + "','" + size + "')");
						$a.eq(1).attr("href","javascript:showImg('" + (pi.prePage==0?1:pi.prePage) + "','" + size + "')");
						$a.eq(2).attr("href","javascript:showImg('" + (pi.nextPage==0?pi.pages:pi.nextPage) + "','" + size + "')");
						var $span = $("#btn span");
						$span.eq(0).html(index);
						$span.eq(1).html(pi.total);
						$a.eq(3).attr("href","javascript:showImg('" + pi.pages + "','" + size + "')");
					},"json");
				}
			</script>
		</div>


		<div id="p_right">
			<%@ include file="index_news.jsp"%>
			<%@ include file="hotproduct.jsp"%>
		</div>
	</div>

	<div id="foot">Copyright © 2018 上海海文 All Rights Reserved.

	</div>
</body>
</html>