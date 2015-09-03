<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- jQuery -->
<script src="/bower_components/jquery/dist/jquery.min.js"></script>

<fmc:container template="teacher" subject="校园管理 - 教师编辑">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">教师信息</div>
                <div class="panel-body">
                    <form action="${ctx}/admin/school/teacher-detail-save" method="post">
                        <div class="form-inline">
                            <div class="form-group input-group">
                                <span class="input-group-addon">姓名：</span>
                                <input type="text" id="name" name="name" class="form-control" value="${teacher.name}" />
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon">所在学校：</span>
                                <c:choose>
                                    <c:when test="${teacher.id gt 0}">
                                        <input type="text" id="_school" name="_school" class="form-control"
                                               value="${teacher.school.name}" disabled="disabled" />
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" id="_school" name="_school" class="form-control"
                                               value="${locationMap[param.schoolId].name}" disabled="disabled" />
                                        <input type="hidden" name="school.id" value="${param.schoolId}" />
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon">手机：</span>
                                <input type="text" id="phone" name="phone" class="form-control" value="${teacher.phone}" />
                            </div>
                        </div>
                        <div class="form-inline">
                            <div class="form-group input-group">
                                <span class="input-group-addon">科目：</span>
                                <input type="text" id="address" name="course" class="form-control" value="${teacher.course}" />
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon">出生日期：</span>
                                <input type="text" id="birth" name="birthStr" class="form-control"
                                       value="<fmt:formatDate pattern="${datePattern}" value="${teacher.birth}" />" />
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon">性别：</span>
                                <select name="male" class="form-control">
                                    <option value="false" ${not teacher.male ? 'selected' : ''}>女</option>
                                    <option value="true" ${teacher.male ? 'selected' : ''}>男</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">简介：</span>
                            <input type="text" id="resume" name="resume" class="form-control" value="${teacher.resume}" />
                        </div>
                        <div class="form-inline">
                            <div class="form-group input-group">
                                <span class="input-group-addon">状态：</span>
                                <input type="text" id="_initialized" name="_initialized" class="form-control"
                                       value="${teacher.initialized ? '已启用' : '未启用'}" disabled="disabled" />
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon">设备：</span>
                                <input type="text" id="_deviceType" name="_deviceType" class="form-control"
                                       value="${deviceTypeMap[teacher.deviceType]}" disabled="disabled" />
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon">app id：</span>
                                <input type="text" id="_appId" name="_appId" class="form-control" value="${teacher.appId}"
                                       disabled="disabled" />
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon">channel id：</span>
                                <input type="text" id="_channelId" name="_channelId" class="form-control" value="${teacher.channelId}"
                                       disabled="disabled" />
                            </div>
                        </div>
                        <div class="form-inline">
                            <div class="form-group input-group">
                                <input type="hidden" name="id" value="${teacher.id gt 0 ? teacher.id : 0}" />
                                    <%-- default availabel is true--%>
                                <input type="hidden" name="available" value="${teacher.id gt 0 ? teacher.available : true}" />
                                    <%-- default initialized is false--%>
                                <input type="hidden" name="initialized" value="${teacher.id gt 0 ? teacher.initialized : false}" />
                                <input type="submit" value="保存" class="btn btn-primary" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <c:if test="${teacher.id gt 0}">
                <div class="panel panel-default" style="align-content: center;">
                    <div class="panel-heading">班级列表</div>
                    <div class="panel-body">
                        <div class="dataTable_wrapper">
                            <table class="table table-striped table-bordered table-hover" id="table-classes">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>学校</th>
                                    <th>班级</th>
                                    <th>学生人数</th>
                                    <th>启用</th>
                                    <th>最后更新</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="relationship" items="${relationships}" varStatus="index">
                                    <tr align="right">
                                        <td>${relationship.classId}</td>
                                        <td>${teacher.school.name}</td>
                                        <td><a href="${ctx}/admin/school/class-detail?classId=${relationship.classId}">
                                                ${relationship.fmcClass.grade}年级 / ${relationship.fmcClass.realClass}班</a></td>
                                        <td>${relationship.fmcClass.studentCount}</td>
                                        <td>${relationship.fmcClass.available ? '是' : '否'}</td>
                                        <td><fmt:formatDate pattern="${datetimePattern}"
                                                            value="${relationship.fmcClass.lastUpdateDate}" /></td>
                                        111
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
    <script src="/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/dist/js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <c:if test="${teacher.id gt 0}">
        <!-- DataTables JavaScript -->
        <script src="/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
        <script src="/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
        <link href="/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">
        <script>
            $(document).ready(function () {
                $('#table-classes').DataTable({
                    responsive: true
                });
            });
        </script>
    </c:if>
</fmc:container>

</html>