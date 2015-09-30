<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmc:container template="student" subject="校园管理 - 学生编辑">
    <div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default" style="align-content: center;">
            <div class="panel-heading">学生信息: ${student.name}</div>
            <div class="panel-body">
                <form action="${ctx}/admin/school/student-detail-save" method="post">
                    <div class="form-inline">
                        <div class="form-group input-group">
                            <span class="input-group-addon">学生姓名：</span>
                            <input type="text" id="name" name="name" class="form-control" value="${student.name}" maxlength="7" />
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">所在学校：</span>
                            <input type="text" id="_school" name="_school" class="form-control"
                                   value="${school.name}" disabled="disabled" />
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">所在班级：</span>
                            <input type="text" id="_class" name="_class" class="form-control"
                                   value="${student.fmcClass.grade} / ${student.fmcClass.realClass}" disabled="disabled" />
                        </div>
                    </div>
                    <div class="form-inline">
                        <div class="form-group input-group">
                            <span class="input-group-addon">出生日期：</span>
                            <input type="text" id="birth" name="birthStr" class="form-control"
                                   value="<fmt:formatDate pattern="${datePattern}" value="${student.birth}" />" maxlength="10" />
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">性别：</span>
                            <select name="male" class="form-control">
                                <option value="false" ${not student.male ? 'selected' : ''}>女</option>
                                <option value="true" ${student.male ? 'selected' : ''}>男</option>
                            </select>
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">启用状态：</span>
                            <select name="available" class="form-control">
                                <option value="false" ${not student.available ? 'selected' : ''}>未启用</option>
                                <option value="true" ${student.available ? 'selected' : ''}>已启用</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-inline">
                        <div class="form-group input-group">
                            <span class="input-group-addon">手环编号：</span>
                            <input type="text" id="ringNumber" name="ringNumber" class="form-control" value="${student.ringNumber}" />
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">手环电话：</span>
                            <input type="text" id="ringPhone" name="ringPhone" class="form-control" value="${student.ringPhone}" />
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">创建时间：</span>
                            <input type="text" id="_creationDate" name="_creationDate" class="form-control"
                                   value="<fmt:formatDate pattern="${datetimePattern}" value="${student.creationDate}" />"
                                   disabled="disabled" />
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">最后更新：</span>
                            <input type="text" id="_lastUpdateDate" name="_lastUpdateDate" class="form-control"
                                   value="<fmt:formatDate pattern="${datetimePattern}" value="${student.lastUpdateDate}" />"
                                   disabled="disabled" />
                        </div>
                    </div>
                    <div class="form-inline">
                        <div class="form-group input-group">
                            <input type="hidden" name="id" value="${student.id gt 0 ? student.id : 0}" />
                            <input type="hidden" name="classId" value="${student.classId gt 0 ? student.id : classId}" />
                            <input type="submit" value="保存" class="btn btn-primary" />
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <c:if test="${student.id gt 0}">
            <c:forEach var="parent" items="${parents}" varStatus="index">
                <c:set var="rel" value="${parent.parentStudentRelationship}" />
                <div class="panel panel-default" style="align-content: center;">
                    <div class="panel-heading">家长信息: ${parent.name} - ${parent.phone}</div>
                    <div class="panel-body">
                        <form action="${ctx}/admin/school/student-parent-save" method="post">
                            <div class="form-inline">
                                <div class="form-group input-group">
                                    <span class="input-group-addon">家长姓名：</span>
                                    <input type="text" name="name" class="form-control" value="${parent.name}" maxlength="7" />
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon">手机号码：</span>
                                    <input type="text" name="phone" class="form-control" value="${parent.phone}"
                                           maxlength="11" />
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon">亲属关系：</span>
                                    <select name="parentStudentRelationship.relationship" class="form-control">
                                        <c:forEach var="relationshipString" items="${parentStudentRelationshipStrings}">
                                            <option ${relationshipString eq rel.relationship ? 'selected' : ''}
                                                    value="${relationshipString}">${relationshipString}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-inline">
                                <div class="form-group input-group">
                                    <span class="input-group-addon">账号状态：</span>
                                    <select name="available" class="form-control">
                                        <option value="false" ${not parent.available ? 'selected' : ''}>未启用</option>
                                        <option value="true" ${parent.available ? 'selected' : ''}>已启用</option>
                                    </select>
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon">审核状态：</span>
                                    <select name="parentStudentRelationship.approved" class="form-control">
                                        <option value="0" ${rel.approved eq 0 ? 'selected' : ''}>未通过</option>
                                        <option value="1" ${rel.approved eq 1 ? 'selected' : ''}>已通过</option>
                                    </select>
                                </div>
                                <c:if test="${rel.approved eq 1}">
                                    <div class="form-group input-group">
                                        <span class="input-group-addon">审核时间：</span>
                                        <input type="text" name="approvedDate" class="form-control"
                                               value="<fmt:formatDate pattern="${datetimePattern}" value="${rel.approvedDate}" />"
                                               disabled="disabled" />
                                    </div>
                                </c:if>
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon">地址：</span>
                                <input type="text" name="address.address" class="form-control"
                                       value="${parent.address.address}" maxlength="100" />
                                <input type="hidden" name="address.id" value="${parent.addressId gt 0? parent.addressId : 0}" />
                                <input type="hidden" name="address.cityId"
                                       value="${parent.address.cityId gt 0 ? parent.address.cityId : school.cityId}" />
                                <input type="hidden" name="address.provinceId"
                                       value="${parent.address.provinceId gt 0 ? parent.address.provinceId : school.provinceId}" />
                            </div>
                            <div class="form-inline">
                                <div class="form-group input-group">
                                    <span class="input-group-addon">访问设备：</span>
                                    <input type="text" name="_deviceType" class="form-control"
                                           value="${deviceTypeMap[parent.deviceType]}" disabled="disabled" />
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon">APP ID：</span>
                                    <input type="text" name="_appId" class="form-control" value="${parent.appId}"
                                           disabled="disabled" />
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon">Channel ID：</span>
                                    <input type="text" name="_channelId" class="form-control" value="${parent.channelId}"
                                           disabled="disabled" />
                                </div>
                            </div>
                            <div class="form-inline">
                                <div class="form-inline">
                                    <div class="form-group input-group">
                                        <span class="input-group-addon">创建时间：</span>
                                        <input type="text" name="_creationDate" class="form-control"
                                               value="<fmt:formatDate pattern="${datetimePattern}" value="${parent.creationDate}" />"
                                               disabled="disabled" />
                                    </div>
                                    <div class="form-group input-group">
                                        <span class="input-group-addon">最后登录时间：</span>
                                        <input type="text" name="_lastLoginDate" class="form-control"
                                               value="<fmt:formatDate pattern="${datetimePattern}" value="${parent.lastLoginDate}" />"
                                               disabled="disabled" />
                                    </div>
                                    <div class="form-group input-group">
                                        <span class="input-group-addon">最后更新时间：</span>
                                        <input type="text" name="_lastUpdateDate" class="form-control"
                                               value="<fmt:formatDate pattern="${datetimePattern}" value="${parent.lastUpdateDate}" />"
                                               disabled="disabled" />
                                    </div>
                                </div>
                            </div>
                            <div class="form-inline">
                                <div class="form-group input-group">
                                    <input type="hidden" name="id" value="${parent.id gt 0 ? parent.id : 0}" />
                                    <input type="hidden" name="parentStudentRelationship.studentId"
                                           value="${rel.studentId gt 0 ? rel.studentId : 0}" />
                                    <input type="submit" value="保存" class="btn btn-primary" />
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <!-- /.panel-heading -->
    </div>
    <!-- Bootstrap Core JavaScript -->
    <script src="${ctx}/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${ctx}/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${ctx}/dist/js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <c:if test="${student.id gt 0}">
        <!-- DataTables JavaScript -->
        <script src="${ctx}/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
        <script src="${ctx}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
        <link href="${ctx}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">
        <script>
            $(document).ready(function () {
                $('#table-parents').DataTable({
                    responsive: true
                });
            });
        </script>
    </c:if>
</fmc:container>

</html>