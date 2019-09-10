<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="hot_sale">
	<h2></h2>
	<%--<ul>
		<li>--%>
			<dl>
				<dt>
					<a href="" target="_self"> <img src="" /></a>
				</dt>
				<dd class="p_name">
					<a href="" target="_self"></a>
				</dd>
				<dd class="price">￥</dd>
			</dl>
	<%--	</li>
	</ul>--%>

	<script>
		$(function () {
			$.post("/findHotlist",{}
			,function (data) {
					var hotlist = data.ishot;
					var $dl = $(".hot_sale dl");
						for(var i=0;i < hotlist.length;i++){
							//"+hotlist[i].imgSource+"
							var $dt = $("<dt><a href='/findProductDetail?id="+hotlist[i].id+"'><img src='"+hotlist[i].imgSource+"' /> </a> </dt>");

							var $dd1 = $("<dt><a href='/findProductDetail?id="+hotlist[i].id+"'>"+hotlist[i].name+"</a> </dt>");
							var $dd2 = $("<dt><a href=''>"+hotlist[i].price+"</a> </dt>");

							$dl.append($dt);
							$dl.append($dd1);
							$dl.append($dd2)
						}

					},"json");
		})
	</script>
</div>
