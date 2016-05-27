<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>一键爬虫</title>
	<style type="text/css">
	<!--
	body {
		margin-left: 0px;
		margin-top: 0px;
		margin-right: 0px;
		margin-bottom: 0px;
		background-color: #EEF2FB;
	}
	#addSubjectForm table  td{
		font-size:12px;
	}
	-->
	</style>
	  <script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/cityList.js"></script>
	<link href="${pageContext.request.contextPath}/admin/images/skin.css" rel="stylesheet" type="text/css">
  </head>
<body> 
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" valign="top" background="${pageContext.request.contextPath}/admin/images/mail_leftbg.gif"><img src="${pageContext.request.contextPath}/admin/images/left-top-right.gif" width="17" height="29" /></td>
    <td valign="top" background="${pageContext.request.contextPath}/admin/images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">爬取数据</div></td>
      </tr>
    </table></td>
    <td width="16" valign="top" background="${pageContext.request.contextPath}/admin/images/mail_rightbg.gif"><img src="${pageContext.request.contextPath}/admin/images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <tr>
    <td valign="middle" background="${pageContext.request.contextPath}/admin/images/mail_leftbg.gif">&nbsp;</td>
    <td valign="top" bgcolor="#F7F8F9">
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="53%" valign="top">&nbsp;</td>
        </tr>
      <tr>
        <td valign="middle"><span class="left_txt">
			<div id="addSubjectForm" align="center">


				<div style="overflow-x: auto; overflow-y: auto; height: 200px; width:1100px;">
					<table id="table" align="center" width="1050px" height="200px">
						<tbody>
						${demo}
						<tr>
							<th style="border-bottom:1px solid #000" width="190px">Permissio</th>
							<th style="border-bottom:1px solid #000" width="190px">Owner</th>
							<th style="border-bottom:1px solid #000" width="190px">Name</th>
							<th style="border-bottom:1px solid #000" width="190px">Action</th>
						</tr>
                     <s:iterator value="fileslist" var="f">
							<tr>
								<th style="border-bottom:1px solid #000" width="190px"><a href="#">${f.permission}</a></th>
								<th style="border-bottom:1px solid #000" width="190px"><a href="#">${f.owner}</a></th>
								<th style="border-bottom:1px solid #000" width="190px"><a href="#" onclick="setCity('<s:property value="#f.name"/>')">${f.name}</a></th>
								<th style="border-bottom:1px solid #000" width="190px"><a href="dfsDelAction?delfile=/${f.parent}/${f.name}" onclick="return confirm('是否确认删除文件！')">删除</a></th>

							</tr>
					 </s:iterator>
						</tbody>
					</table>
					  </div>
			<script>
				function setCity(name){
					var city = document.getElementById("city1");
					city.setAttribute("value",name);
				}

			</script>



				<form action="QuartzSpiderAction" method="post">
				<table >
				  <tr>
					<td  colspan="2"><FONT color="red"><s:actionerror/></FONT></td>
				  </tr>

					<tr>
					  <td  colspan="2">
						  请选择URL种子库:<input type="text" id="city1" name="file"/>设置爬虫周期-启动时间:<input name="hour"  type="text" size="5"/>  时  <input name="min" type="text" size="5"/>分,
						  线程数:<input tpye="text" name="thread" style="width:30px"/>

					  </td>

				  </tr>
				  <tr colspan="2">
				  	<td >
				  	  <s:submit value="开始爬虫" align="center" style="margin-top:-29px;margin-left:660px" onclick="return confirm('是否开始爬取数据？')"/>
						<b style="position: absolute;margin-top:36px;margin-left:-365px">端口号:</b><input id="port" type="text" style="width:100px;position: absolute;margin-top:36px;margin-left:-320px" />
						<a href="#iframeLabel"><input type="button" value="查看爬虫监控信息" style="position: absolute;margin-top:35px;margin-left:-210px" onclick="showIframe()"/></a>
						<script>
							function showIframe(){
								var iframe1 = document.getElementById("iframe1");
								var port = document.getElementById("port").value;
								if(port == ""){ alert("请输入端口号");}
								else {
									url = "http://"+port+"/ViewObjectRes//WebMagic%3Aname%3Ddujia.qunar.com";
									iframe1.setAttribute("src", url);
									iframe1.style.display = "block";
									iframe1.style.marginLeft = "8px";
								}
							}
						</script>
					</td>
				  </tr>
			</table>
			</form>	
			</div>
			</span>
		</td>
        </tr>
      
    </table></td>
    <td background="${pageContext.request.contextPath}/admin/images/mail_rightbg.gif">&nbsp;</td>
  </tr>
  <tr>
    <td valign="bottom" background="${pageContext.request.contextPath}/admin/images/mail_leftbg.gif"><img src="${pageContext.request.contextPath}/admin/images/buttom_left2.gif" width="17" height="17" /></td>
    <td background="${pageContext.request.contextPath}/admin/images/buttom_bgs.gif"><img src="${pageContext.request.contextPath}/admin/images/buttom_bgs.gif" width="17" height="17"></td>
    <td valign="bottom" background="${pageContext.request.contextPath}/admin/images/mail_rightbg.gif"><img src="${pageContext.request.contextPath}/admin/images/buttom_right2.gif" width="16" height="17" /></td>
  </tr>
</table>

 <a name="iframeLabel"></a><iFrame id="iframe1" style="display:none;" frameborder="1" width="1160" height="500" style="margin-left:8px;" scrolling="auto"/>
</body>
<script type="text/javascript">
	function show(type){
		var select = document.getElementById("abc");
		if(type=="all"){
			select.setAttribute("style", "display:none");
		}else if(type=="single"){
			select.setAttribute("style", "display:block");
		}
	}

</script>
</html>
