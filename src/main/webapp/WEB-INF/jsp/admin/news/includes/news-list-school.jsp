<c:set var="provinceId" value="${param.provinceId}" />
<form action="/admin/news/news-list" method="get" class="form-inline">
    <input type="hidden" name="t" value="${param.t}" />
    <div class="form-group input-group">
        <span class="input-group-addon">省份：</span>
        <select name="provinceId" class="form-control" id="slt-province">
            <c:forEach var="province" items="${locationMap}" varStatus="index">
                <c:if test="${index.index eq '0' and empty provinceId}">
                    <c:set var="provinceId" value="${province.key}" />
                </c:if>
                <option value="${province.key}" ${provinceId eq province.key ? 'selected' : ''}>${province.value.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group input-group">
        <span class="input-group-addon">城市：</span>
        <select name="cityId" class="form-control" id="slt-cities">
            <c:forEach var="city" items="${locationMap[provinceId].cities}">
                <option value="${city.key}">${city.value.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group input-group">
        <span class="input-group-addon">学校：</span>
        <select name="schoolId" class="form-control" id="slt-schools">
            <option value="" ${empty param.schoolId ? 'selected' : ''}>--</option>
            <c:forEach var="school" items="${schools}">
                <option value="${school.schoolId}" ${school.schoolId eq param.schoolId ? 'selected' : ''}>${school.schoolName}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group input-group">
        <span class="input-group-addon">类型：</span>
        <select name="mode" class="form-control">
            <option value="2" ${param.mode eq 2 ? 'selected' : ''}>校园动态</option>
            <option value="3" ${param.mode eq 3 ? 'selected' : ''}>校园通知</option>
            <option value="4" ${param.mode eq 4 ? 'selected' : ''}>校园新闻</option>
        </select>
    </div>
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