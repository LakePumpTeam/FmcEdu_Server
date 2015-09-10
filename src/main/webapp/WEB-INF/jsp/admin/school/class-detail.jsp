<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmc:container template="teacher" subject="校园管理 - 班级编辑">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">班级信息</div>
                <div class="panel-body">
                    <form action="${ctx}/admin/school/class-detail-save" method="post">
                        <div class="form-inline">
                            <div class="form-group input-group">
                                <span class="input-group-addon">学校：</span>
                                <c:choose>
                                    <c:when test="${fmcClass.id gt 0}">
                                        <input type="text" id="_school" name="_school" class="form-control"
                                               value="${fmcClass.schoolName}" disabled="disabled" />
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" id="_school" name="_school" class="form-control"
                                               value="${locationMap[param.schoolId].name}" disabled="disabled" />
                                        <input type="hidden" name="school.id" value="${param.schoolId}" />
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon">年级：</span>
                                <input type="text" id="name" name="grade" class="form-control" value="${fmcClass.grade}" maxlength="7" />
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon">班级：</span>
                                <input type="text" id="phone" name="realClass" class="form-control" value="${fmcClass.realClass}"
                                       maxlength="11" />
                            </div>
                        </div>
                        <div class="form-inline">
                            <div class="form-group input-group">
                                <span class="input-group-addon">状态：</span>
                                <select name="available" class="form-control">
                                    <option value="false" ${not fmcClass.available ? 'selected' : ''}>未启用</option>
                                    <option value="true" ${fmcClass.available ? 'selected' : ''}>已启用</option>
                                </select>
                            </div>
                            <c:if test="${param.classId gt 0}">
                                <div class="form-group input-group">
                                    <span class="input-group-addon">班主任：</span>
                                    <select name="headTeacherId" class="form-control">
                                        <c:forEach var="rel" items="${relationships}" varStatus="index">
                                            <option value="${rel.teacherId}" ${fmcClass.headTeacherId eq rel.teacherId ? 'selected' : ''}>${rel.teacher.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </c:if>
                        </div>
                        <div class="form-inline">
                            <div class="form-group input-group">
                                <input type="hidden" name="id" value="${fmcClass.id gt 0 ? fmcClass.id : 0}" />
                                <input type="submit" value="保存" class="btn btn-primary" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <c:if test="${param.classId gt 0}">
                <div class="panel panel-default" style="align-content: center;">
                    <div class="panel-heading">科任教师列表</div>
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
                                    <th>教师启用</th>
                                    <th>关系启用</th>
                                    <th>最后登录</th>
                                    <th>最后更新</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="rel" items="${relationships}" varStatus="index">
                                    <tr align="right">
                                        <td>${rel.teacher.id}</td>
                                        <td><a href="${ctx}/admin/school/teacher-detail?teacherId=${rel.teacher.id}">${rel.teacher.name}</a>
                                        </td>
                                        <td>${rel.teacher.phone}</td>
                                        <td>${rel.teacher.male ? '男' : '女'}</td>
                                        <td><fmt:formatDate pattern="${datePattern}" value="${rel.teacher.birth}" /></td>
                                        <td>${rel.headTeacher ? '是' : '否'}</td>
                                        <td>${rel.subTitle}</td>
                                        <td>${rel.teacher.initialized ? '已初始化' : '未初始化'}</td>
                                        <td>${rel.teacher.available ? '已启用' : '未启用'}</td>
                                        <td>${rel.available ? '已启用' : '未启用'}</td>
                                        <td><fmt:formatDate pattern="${datetimePattern}" value="${rel.teacher.lastLoginDate}" /></td>
                                        <td><fmt:formatDate pattern="${datetimePattern}" value="${rel.teacher.lastUpdateDate}" /></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default" style="align-content: center;">
                    <div class="panel-heading">学生列表</div>
                    <div class="panel-body">
                        <div class="dataTable_wrapper">
                            <table class="table table-striped table-bordered table-hover" id="table-students">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>出生日期</th>
                                    <th>手环编号</th>
                                    <th>手环电话号码</th>
                                    <th>已启用</th>
                                    <th>最后更新</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="student" items="${students}" varStatus="index">
                                    <tr align="right">
                                        <td>${student.id}</td>
                                        <td><a href="${ctx}/admin/school/student-detail?studentId=${student.id}">${student.name}</a></td>
                                        <td>${student.male ? '男' : '女'}</td>
                                        <td><fmt:formatDate pattern="${datePattern}" value="${student.birth}" /></td>
                                        <td>${student.ringNumber}</td>
                                        <td>${student.ringPhone}</td>
                                        <td>${rel.available ? '已启用' : '未启用'}</td>
                                        <td><fmt:formatDate pattern="${datetimePattern}" value="${student.lastUpdateDate}" /></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
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
    <c:if test="${fmcClass.id gt 0}">
        <!-- DataTables JavaScript -->
        <script src="${ctx}/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
        <script src="${ctx}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
        <link href="${ctx}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">
        <script>
            $(document).ready(function () {
                $('#table-teachers').DataTable({
                    responsive: true
                });
                $('#table-students').DataTable({
                    responsive: true
                });
            });
        </script>
    </c:if>
</fmc:container>

</html>