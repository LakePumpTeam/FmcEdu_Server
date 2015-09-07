<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${param.t eq 'school'}">
        <c:set var="subject" value="校园动态" />
        <c:set var="includeJspURL" value="includes/news-list-school.jsp" />
    </c:when>
    <c:when test="${param.t eq 'childedu'}">
        <c:set var="subject" value="育儿学堂" />
        <c:set var="includeJspURL" value="includes/news-list-parent-child-edu.jsp" />
    </c:when>
    <c:when test="${param.t eq 'ballot'}">
        <c:set var="subject" value="校园吧" />
        <c:set var="includeJspURL" value="includes/news-list-ballot.jsp" />
    </c:when>
</c:choose>
<c:set var="subheading" value="${newsType[param.mode]}" />

<fmc:container template="news" subject="${subject} - ${subheading}">
    <link href="${ctx}/css/umeditor.css" type="text/css" rel="stylesheet">
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
    <script src="${ctx}/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${ctx}/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="${ctx}/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="${ctx}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${ctx}/dist/js/sb-admin-2.js"></script>

    <link href="${ctx}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"
          rel="stylesheet">
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
        $(document).ready(function () {
            $('#table-news').DataTable({
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