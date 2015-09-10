<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmc:container template="slide" subject="育儿学堂幻灯片">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" style="align-content: center;">
                <c:choose>
                    <c:when test="${param.success eq 0}">
                        <div class="alert alert-danger">
                            <b>错误：</b>保存操作失败。
                        </div>
                    </c:when>
                    <c:when test="${param.success eq 1}">
                        <div class="alert alert-success">
                            <b>成功：</b>保存操作成功。
                        </div>
                    </c:when>
                </c:choose>
                <c:if test="${fn:length(activeSlides) gt slideCount}">
                    <div class="alert alert-danger">
                        <b>错误：</b>当前启用的幻灯片数量<b>(${fn:length(activeSlides)})</b>已经超过允许设置的最大数量<b>(${slideCount})</b>。
                    </div>
                </c:if>

                <div class="panel-heading">
                    育儿学堂幻灯片列表
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <form action="/admin/saveSlides" method="post">
                        <p class="text-success">当前已启用的育儿学堂幻灯片：
                            <c:if test="${fn:length(activeSlides) eq 0}">
                                <span class="text-warning">暂时没有记录。</span>
                            </c:if>
                        </p>
                        <c:if test="${fn:length(activeSlides) gt 0}">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="table-active-slide">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>预览</th>
                                        <th>主题</th>
                                        <th>链接到文章</th>
                                        <th>显示顺序</th>
                                        <th>是否启用</th>
                                        <th>创建日期</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="slide" items="${activeSlides}" varStatus="index">
                                        <tr>
                                            <td>${slide.id}
                                                <input type="hidden" name="ids" value="${slide.id}" />
                                            </td>
                                            <td>${slide.subject}</td>
                                            <td>《${slide.news.subject}》</td>
                                            <td><img src="${ctx}/slide/${slide.imgPath}${slide.imgName}" height="80px" /></td>
                                            <td><select name="order" class="form-control" data-slide-count="${slideCount}">
                                                <c:forEach var="i" begin="1" end="${slideCount}">
                                                    <option value="${i}" ${slide.order eq i ? 'selected' : ''}>${i}</option>
                                                </c:forEach>
                                            </select></td>
                                            <td><select name="available" class="form-control">
                                                <option ${slide.available ? 'selected' : ''} value="true">启用</option>
                                                <option ${not slide.available ? 'selected' : ''} value="false">禁用</option>
                                            </select></td>
                                            <td><fmt:formatDate pattern="${datetimePattern}" value="${slide.creationDate}" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>

                        <hr />
                        <p class="text-danger">当前未启用的育儿学堂幻灯片：
                            <c:if test="${fn:length(inactiveSlides) eq 0}">
                                <span class="text-warning">暂时没有记录。</span>
                            </c:if>
                        </p>
                        <c:if test="${fn:length(inactiveSlides) gt 0}">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="table-inactive-slide">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>预览</th>
                                        <th>主题</th>
                                        <th>链接到文章</th>
                                        <th>显示顺序</th>
                                        <th>是否启用</th>
                                        <th>创建日期</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="slide" items="${inactiveSlides}" varStatus="index">
                                        <tr>
                                            <td>${slide.id}
                                                <input type="hidden" name="ids" value="${slide.id}" />
                                            </td>
                                            <td><img src="${ctx}/slide/${slide.imgPath}${slide.imgName}" height="80px" /></td>
                                            <td>${slide.subject}</td>
                                            <td>《${slide.news.subject}》</td>
                                            <td><select name="order" class="form-control" data-slide-count="${slideCount}" disabled>
                                                <c:forEach var="i" begin="1" end="${slideCount}">
                                                    <option value="${i}" ${slide.order eq i ? 'selected' : ''}>${i}</option>
                                                </c:forEach>
                                            </select>
                                                <input type="hidden" name="order" value="${slide.order}" />
                                            </td>
                                            <td><select name="available" class="form-control">
                                                <option ${slide.available ? 'selected' : ''} value="true">启用</option>
                                                <option ${not slide.available ? 'selected' : ''} value="false">禁用</option>
                                            </select></td>
                                            <td><fmt:formatDate pattern="${datetimePattern}" value="${slide.creationDate}" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>
                        <br />
                        <c:if test="${fn:length(activeSlides) gt 0 or fn:length(inactiveSlides) gt 0}">
                            <div class="form-group">
                                <input type="submit" class="btn btn-primary" value="保存" />
                            </div>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>

        <!-- Slide Publish Area -->
        <div class="col-lg-12" id="div_slide_publish">
            <div class="panel panel-default" style="align-content: center;">
                <div class="panel-heading">创建新的育儿学堂幻灯片</div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <form action="/admin/createSlide" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label>主题：</label>
                            <input type="text" name="subject" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>请选择图片：</label>
                            <input type="file" name="image" class="btn btn-primary">
                        </div>
                        <div class="form-group">
                            <label>请选择链接的文章：</label>

                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="table-news">
                                    <thead>
                                    <tr>
                                        <th>选择</th>
                                        <th>#</th>
                                        <th>标题</th>
                                        <th>内容</th>
                                        <th>赞</th>
                                        <th>创建时间</th>
                                        <th>发布时间</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="news" items="${newsList}">
                                        <tr>
                                            <td><input type="radio" name="newsId" value="${news.id}" /></td>
                                            <td>${news.id}</td>
                                            <td>${news.subject}</td>
                                            <td>${fn:substring(news.content, 0, 25)}...</td>
                                            <td>${news.like}</td>
                                            <td><fmt:formatDate pattern="${datetimePattern}" value="${news.creationDate}" /></td>
                                            <td><fmt:formatDate pattern="${datetimePattern}" value="${news.publishDate}" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>是否启用：</label>&nbsp;
                            <label class="radio-inline text-danger">
                                <input type="radio" name="available" value="false" checked="checked"> 禁用
                            </label>
                            <label class="radio-inline text-success">
                                <input type="radio" name="available" value="true"> 启用
                            </label>
                        </div>
                        <div class="form-group">
                            <div class="form-group">
                                <input type="submit" value="创建" class="btn btn-primary" />
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