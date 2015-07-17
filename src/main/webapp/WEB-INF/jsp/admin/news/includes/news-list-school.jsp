<form action="/admin/createSlide" method="post" class="form-inline">
    <div class="form-group input-group">
        <span class="input-group-addon">省份：</span>
        <select name="provinceId" class="form-control col-xs-2" id="slt-province">
            <c:forEach var="province" items="${locationMap}" varStatus="index">
                <c:if test="${index.index eq '0' and empty pid}">
                    <c:set var="pid" value="${province.key}" />
                </c:if>
                <option value="${province.key}" ${pid eq province.key ? 'selected' : ''}>${province.value.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group input-group">
        <span class="input-group-addon">城市：</span>
        <select name="cityId" class="form-control col-xs-2" id="slt-cities">
            <c:forEach var="city" items="${locationMap[pid].cities}">
                <option value="city.key">${city.value.name}</option>
            </c:forEach>
        </select>
    </div>
</form>
<div class="dataTable_wrapper">
    <table class="table table-striped table-bordered table-hover" id="table-news">
        <thead>
        <tr>
            <th>#</th>
            <th>标题</th>
            <th>内容</th>
            <th>作者</th>
            <th>是否启用</th>
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
            <tr>
                <td>${news.id}
                    <input type="hidden" name="ids" value="${news.id}" />
                </td>
                <td>${news.subject}</td>
                <td>${news.content}</td>
                <td>${news.author}</td>
                <td><select name="available" class="form-control">
                    <option ${news.available ? 'selected' : ''} value="true">启用</option>
                    <option ${not news.available ? 'selected' : ''} value="false">禁用</option>
                </select></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${news.publishDate}" /></td>
                <td>${news.approved}</td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${news.approvedDate}" /></td>
                <td>${news.approvedBy}</td>
                <td>${news.like}</td>
                <td>${news.popular}</td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${news.creationDate}" /></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${news.lastUpdateDate}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>