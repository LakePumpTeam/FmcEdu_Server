<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmc:container template="interface" subject="Interface - ${param.i}">

    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Interfaces mock test service, module: ${param.i}
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div class="dataTable_wrapper">
                        <table class="table table-striped table-bordered table-hover" id="table-interface">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Function</th>
                                <th>BaseURL</th>
                                <th>Parameters</th>
                                <th width="12%">Request / Reset</th>
                                <th width="36%">Result</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:choose>
                                <c:when test="${param.i eq 'profile'}">
                                    <jsp:include page="includes/interface-profile.jsp" />
                                </c:when>
                            </c:choose>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.table-responsive -->
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>

    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

    <link href="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">
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

        .ipt_value {
            width: 390px !important;
        }
    </style>
</fmc:container>

</html>
