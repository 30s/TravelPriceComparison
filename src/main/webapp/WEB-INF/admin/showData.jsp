<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>旅游信息数据</title>
    <style type="text/css">
        <!--
        body {
            margin-left: 0px;
            margin-top: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
            background-color: #EEF2FB;
        }

        #manageSubject table td {
            font-size: 12px;
        }

        -->
    </style>
    <link href="${pageContext.request.contextPath}/admin/images/skin.css" rel="stylesheet" type="text/css">
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td width="17" valign="top" background="${pageContext.request.contextPath}/admin/images/mail_leftbg.gif"><img
                src="${pageContext.request.contextPath}/admin/images/left-top-right.gif" width="17" height="29"/></td>
        <td valign="top" background="${pageContext.request.contextPath}/admin/images/content-bg.gif">
            <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
                <tr>
                    <td height="31">
                        <div class="titlebt">Hbase</div>
                    </td>
                </tr>
            </table>
        </td>
        <td width="16" valign="top" background="${pageContext.request.contextPath}/admin/images/mail_rightbg.gif"><img
                src="${pageContext.request.contextPath}/admin/images/nav-right-bg.gif" width="16" height="29"/></td>
    </tr>
    <tr>
        <td valign="middle" background="${pageContext.request.contextPath}/admin/images/mail_leftbg.gif">&nbsp;</td>
        <td valign="top" bgcolor="#F7F8F9">
            <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="53%" valign="top">
                        <form action="showDataAction">
                            <input list="cars" name="SP" style="position: relative;left:825px">
                            <datalist id="cars">
                                <option value="北京">
                                <option value="四川">
                                <option value="上海">
                                <option value="广州">
                                <option value="澳门">
                            </datalist>
                            <input style="position: relative;left:830px" type="submit" value="搜索地区"/>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td valign="middle"><span class="left_txt">
			<div id="manageSubject" align="center">
                <table width="95%" cellspacing="10">
                    <tr align="center">
                        <td>出发地</td>
                        <td>目的地</td>
                        <td>来源网站</td>
                        <td>清空</td>
                    </tr>

                    <s:iterator value="page.records" var="record">
                        <tr align="center">
                            <td>${record.SP}</td>
                            <td>${record.EP}</td>
                            <td>${record.SUPPLIER}</td>
                            <td><a href="#">查看</a></td>
                            <td><a href="deleteAction.action" onclick="return confirm('是否清空各个地区的所有数据？')">清空</a></td>
                        </tr>
                    </s:iterator>

                    <tr>
                        <td colspan="10" align="center">
                            <center>
                                <%@include file="Page.jsp" %>
                            </center>
                        </td>
                    </tr>
                </table>
            </div>
                    </td>
                </tr>

            </table>
        </td>
        <td background="${pageContext.request.contextPath}/admin/images/mail_rightbg.gif">&nbsp;</td>
    </tr>
    <tr>
        <td valign="bottom" background="${pageContext.request.contextPath}/admin/images/mail_leftbg.gif"><img
                src="${pageContext.request.contextPath}/admin/images/buttom_left2.gif" width="17" height="17"/></td>
        <td background="${pageContext.request.contextPath}/admin/images/buttom_bgs.gif"><img
                src="${pageContext.request.contextPath}/admin/images/buttom_bgs.gif" width="17" height="17"></td>
        <td valign="bottom" background="${pageContext.request.contextPath}/admin/images/mail_rightbg.gif"><img
                src="${pageContext.request.contextPath}/admin/images/buttom_right2.gif" width="16" height="17"/></td>
    </tr>
</table>
</body>
</html>
