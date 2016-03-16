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
  <h1>快速查询，帮您选出最佳的旅游比价决策</h1>
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
				<div class="" style="font-size: 20px;margin-top: 20px">
						价格排序：
						<select id="order" style="width: 100px" onchange="submitFun('order')">
							<option value="ASC" name="price" <c:if test="${page.placelevel == 'ASC'}">selected</c:if>  id="id_asc">升序</option>
							<option value="DESC" name="price" <c:if test="${page.placelevel == 'DESC'}">selected</c:if> id="id_desc">降序</option>
						</select>

						酒店星级数筛选：
						<select id="level" style="width: 100px" onchange="submitFun1('level')">
							<option value="default" name="hotel" <c:if test="${page.hotellevel == 'all'}">selected</c:if>>所有</option>
							<option value="3" name="hotel" <c:if test="${page.hotellevel == '1'}">selected</c:if>>111</option>
							<option value="3" name="hotel" <c:if test="${page.hotellevel == '2'}">selected</c:if>>三星</option>
							<option value="4" name="hotel" <c:if test="${page.hotellevel == '3'}">selected</c:if>>四星</option>
							<option value="5" name="hotel" <c:if test="${page.hotellevel == '4'}">selected</c:if>>五星</option>
						</select>

					<script type="text/javascript">
						function submitFun(name){

							var obj = document.getElementById(name);
							var index = obj.selectedIndex;
							var value = obj.options[index].value;
							alert(value);
							alert(${page.records[0].ST});
							window.location.href="${page.uri}?num=1&ST=${page.records[0].ST}&SP=${page.records[0].SP}&EP=${page.records[0].EP}&placelevel="+value
						}
					</script>

					<script type="text/javascript">
						function submitFun1(name){
							var obj = document.getElementById(name);
							var index = obj.selectedIndex;
							var value = obj.options[index].value;
							alert(value);
							window.location.href="${page.uri}?num=1&ST=${page.records[0].ST}&SP=${page.records[0].SP}&EP=${page.records[0].EP}&hotellevel="+value
						}
					</script>
				</div>
			<c:if test="${!empty page.records }">
			    <c:forEach items="${page.records}" var="c" varStatus="status">
			    <c:choose>
			    <c:when test="${status.count ==1 || status.count ==5}">
				<div class="top-grids">
				<div class="top-grid">
				</c:when>
				
				<c:otherwise>
				<div class="top-grid">
				</c:otherwise>
				</c:choose>
				
						<a href="${c.URL}"><img src="${c.IMAGE}" alt="" /></a>
						<div class="top-grid-info">
							<a href="${c.URL}">${c.EP}</a>
							<p>出发地点：${c.SP}</p>
                            <p>目的地点：${c.EP}</p>
							<p>出发时间：${c.ST}</p>
                            <p>路由路线：${c.SIGHTS}</p>
                            <p>路由价格：${c.TOTALPRICE}</p>
						</div>
					</div>
				
					<c:if test="${status.count%4 == 0}">
					 <div class="clearfix"> </div>
					 </c:if>
				</c:forEach>
					
				 </c:if>			
			
				  
			</div>	 
			
			</div>	
			
			<!-- //container -->
			 <center><%@include file="Page.jsp"%></center>
		</div>	
	
		<!-- //banner-grids -->
		<!-- before -->

	</body>
</html>