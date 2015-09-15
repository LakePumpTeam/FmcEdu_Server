<%@ page pageEncoding="UTF-8" %>
<c:if test="${not empty param.provinceId}">
    <c:set var="provinceId" value="${param.provinceId}" />
</c:if>
<div class="form-group form-inline">
    <div class="input-group">
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
    <div class="input-group">
        <span class="input-group-addon">城市：</span>
        <select name="cityId" class="form-control" id="slt-cities">
            <c:forEach var="city" items="${locationMap[provinceId].cities}">
                <option value="${city.key}" ${city.key eq param.cityId ? 'selected' : ''}>${city.value.name}</option>
            </c:forEach>
        </select>
    </div>
    <c:if test="${param.enableSchool}">
        <div class="input-group">
            <span class="input-group-addon">学校：</span>
            <select name="schoolId" class="form-control" id="slt-schools">
                <c:forEach var="school" items="${not empty schools ? schools : schoolsOfDefaultCity}">
                    <option value="${school.schoolId}" ${school.schoolId eq param.schoolId ? 'selected' : ''}>${school.schoolName}</option>
                </c:forEach>
            </select>
        </div>
    </c:if>

    <script>
        $(document).ready(function () {
            $("#slt-province").on("change", provinceChange);
            <c:if test="${param.enableSchool}">
            $("#slt-cities").on("change", cityChange);
            </c:if>
        });

        function provinceChange(e) {
            $.getJSON(ctx + "/admin/cities?provinceId=" + $("#slt-province").val(), function (data) {
                var cityOptions = [];
                $.each(data, function (i, e) {
                    cityOptions.push(new Option(e.name, e.cityId));
                });
                $(cityOptions[0]).prop("selected", true);
                var citySelect = $("#slt-cities");
                citySelect.find("option").remove();
                citySelect.append(cityOptions);
                // even if the event is not incorrect
                cityChange(e);
            });
        }
        <c:if test="${param.enableSchool}">
        function cityChange(e) {
            $.getJSON(ctx + "/admin/schools?cityId=" + $("#slt-cities").val(), function (data) {
                var schoolSelect = $("#slt-schools");
                schoolSelect.find("option").remove();
                if (data.length !== undefined && data.length > 0) {
                    var schoolOptions = [];
                    $.each(data, function (i, e) {
                        schoolOptions.push(new Option(e.schoolName, e.schoolId));
                    });
                    schoolSelect.append(schoolOptions);
                }
            });
        }
        </c:if>
    </script>
</div>