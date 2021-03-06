<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmc:container template="school" subject="校园管理 - 学校编辑">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">学校信息</div>
                <div class="panel-body">
                    <form action="${ctx}/admin/school/school-detail-save" method="post">
                        <div class="form-group input-group">
                            <span class="input-group-addon">学校名称：</span>
                            <input type="text" id="name" name="name" class="form-control" value="${school.name}" maxlength="40" />
                        </div>
                        <jsp:include page="../fragment/fragement-province-city-school.jsp" flush="true">
                            <jsp:param name="provinceId" value="${school.provinceId}" />
                            <jsp:param name="cityId" value="${school.cityId}" />
                        </jsp:include>
                        <div class="form-group input-group">
                            <span class="input-group-addon">地址：</span>
                            <input type="text" id="address" name="address" class="form-control" value="${school.address}" maxlength="100" />
                            <input type="hidden" name="addressId" value="${school.addressId gt 0? school.addressId : 0}" />
                        </div>
                        <div class="form-inline">
                            <div class="form-group input-group">
                                <input type="hidden" name="id" value="${school.id gt 0 ? school.id : 0}" />
                                <input type="submit" value="保存" class="btn btn-primary" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <c:if test="${school.id gt 0}">
                <div class="panel panel-default" style="align-content: center;">
                    <div class="panel-heading">班级列表</div>
                    <div class="panel-body">
                        <div class="dataTable_wrapper">
                            <table class="table table-striped table-bordered table-hover" id="table-classes">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>年级 / 班级</th>
                                    <th>班主任老师</th>
                                    <th>学生人数</th>
                                    <th>班级状态</th>
                                    <th>最后更新</th>
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
                                        <td>${fmcClass.available ? '已启用' : '未启用'}</td>
                                        <td><fmt:formatDate pattern="${datetimePattern}" value="${fmcClass.lastUpdateDate}" /></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div>
                            <input type="button" value="新增班级" class="btn btn-primary" id="btn_create_class" />
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
                                    <th>班主任</th>
                                    <th>科目</th>
                                    <th>已初始化</th>
                                    <th>教师状态</th>
                                    <th>最后登录</th>
                                    <th>最后更新</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="teacher" items="${teachers}" varStatus="index">
                                    <tr align="right">
                                        <td>${teacher.id}</td>
                                        <td><a href="${ctx}/admin/school/teacher-detail?teacherId=${teacher.id}">${teacher.name}</a></td>
                                        <td>${teacher.phone}</td>
                                        <td>${teacher.male ? '男' : '女'}</td>
                                        <td><fmt:formatDate pattern="${datePattern}" value="${teacher.birth}" /></td>
                                        <td>${teacher.headTeacher ? '是' : '否'}</td>
                                        <td>${teacher.course}</td>
                                        <td>${teacher.initialized ? '已初始化' : '未初始化'}</td>
                                        <td>${teacher.available ? '已启用' : '未启用'}</td>
                                        <td><fmt:formatDate pattern="${datetimePattern}" value="${teacher.lastLoginDate}" /></td>
                                        <td><fmt:formatDate pattern="${datetimePattern}" value="${teacher.lastUpdateDate}" /></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div>
                            <input type="button" value="新增教师" class="btn btn-primary" id="btn_create_teacher" />
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
        <!-- /.panel-heading -->
    </div>
    <!-- Bootstrap Core JavaScript -->
    <script src="${ctx}/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${ctx}/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${ctx}/dist/js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <c:if test="${school.id gt 0}">
        <!-- DataTables JavaScript -->
        <script src="${ctx}/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
        <script src="${ctx}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
        <link href="${ctx}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">
        <script>
            $(document).ready(function () {
                $('#table-classes').DataTable({
                    responsive: true
                });
                $('#table-teachers').DataTable({
                    responsive: true
                });
                $("#btn_create_teacher").on('click', document, function (e) {
                    window.location.href = "${ctx}/admin/school/teacher-detail?schoolId=${school.id}";
                });
                $("#btn_create_class").on('click', document, function (e) {
                    window.location.href = "${ctx}/admin/school/class-detail?schoolId=${school.id}";
                });
            });
        </script>
    </c:if>
</fmc:container>

</html>