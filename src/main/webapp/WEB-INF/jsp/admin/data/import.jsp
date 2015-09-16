<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmc:container template="slide" subject="育儿学堂幻灯片">
    <div class="row">
        <!-- Slide Publish Area -->
        <div class="col-lg-12" id="div_slide_publish">
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">导入数据</div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <form action="${ctx}/admin/data/import" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label>选择excel数据文件：</label>
                            <input type="file" name="file" class="btn btn-primary">
                        </div>
                        <div class="form-group">
                            <div class="form-group">
                                <input type="submit" value="Import" class="btn btn-primary"/>
                            </div>
                        </div>
                    </form>
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
            $('#table-active-slide').DataTable({
                responsive: true,
                bPaginate: false,
                bFilter: false
            });
            $('#table-inactive-slide').DataTable({
                responsive: true,
                bPaginate: false,
                bFilter: false
            });
            $('#table-news').DataTable({
                responsive: true,
                bFilter: false
            });
        });
    </script>
</fmc:container>

</html>