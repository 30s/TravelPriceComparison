<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<title>Journey a Travel Category Flat Bootstarp Responsive Website Template | Home :: w3layouts</title>

<link href="css/search/style1.css" rel='stylesheet' type='text/css' media="all" />
<!--theme-style-->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />	
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all">
<!--//theme-style-->



<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Journey Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- web-font -->
<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
<!-- web-font -->
<!-- js -->
<script src="js/jquery.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- js -->
<!-- start-smoth-scrolling -->
<script type="text/javascript" src="js/move-top.js"></script>
<script type="text/javascript" src="js/easing.js"></script>
<script type="text/javascript">
			jQuery(document).ready(function($) {
				$(".scroll").click(function(event){		
					event.preventDefault();
					$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
				});
			});
		</script>
		<!-- start-smoth-scrolling -->
</head>
	<body>
		<!-- header -->
		<div class="header">
			<!-- container -->
			<div class="container">

<div class="banner-top">
  <h3>帮您快速选出最佳的旅游比价决策</h3>
  <div class="banner-bottom">
    <div class="bnr-one">
      <div class="bnr-left">
        <p>出发时间</p>
      </div>
      <div class="bnr-right">
      
      <form action="travelAction.action" method="post">
        <input class="date" name="ST" id="datepicker" type="text" required="" />

      </div>
      <div class="clearfix"></div>
    </div>
    <div class="bnr-one">
      <div class="bnr-left">
        <p>出发地</p>
      </div>
      <div class="bnr-right">
      <input type="text" name="SP" value=""  required="required" />
      </div>
      <div class="clearfix"></div>
    </div>
    <div class="bnr-one">
      <div class="bnr-left">
        <p>目的地</p>
      </div>
      <div class="bnr-right">
        <input type="text" name="EP" value=""  required="required" />
      </div>
      <div class="clearfix"></div>
    </div>
    <div class="bnr-btn">
        <input type="submit" value="查询" />
      </form>
    </div>
  </div>
</div>

		<link rel="stylesheet" href="css/jquery-ui.css" />
		<script src="js/jquery-ui.js"></script>
			<script>
				$(function() {
				$( "#datepicker,#datepicker1" ).datepicker();
				});
			</script>				
  </div>
			<!-- //container -->
		</div>
		<!-- //header -->
		<!-- banner-grids -->
		<div class="banner-grids">
			<!-- container -->
			<div class="container">
				<div class="banner-grid-info">
					<h3>搜索结果</h3>
				</div>
				
			    <c:forEach items="${travel}" var="c" varStatus="status">

				<div class="top-grid">

						<a href="${c.URL}"><img src="images/6.jpg" alt="" /></a>
						<div class="top-grid-info">
							<a href="${c.URL}">${c.EP}</a>
							<p>出发地点：${c.SP}</p>
                            <p>目的地点：${c.EP}</p>
							<p>出发时间：${c.ST}</p>
                            <p>路线：${c.SIGHTS}</p>
                            <p>价格：${c.TOTALPRICE}</p>
						</div>
					</div>
				
				</c:forEach>

				  
			</div>	 
			
			</div>
				<a href="http://localhost:8787/hadoopweb/travelAction.action">下一页</a>
			<!-- //container
				第${page.currentPageNum}页/共${page.totalPage}页&nbsp;&nbsp;
				首页</a>
				<a href="${page.uri}?num=${page.prePageNum}&ST=${page.records[0].ST}&SP=${page.records[0].SP}&EP=${page.records[0].EP}">上一页</a>
				&nbsp;&nbsp;

				<c:forEach begin="${page.startPage}" end="${page.endPage}" var="num">
					<a href="${page.uri}?num=${num}&ST=${page.records[0].ST}&SP=${page.records[0].SP}&EP=${page.records[0].EP}">${num}</a>
				</c:forEach>

				&nbsp;&nbsp;
				<a href="${page.uri}?num=${page.nextPageNum}&ST=${page.records[0].ST}&SP=${page.records[0].SP}&EP=${page.records[0].EP}">下一页</a>
				<a href="${page.uri}?num=${page.totalPage}&ST=${page.records[0].ST}&SP=${page.records[0].SP}&EP=${page.records[0].EP}">尾页</a>
		 -->
		</div>
	
		<!-- //banner-grids -->
		<!-- before -->

		<!-- //before -->
		<!-- footer -->

		<!-- //footer -->
		<script type="text/javascript">
									$(document).ready(function() {
										/*
										var defaults = {
								  			containerID: 'toTop', // fading element id
											containerHoverID: 'toTopHover', // fading element hover id
											scrollSpeed: 1200,
											easingType: 'linear' 
								 		};
										*/
										
										$().UItoTop({ easingType: 'easeOutQuart' });
										
									});
								</script>
									<a href="#" id="toTop" style="display: block;"> <span id="toTopHover" style="opacity: 1;"> </span></a>
	<!-- content-Get-in-touch -->
	</body>
</html>