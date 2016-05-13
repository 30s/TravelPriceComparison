<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>BusinessNews - Business</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width" />

<!--[if lt IE 9]>
<![endif]-->

<link rel="stylesheet" href="layout/style.css" type="text/css" />
<link href="http://fonts.googleapis.com/css?family=PT+Sans:400,700"
	rel="stylesheet" type="text/css" />
<link
	href="http://fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700"
	rel="stylesheet" type="text/css" />
<link
	href="http://fonts.googleapis.com/css?family=Droid+Serif:400,400italic"
	rel="stylesheet" type="text/css" />

<!-- PrettyPhoto start -->
<link rel="stylesheet"
	href="layout/plugins/prettyphoto/css/prettyPhoto.css" type="text/css" />
<!-- PrettyPhoto end -->

<!-- jQuery tools start -->
<!-- jQuery tools end -->

<!-- Calendar start -->
<link rel="stylesheet" href="layout/plugins/calendar/calendar.css"
	type="text/css" />
<!-- Calendar end -->

<!-- ScrollTo start -->
<!-- ScrollTo end -->

<!-- MediaElements start -->
<link rel="stylesheet"
	href="layout/plugins/video-audio/mediaelementplayer.css" />
<!-- MediaElements end -->

<!-- FlexSlider start -->
<link rel="stylesheet" href="layout/plugins/flexslider/flexslider.css"
	type="text/css" />
<!-- FlexSlider end -->

<!-- iButtons start -->
<link rel="stylesheet"
	href="layout/plugins/ibuttons/css/jquery.ibutton.css" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/jquery-labelauty.css">
<style>
ul {
	list-style-type: none;
}

li {
	display: inline-block;
}

li {
	margin: 10px 0;
}

input.labelauty+label {
	font: 12px "Microsoft Yahei";
}
</style>




<!-- aJax刷新筛选 -->
<script src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/mainContent.js" charset="utf-8"></script>

<!-- aJax刷新筛选end -->

</head>

<body>
	<div class="wrapper sticky_footer">
		<!-- HEADER BEGIN -->
		<header>
		<div id="header">
			<jsp:include page="head.jsp" flush="true" />
		</div>
		</header>
		<!-- HEADER END -->

		<!-- 复选框 -->

		<div style="margin-top:40px">

			<div style="margin-left:70px">
				<div>
					<div style="width:120px;float:left;height:56px;">
						<h4 style="margin-top:13px;">出游天数：</h4>
					</div>

					<ul id="liDay" class="dowebok" style="margin-left:60px">
						<li><input type="radio" name="day" value="1"
							data-labelauty="1天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;">
						</li>
						<li><input type="radio" name="day" value="2"
							data-labelauty="2天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;">
						</li>
						<li><input type="radio" name="day" value="3"
							data-labelauty="3天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;">
						</li>
						<li><input type="radio" name="day" value="4"
							data-labelauty="4天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;">
						</li>
					</ul>

				</div>

				<div>
					<div style="width:120px;float:left;height:56px;">
						<h4 style="margin-top:13px;">酒店星级：</h4>
					</div>
					<ul id="liHotel" class="dowebok" style="margin-left:60px">
						<li><input type="radio" name="hotel" value="2"
							data-labelauty="经济型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></li>
						<li><input type="radio" name="hotel" value="3"
							data-labelauty="高档型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></li>
						<li><input type="radio" name="hotel" value="4"
							data-labelauty="舒适型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></li>
						<li><input type="radio" name="hotel" value="5"
							data-labelauty="豪华型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></li>
					</ul>
				</div>

				<div>
					<div style="width:120px;float:left;height:56px;">
						<h4 style="margin-top:13px;">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</h4>
					</div>
					<ul id="liPrice" class="dowebok" style="margin-left:60px">
						<li><input type="radio" name="sortPrice" value="HfromL"
							data-labelauty="从高到低↓  "></li>
						<li><input type="radio" name="sortPrice" value="LfromH"
							data-labelauty="从低到高↑  "></li>
					</ul>
				</div>

			</div>
		</div>

		<!-- CONTENT BEGIN -->
		<div id="content" class="right_sidebar" style="margin-top:-60px;">
			<div class="inner">
				<div class="general_content">
					<div class="main_content">
						<div class="separator" style="height:30px;"></div>
						<h2>搜索结果</h2>
						<div class="line_4" style="margin:0px 0px 18px;"></div>


						<!-- ---------------------------------------article_start -->
