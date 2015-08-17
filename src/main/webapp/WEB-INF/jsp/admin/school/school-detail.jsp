<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="/css/umeditor.css" type="text/css" rel="stylesheet">
<!-- jQuery -->
<script src="/bower_components/jquery/dist/jquery.min.js"></script>

<fmc:container template="news" subject="区域 - 学校编辑">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">&nbsp;</div>
                <div class="panel-body">
                    <form action="${ctx}/admin/school/school-detail-save" method="post">
                        <div class="form-group input-group">
                            <span class="input-group-addon">学校名称：</span>
                            <input type="text" id="name" name="name" class="form-control" value="${school.name}" />
                        </div>
                        <jsp:include page="../fragment/fragement-province-city-school.jsp" flush="true">
                            <jsp:param name="provinceId" value="${school.provinceId}" />
                            <jsp:param name="cityId" value="${school.cityId}" />
                        </jsp:include>
                        <div class="form-group input-group">
                            <span class="input-group-addon">地址：</span>
                            <input type="text" id="address" name="address" class="form-control" value="${school.address}" />
                            <input type="hidden" name="addressId" value="${not empty school.addressId ? school.addressId : 0}" />
                        </div>
                        <div class="form-inline">
                            <div class="form-group input-group">
                                <input type="hidden" name="id" value="${not empty school.id ? school.id : 0}" />
                                <input type="submit" value="保存" class="btn btn-primary" />
                            </div>
                            <c:if test="${not empty param.back}">
                                <div class="form-group input-group">
                                    <input type="button" value="返回" class="btn btn-primary" id="btn_return" data-url="${back}" />
                                    <input type="hidden" name="back" value="${back}" />
                                </div>
                            </c:if>
                        </div>
                    </form>
                </div>
            </div>
            <!-- /.panel-heading -->
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
            $("#btn_return").on('click', document, function (e) {
                window.location.href = $(e.currentTarget).data("url");
            });
        });
    </script>
</fmc:container>

</html>