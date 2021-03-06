<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        <c:set var="includeJspURL" value="includes/news-publish-school.jsp" />
        <c:set var="subheading" value="活动" />
    </c:when>
    <c:when test="${param.mode eq '3'}">
        <c:set var="includeJspURL" value="includes/news-publish-school.jsp" />
        <c:set var="subheading" value="通知" />
    </c:when>
    <c:when test="${param.mode eq '4'}">
        <c:set var="includeJspURL" value="includes/news-publish-school.jsp" />
        <c:set var="subheading" value="新闻" />
    </c:when>
    <c:when test="${param.mode eq '1'}">
        <c:set var="includeJspURL" value="includes/news-publish-school.jsp" />
    </c:when>
    <c:when test="${param.mode eq '7'}">
        <c:set var="includeJspURL" value="includes/news-publish-ballot.jsp" />
    </c:when>
</c:choose>

<fmc:container template="news" subject="${subject} - ${subheading}发布">
    <link href="${ctx}/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="${ctx}/js/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctx}/js/editor_api.js"></script>
    <script type="text/javascript" src="${ctx}/js/lang/zh-cn/zh-cn.js"></script>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">&nbsp;</div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div id="news_content">
                        <form action="${ctx}/admin/news/do-news-publish" method="post" enctype="multipart/form-data" id="news_form">
                            <jsp:include page="../fragment/fragement-province-city-school.jsp" flush="true">
                                <jsp:param name="enableSchool" value="true" />
                            </jsp:include>
                            <jsp:include page="${includeJspURL}" flush="true">
                                <jsp:param name="subheading" value="${subheading}" />
                            </jsp:include>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Bootstrap Core JavaScript -->
    <script src="${ctx}/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${ctx}/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${ctx}/dist/js/sb-admin-2.js"></script>

    <link href="${ctx}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"
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