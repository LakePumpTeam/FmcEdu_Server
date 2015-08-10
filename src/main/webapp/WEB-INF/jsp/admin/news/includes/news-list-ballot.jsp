<c:set var="provinceId" value="${param.provinceId}" />
<form action="/admin/news/news-list" method="get" class="form-inline">
    <input type="hidden" name="t" value="${param.t}" />
    <input type="hidden" name="mode" value="${param.mode}" />

    <jsp:include page="fragment/fragement-province-city-school.jsp" flush="true" />

    <div class="form-group input-group">
        <input type="submit" value="查找" class="btn btn-primary" />
    </div>
</form>
<div class="dataTable_wrapper">
    <table class="table table-striped table-bordered table-hover" id="table-news">
        <thead>
        <tr>
            <th>#</th>
            <th>标题</th>
            <%--<th>内容</th>--%>
            <th>作者</th>
            <th>启用</th>
            <th>发布日期</th>
            <th>是否审核</th>
            <th>审核日期</th>
            <th>审核人员</th>
            <th>赞</th>
            <th>热门</th>
            <th>创建日期</th>
            <th>最后更新日期</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="news" items="${newsList}" varStatus="index">
            <tr align="right">
                <td>${news.id}
                    <input type="hidden" name="ids" value="${news.id}" />
                </td>
                <td>${news.subject}</td>
                <td>${news.authorName}</td>
                <td>${news.available ? '是' : '否'}</td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${news.publishDate}" /></td>
                <td>${news.approved ? '已' : '未'}审核</td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${news.approvedDate}" /></td>
                <td>${news.approvedByName}</td>
                <td>${news.like}</td>
                <td>${news.popular ? '是' : '否'}</td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${news.creationDate}" /></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${news.lastUpdateDate}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>