<!-- 						<article class="block_topic_post_feature" -->
<!-- 							style=" margin-bottom:40px"> -->
<!-- 						<div class="f_pic"> -->
<!-- 							<a href="news_post.html" class="general_pic_hover scale"><img -->
<!-- 								src="images/pic_business_big.jpg" alt="" /></a> -->
<!-- 						</div> -->
<!-- 						<div class="content"> -->
<!-- 							<p class="title"> -->
<!-- 								<a href="news_post.html"><font color="red">（跟团）</font>上海杜莎夫人蜡像馆。</a> -->
<!-- 							</p> -->
<!-- 							<div class="info"> -->
<!-- 								<div class="date"> -->
<!-- 									<p> -->
<!-- 										<b>出发点：</b><font color="red">广州</font>, <b>目的地:</b><font -->
<!-- 											color="red">上海</font> -->
<!-- 									</p> -->
<!-- 								</div> -->
<!-- 								<div class="r_part"> -->
<!-- 									<div class="category"> -->
<!-- 										<p> -->
<!-- 											<a href="#"><b>出游天数：</b><font color="red">3天</font></a> -->
<!-- 										</p> -->
<!-- 									</div> -->
<!-- 									<a href="#" class="views"><font color="red" size="+2">220</font></a> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="category"> -->
<!-- 								<p class="text">旅游景点：上海杜莎夫人蜡像</p> -->
<!-- 							</div> -->
<!-- 							<div class="category" -->
<!-- 								style="position:absolute;margin-left:250px;"> -->
<!-- 								<p class="text">交通工具：飞机</p> -->
<!-- 							</div> -->
<!-- 							<div class="category" -->
<!-- 								style="position:absolute;margin-left:450px;"> -->
<!-- 								<p class="text">是否直达：直达</p> -->
<!-- 							</div> -->
<!-- 							<br /> -->
<!-- 							<div class="category" style="position:absolute;margin-top:15px"> -->
<!-- 								<p class="text">酒店名称：维亚纳大酒店</p> -->
<!-- 							</div> -->
<!-- 							<div class="category" -->
<!-- 								style="position:absolute;margin-left:250px;margin-top:15px"> -->
<!-- 								<p class="text">代理公司：去哪儿网</p> -->
<!-- 							</div> -->
<!-- 							<br /> -->
<!-- 							<p class="text" -->
<!-- 								style="position:absolute;margin-top:39px;width:65%"> -->
<!-- 								<font color="#5a8ea1">数据来源：Many variations of passages of -->
<!-- 									available, but the majority have suffered alteration in some -->
<!-- 									form. Humour, or randomised words which don't look even -->
<!-- 									slightly believable. If you are going to use a passage of you -->
<!-- 									need to be sure.</font> -->
<!-- 							</p> -->

<!-- 						</div> -->
<!-- 						<div class="clearboth"></div> -->
<!-- 						</article> -->
						<!-- ---------------------------------------article_end -->
						
					<div id="mainContent"></div>
						
						<div class="line_2" style="margin:25px 0px 25px;"></div>
						<div class="block_pager">
							<a href="#" class="prev">Previous</a> <a href="#" class="next">Next</a>
							<div class="pages">
								<ul>
									<li class="current"><a href="#">1</a></li>
									<li><a href="#">2</a></li>
									<li><a href="#">3</a></li>
									<li><a href="#">4</a></li>
									<li><a href="#">5</a></li>
									<li><a href="#">6</a></li>
								</ul>
							</div>
							<div class="clearboth"></div>
						</div>
						<div class="separator" style="height:31px;"></div>
						<div class="separator" style="height:31px;"></div>
						<div class="clearboth"></div>
					</div>
				</div>
			</div>
			<!-- CONTENT END -->

			<!-- FOOTER BEGIN -->

			<!-- FOOTER END -->
		</div>
		<script>
			$(function() {
				$(':input').labelauty();
			});
		</script>
		<script src="js/jquery-labelauty.js"></script>
</body>
</html>
