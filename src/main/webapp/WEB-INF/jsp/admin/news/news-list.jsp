<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="/css/umeditor.css" type="text/css" rel="stylesheet">
<!-- jQuery -->
<script src="/bower_components/jquery/dist/jquery.min.js"></script>

<c:choose>
    <c:when test="${param.t eq 'school'}">
        <c:set var="subject" value="校园动态" />
        <c:set var="includeJspURL" value="includes/news-list-school.jsp" />
    </c:when>
    <c:when test="${param.t eq 'childedu'}">
        <c:set var="subject" value="育儿学堂" />
        <c:set var="includeJspURL" value="includes/news-list-parent-child-edu.jsp" />
    </c:when>
</c:choose>
<c:set var="subheading" value="${newsType[param.mode]}" />

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

    <!-- DataTables JavaScript -->
    <script src="/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/dist/js/sb-admin-2.js"></script>

    <link href="/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"
          rel="stylesheet">
    <!-- Page-Level Demo Scripts - Tables - Use for refer ence -->
    <script>
        $(document).ready(function () {
            $('#table-news').DataTable({
                responsive: true
            });

            $("#slt-province").on("change", provinceChange);
            $("#slt-cities").on("change", cityChange);
        });

        function provinceChange(e) {
            $.getJSON(ctx + "/admin/cities?provinceId=" + $("#slt-province").val(), function (data) {
                var cityOptions = [];
                $.each(data, function (i, e) {
                    cityOptions.push(new Option(e.name, e.cityId));
                });
                $(cityOptions[0]).prop("selected", true);
                var citySelect = $("#slt-cities");
                citySelect.find("option").remove();
                citySelect.append(cityOptions);
                // even if the event is not incorrect
                cityChange(e);
            });
        }

        function cityChange(e) {
            $.getJSON(ctx + "/admin/schools?cityId=" + $("#slt-cities").val(), function (data) {
                var schoolSelect = $("#slt-schools");
                schoolSelect.find("option").remove();
                schoolSelect.append(new Option("--", ""));
                if (data.length !== undefined && data.length > 0) {
                    var schoolOptions = [];
                    $.each(data, function (i, e) {
                        schoolOptions.push(new Option(e.schoolName, e.schoolId));
                    });
                    schoolSelect.append(schoolOptions);
                }
            });
        }
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