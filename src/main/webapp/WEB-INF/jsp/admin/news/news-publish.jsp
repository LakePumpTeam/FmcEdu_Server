<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="/css/umeditor.css" type="text/css" rel="stylesheet">
<!-- jQuery -->
<script src="/bower_components/jquery/dist/jquery.min.js"></script>

<script type="text/javascript" charset="utf-8" src="/js/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/editor_api.js"></script>
<script type="text/javascript" src="/js/lang/zh-cn/zh-cn.js"></script>
<c:choose>
    <c:when test="${param.t eq 'school'}">
        <c:set var="subject" value="校园动态" />
    </c:when>
    <c:when test="${param.t eq 'childedu'}">
        <c:set var="subject" value="育儿学堂" />
    </c:when>
    <c:when test="${param.t eq 'ballot'}">
        <c:set var="subject" value="校园吧" />
    </c:when>
</c:choose>
<c:choose>
    <c:when test="${param.mode eq '2'}">
        <c:set var="includeJspURL" value="includes/news-school-activity.jsp" />
        <c:set var="subheading" value="活动发布" />
    </c:when>
    <c:when test="${param.mode eq '3'}">
        <c:set var="includeJspURL" value="includes/news-school-notification.jsp" />
        <c:set var="subheading" value="通知发布" />
    </c:when>
    <c:when test="${param.mode eq '4'}">
        <c:set var="includeJspURL" value="includes/news-school-news.jsp" />
        <c:set var="subheading" value="新闻发布" />
    </c:when>
    <c:when test="${param.mode eq '1'}">
        <c:set var="includeJspURL" value="includes/news-parent-child-edu.jsp" />
        <c:set var="subheading" value="发布" />
    </c:when>
    <c:when test="${param.mode eq '7'}">
        <c:set var="includeJspURL" value="includes/news-ballot.jsp" />
        <c:set var="subheading" value="发布" />
    </c:when>
</c:choose>

<fmc:container template="news" subject="${subject} - ${subheading}">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">&nbsp;</div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <jsp:include page="${includeJspURL}" />
                </div>
            </div>
        </div>
    </div>
    <!-- Bootstrap Core JavaScript -->
    <script src="/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/dist/js/sb-admin-2.js"></script>

    <link href="/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"
          rel="stylesheet">

    <style type="text/css">
        .ul-toolbar li {
            margin-bottom: 4px;
        }

        td.parameters table {
            float: right;
        }
    </style>
</fmc:container>

</html>