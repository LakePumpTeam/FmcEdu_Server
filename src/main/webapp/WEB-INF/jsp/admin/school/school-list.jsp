<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmc:container template="school" subject="校园管理 - 学校管理">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">&nbsp;</div>
                <div class="panel-body">
                    <form action="${ctx}/admin/school/school-list" method="get" class="form-inline">
                        <jsp:include page="../fragment/fragement-province-city-school.jsp" flush="true" />
                        <div class="form-group input-group">
                            <input type="submit" value="查找" class="btn btn-primary" />
                        </div>
                        <div class="form-group input-group">
                            <input type="button" value="新增" class="btn btn-primary" id="btn_create" />
                        </div>
                        <input type="hidden" name="back" value="${originalURI}" />
                    </form>

                    <div class="dataTable_wrapper">
                        <table class="table table-striped table-bordered table-hover" id="table-school">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>学校名称</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="school" items="${schools}" varStatus="index">
                                <tr>
                                    <td align="right">${index.count} </td>
                                    <td>
                                        <a href="${ctx}/admin/school/school-detail?schoolId=${school.schoolId}">${school.schoolName}</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
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
            $('#table-school').DataTable({
                responsive: true
            });

            $("#btn_create").on('click', document, function (e) {
                window.location.href = "${ctx}/admin/school/school-detail";
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