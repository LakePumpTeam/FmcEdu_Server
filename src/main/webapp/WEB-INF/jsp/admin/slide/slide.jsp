<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmc:container template="news" subject="Slide">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">育儿学堂幻灯片</div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <p class="text-success">当前已启用的育儿学堂幻灯片：</p>

                    <div class="dataTable_wrapper">
                        <table class="table table-striped table-bordered table-hover" id="table-active-slide">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>预览</th>
                                <th>链接到文章</th>
                                <th>显示顺序</th>
                                <th>是否启用</th>
                                <th>创建日期</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="slide" items="${activeSlides}" varStatus="index">
                                <tr>
                                    <td>${slide.id}</td>
                                    <td><img src="${ctx}${slide.imgPath}${slide.imgName}" height="80px" /></td>
                                    <td>《${slide.news.subject}》</td>
                                    <td><select name="order" class="form-control" data-slide-count="${slideCount}">
                                        <c:forEach var="i" begin="1" end="${slideCount}">
                                            <option value="${i}" ${slide.order eq i ? 'selected' : ''}>${i}</option>
                                        </c:forEach>
                                    </select></td>
                                    <td><select name="available" class="form-control">
                                        <option ${slide.available ? 'selected' : ''} value="true">启用</option>
                                        <option ${not slide.available ? 'selected' : ''} value="false">禁用</option>
                                    </select></td>
                                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${slide.creationDate}" /></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <hr />
                    <p class="text-danger">当前未启用的育儿学堂幻灯片：</p>

                    <div class="dataTable_wrapper">
                        <table class="table table-striped table-bordered table-hover" id="table-inactive-slide">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>预览</th>
                                <th>链接到文章</th>
                                <th>显示顺序</th>
                                <th>是否启用</th>
                                <th>创建日期</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="slide" items="${inactiveSlides}" varStatus="index">
                                <tr>
                                    <td>${slide.id}</td>
                                    <td><img src="${ctx}${slide.imgPath}${slide.imgName}" height="80px" /></td>
                                    <td>《${slide.news.subject}》</td>
                                    <td><select name="order" class="form-control" data-slide-count="${slideCount}" disabled>
                                        <c:forEach var="i" begin="1" end="${slideCount}">
                                            <option value="${i}" ${slide.order eq i ? 'selected' : ''}>${i}</option>
                                        </c:forEach>
                                    </select></td>
                                    <td><select name="available" class="form-control">
                                        <option ${slide.available ? 'selected' : ''} value="true">启用</option>
                                        <option ${not slide.available ? 'selected' : ''} value="false">禁用</option>
                                    </select></td>
                                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${slide.creationDate}" /></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <br />

                    <div class="form-group">
                        <input type="submit" class="btn btn-primary" value="保存" />
                    </div>
                </div>
            </div>
        </div>

        <!-- Slide Publish Area -->
        <div class="col-lg-12" id="div_slide_publish">
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">育儿学堂幻灯片 - 发布</div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div class="form-group">
                        <label>请选择图片</label>
                        <input type="file" class="btn btn-primary">
                    </div>
                    <div class="form-group">
                        <div class="dataTable_wrapper">
                            <table class="table table-striped table-bordered table-hover" id="table-news">
                                <thead>
                                <tr>
                                    <th>选择</th>
                                    <th>#</th>
                                    <th>标题</th>
                                    <th>作者</th>
                                    <th>创建时间</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="news" items="${newsList}">
                                    <tr>
                                        <td><input type="radio" name="news_id" value="${news.id}" /></td>
                                        <td>${news.id}</td>
                                        <td>${news.subject}</td>
                                        <td>${news.author}</td>
                                        <td>${news.creationDate}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="button" value="创建" class="btn btn-primary" />
                    </div>
                </div>
            </div>
        </div>
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

    <link href="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"
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