<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="/css/umeditor.css" type="text/css" rel="stylesheet">
<!-- jQuery -->
<script src="/bower_components/jquery/dist/jquery.min.js"></script>

<fmc:container template="teacher" subject="校园管理 - 教师编辑">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">教师信息</div>
                <div class="panel-body">
                    <form action="${ctx}/admin/school/teacher-detail-save" method="post">
                        <div class="form-group input-group">
                            <span class="input-group-addon">姓名：</span>
                            <input type="text" id="name" name="name" class="form-control" value="${teacher.name}" />
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">地址：</span>
                            <input type="text" id="address" name="address" class="form-control" value="${school.address}" />
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">地址：</span>
                            <input type="text" id="address" name="address" class="form-control" value="${school.address}" />
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">地址：</span>
                            <input type="text" id="address" name="address" class="form-control" value="${school.address}" />
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">地址：</span>
                            <input type="text" id="address" name="address" class="form-control" value="${school.address}" />
                        </div>
                        <div class="form-inline">
                            <div class="form-group input-group">
                                <input type="hidden" name="id" value="${not empty school.id ? school.id : 0}" />
                                <input type="submit" value="保存" class="btn btn-primary" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">班级列表</div>
                <div class="panel-body">
                    <div class="dataTable_wrapper">
                        <table class="table table-striped table-bordered table-hover" id="table-classes">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>班级</th>
                                <th>班主任老师</th>
                                <th>学生人数</th>
                                <th>启用</th>
                                <th>最后更新日期</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="fmcClass" items="${classes}" varStatus="index">
                                <tr align="right">
                                    <td>${fmcClass.id}</td>
                                    <td><a href="${ctx}/admin/school/class-detail?classId=${fmcClass.id}">
                                            ${fmcClass.grade}年级 / ${fmcClass.realClass}班</a></td>
                                    <td><a href="${ctx}/admin/school/teacher-detail?teacherId=${fmcClass.headTeacherId}">
                                            ${fmcClass.headTeacherName}</a></td>
                                    <td>${fmcClass.studentCount}</td>
                                    <td>${fmcClass.available ? '是' : '否'}</td>
                                    <td><fmt:formatDate pattern="${datetimePattern}" value="${fmcClass.lastUpdateDate}" /></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">教师列表</div>
                <div class="panel-body">
                    <div class="dataTable_wrapper">
                        <table class="table table-striped table-bordered table-hover" id="table-teachers">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>姓名</th>
                                <th>电话号码</th>
                                <th>性别</th>
                                <th>出生日期</th>
                                <th>出生日期</th>
                                <th>出生日期</th>
                                <th>最后更新日期</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="fmcClass" items="${classes}" varStatus="index">
                                <tr align="right">
                                    <td>${fmcClass.id}</td>
                                    <td><a href="${ctx}/admin/school/class-detail?classId=${fmcClass.id}">
                                            ${fmcClass.grade}年级 / ${fmcClass.realClass}班</a></td>
                                    <td><a href="${ctx}/admin/school/teacher-detail?teacherId=${fmcClass.headTeacherId}">
                                            ${fmcClass.headTeacherName}</a></td>
                                    <td>${fmcClass.studentCount}</td>
                                    <td>${fmcClass.available ? '是' : '否'}</td>
                                    <td><fmt:formatDate pattern="${datetimePattern}" value="${fmcClass.lastUpdateDate}" /></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
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
            $('#table-classes').DataTable({
                responsive: true
            });
        });
    </script>
</fmc:container>

</html>