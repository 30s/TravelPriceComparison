<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>The signin widget Website Template | Home :: w3layouts</title>
<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
<link href="css/style1.css" rel="stylesheet" type="text/css" media="all" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<!-- -->
<script>var __links = document.querySelectorAll('a');function __linkClick(e) { parent.window.postMessage(this.href, '*');} ;for (var i = 0, l = __links.length; i < l; i++) {if ( __links[i].getAttribute('data-t') == '_blank' ) { __links[i].addEventListener('click', __linkClick, false);}}</script>
<script src="js/jquery.min.js"></script>
<script>$(document).ready(function(c) {
	$('.alert-close').on('click', function(c){
		$('.message').fadeOut('slow', function(c){
	  		$('.message').remove();
		});
	});	
});
</script>
</head>
<body>
<!-- contact-form -->	
<div class="message warning">
<div class="contact-form"">
	<div class="logo">
		<h1>Sign In</h1>
	</div>	
<!--- form --->
<form class="form" action="loginAc" method="post" name="contact_form">
	<ul>
		<li>
			 <label><img src="images/contact.png" alt=""></label>
			 <s:textfield name="user" type="email" class="email" placeholder="info@163.com"></s:textfield>
<!-- 			 <input type="email" name="user" class="email" placeholder="info@163.com" required />		             -->
		 </li>
		 <li>
			 <label><img src="images/lock.png" alt=""></label>
			 <input type="Password" name="password" placeholder="Password" required />		         
		 </li>
		 <li class="style">
		     <input type="Submit" value="登录">
		 </li>
	</ul>	
	<div class="clear"></div>	   	
</form>
</div>
<div class="alert-close"></div>
</div>
<div class="clear"></div>
<!--- footer --->
<div class="footer">
	<p>Template by <a href="http://w3layouts.com">w3layouts</a></p>
</div>
</body>
</html>