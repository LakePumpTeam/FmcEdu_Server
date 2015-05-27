<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="../../../../css/umeditor.css" type="text/css" rel="stylesheet">
<!-- jQuery -->
<script src="../bower_components/jquery/dist/jquery.min.js"></script>

<script type="text/javascript" charset="utf-8" src="../../../../js/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../../../../js/editor_api.js"></script>
<script type="text/javascript" src="../../../../js/lang/zh-cn/zh-cn.js"></script>

<c:choose>
    <c:when test="${param.i eq 'sdat'}">
        <c:set var="includeJspURL" value="includes/news-school-activity.jsp" />
        <c:set var="subheading" value="活动" />
    </c:when>
    <c:when test="${param.i eq 'sdnf'}">
        <c:set var="includeJspURL" value="includes/news-school-notification.jsp" />
        <c:set var="subheading" value="通知" />
    </c:when>
    <c:when test="${param.i eq 'sdnw'}">
        <c:set var="includeJspURL" value="includes/news-school-news.jsp" />
        <c:set var="subheading" value="新闻" />
    </c:when>
</c:choose>
<fmc:container template="news" subject="校园动态 - ${subheading}">

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" style="align-content: center;">
                <jsp:include page="${includeJspURL}" />
            </div>
        </div>
    </div>
    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

    <link href="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"
          rel="stylesheet">
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
        $(document).ready(function () {
            $('#table-interface').DataTable({
                responsive: true
            });
        });
    </script>

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