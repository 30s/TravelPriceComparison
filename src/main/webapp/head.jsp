<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>head</title>
<link href="css/search/style1.css" rel='stylesheet' type='text/css'
	media="all" />
<!--theme-style-->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/bootstrap.css" rel="stylesheet" type="text/css"
	media="all">
<!--//theme-style-->

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="Journey Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript">
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
</script>
<!-- web-font -->
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css'>
<!-- web-font -->
<!-- js -->
<script src="js/jquery.min.js"></script>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<script type="application/x-javascript">
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
</script>
<!-- js -->
<!-- start-smoth-scrolling -->
<script type="text/javascript" src="js/move-top.js"></script>
<script type="text/javascript" src="js/easing.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function($) {
		$(".scroll").click(function(event) {
			event.preventDefault();
			$('html,body').animate({
				scrollTop : $(this.hash).offset().top
			}, 1000);
		});
	});
</script>
<!-- start-smoth-scrolling -->



    <link rel="stylesheet" href="css/citySelector.css">
    <style type="text/css">
        .cityinput{
            border-width: 1px;
            border-style: solid;
            border-color: #666 #ccc #ccc #666;
            height: 40px;
            line-height: 24px;
            width: 325.375px;
            font-size: 12px;
            padding-left: 2px;
/*             background: url(http://img02.taobaocdn.com/tps/i2/T1EPyLXm0hXXXXXXXX-200-100.png) no-repeat 150px 5px; */
            }
    </style>
</head>
<body>
	<!-- header -->
	<div class="header">
		<!-- container -->
		<div class="container">
			<div class="banner-top">
				<h3>快速查询，帮您选出最佳的旅游比价决策</h3>
				<div class="banner-bottom">
					<div class="bnr-one">
						<div class="bnr-left">
							<p>出发时间</p>
						</div>
						<div class="bnr-right">
								<input class="date" name="ST" id="datepicker" type="text"
									style="border-width: 1px;border-style: solid;border-color: #666 #ccc #ccc #666;"
									required />
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="bnr-one">
						<div class="bnr-left">
							<p>出发地</p>
						</div>
						<div >
							<input type="text" class="cityinput" id="citySelect" value="城市名">
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="bnr-one">
						<div class="bnr-left">
							<p>目的地</p>
						</div>
						<div class="bnr-right"></div>
							<input type="text" class="cityinput" id="citySelect1" value="城市名">
						<div class="clearfix"></div>
					</div>
					<div class="bnr-btn">
						<a href="#indexContent"><input id="check" type="submit" value="查询" /></a>

					</div>
				</div>
			</div>
			<link rel="stylesheet" href="css/jquery-ui.css" />
			<script src="js/jquery-ui.js"></script>
			<script>
				$(function() {
					$("#datepicker,#datepicker1").datepicker();
				});
			</script>
		</div>
		<!-- //container -->
	</div>
	<!-- //header -->

<script src="js/citySelector.js" type="text/javascript"></script>
<script type="text/javascript">
    var test=new Vcity.CitySelector({input:'citySelect'});
    var test2=new Vcity.CitySelector({input:'citySelect1'});
</script>
</body>
</html>
