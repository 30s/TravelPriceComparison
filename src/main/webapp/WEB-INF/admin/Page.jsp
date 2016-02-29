<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!-- 分页导航开始 -->
    
    第${page.currentPageNum}页/共${page.totalPage}页&nbsp;&nbsp;
    <a href="${page.uri}?num=1&ST=${page.records[0].ST}&SP=${page.records[0].SP}&EP=${page.records[0].EP}">首页</a>
    <a href="${page.uri}?num=${page.prePageNum}&ST=${page.records[0].ST}&SP=${page.records[0].SP}&EP=${page.records[0].EP}">上一页</a>
    &nbsp;&nbsp;
 
    <c:forEach begin="${page.startPage}" end="${page.endPage}" var="num">
   		<a href="${page.uri}?num=${num}&ST=${page.records[0].ST}&SP=${page.records[0].SP}&EP=${page.records[0].EP}">${num}</a>
    </c:forEach>
    
    &nbsp;&nbsp;
    <a href="${page.uri}?num=${page.nextPageNum}&ST=${page.records[0].ST}&SP=${page.records[0].SP}&EP=${page.records[0].EP}">下一页</a>
    <a href="${page.uri}?num=${page.totalPage}&ST=${page.records[0].ST}&SP=${page.records[0].SP}&EP=${page.records[0].EP}">尾页</a>
    <a href="oneKeyDeleteAction?place=${SP}" style="position:relative;left:350px;color: red" onclick="return confirm('是否删除所有数据？')">删除所有</a>

    
    <!-- 分页导航结束 -->