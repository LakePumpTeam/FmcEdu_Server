<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmc:container template="school" subject="系统管理">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">系统管理</div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-hover" id="table-config">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>说明</th>
                                <th>备注</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tr>
                                <td>1</td>
                                <td>清除并重新加载省份, 城市和默认城市的学校列表的缓存</td>
                                <td></td>
                                <td><a class="btn btn-primary" href="${ctx}/admin/sys/invalid-location-cache">清除省市缓存</a></td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>立刻将当期缓存的点赞数保存到数据库</td>
                                <td>当前未保存的点赞数:&nbsp;
                                    <span class="${cachedNewLike gt 0 ? 'text-success' : 'text-primary'}">${cachedNewLike}</span></td>
                                <td><a class="btn btn-primary" href="${ctx}/admin/sys/persist-news-like-cache">保存当前点赞数量</a></td>
                            </tr>
                            <form action="${ctx}/admin/sys/reset-log-level" method="POST">
                                <tr>
                                    <td>3</td>
                                    <td>设置日志级别</td>
                                    <td><select name="logLevel" class="form-control">
                                        <option value="10000" ${logLevel eq 10000 ? 'selected' : ''}>DEBUG</option>
                                        <option value="20000" ${logLevel eq 20000 ? 'selected' : ''}>INFO</option>
                                        <option value="30000" ${logLevel eq 30000 ? 'selected' : ''}>WARN</option>
                                        <option value="40000" ${logLevel eq 40000 ? 'selected' : ''}>ERROR</option>
                                    </select></td>
                                    <td><input type="submit" class="btn btn-primary" value="保存" /></td>
                                </tr>
                            </form>
                        </table>
                    </div>

                </div>
            </div>
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
    <script src="${ctx}/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="${ctx}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${ctx}/dist/js/sb-admin-2.js"></script>

    <link href="${ctx}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"
          rel="stylesheet">
    <script>
        $(document).ready(function () {

        });
    </script>
</fmc:container>

</html>