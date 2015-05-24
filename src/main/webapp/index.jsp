<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>FmcEdu Interface Index</title>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <script type="text/javascript" src="${ctx}/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.base64.js"></script>
    <script type="text/javascript" src="${ctx}/js/index.js"></script>
    <style type="text/css">
        * {
            font-family: monospace;
        }

        .ul-toolbar li {
            margin-bottom: 4px;
        }

        td.parameters table {
            float: right;
        }
    </style>
</head>
<body>
<ul class="ul-toolbar">
    <li><input type="text" encode-type="encode" class="ipt_value"/>
        <input type="button" class="btn_base64" data-type="encode" value="Base64 Encode"/>
        &nbsp;|&nbsp; <span class="result" encode-type="encode"></span></li>
    <li><input type="text" encode-type="decode" class="ipt_value"/>
        <input type="button" class="btn_base64" data-type="decode" value="Base64 Decode"/>
        &nbsp;|&nbsp; <span class="result" encode-type="decode"></span></li>
</ul>
<hr/>
<fieldset>
    <legend>注册</legend>
    <table border="1">
        <tr>
            <th width="5%">Index#</th>
            <th>Function</th>
            <th>BaseURL</th>
            <th>Parameters</th>
            <th>Request</th>
            <th>Reset</th>
            <th width="56%">Result</th>
        </tr>
        <tr>
            <td>#1</td>
            <td class="function">注册：发送验证码（针对家长）</td>
            <td class="baseUrl">${ctx}/profile/requestPhoneIdentify</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="cellPhone" title="cellPhone">cellPhone</label></td>
                        <td><input type="text" class="ipt_value" id="cellPhone" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#2</td>
            <td class="function">注册：验证基本信息</td>
            <td class="baseUrl">${ctx}/profile/requestRegisterConfirm</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="cellPhone" title="cellPhone">cellPhone</label></td>
                        <td><input type="text" class="ipt_value" id="cellPhone" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="cellPhone" title="cellPhone">authCode</label></td>
                        <td><input type="text" class="ipt_value" id="authCode" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="salt" title="salt">salt</label></td>
                        <td><input type="text" class="ipt_value" id="salt" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="password" title="cellPhone">password</label></td>
                        <td><input type="text" class="ipt_value" id="password" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="confirmPassword" title="cellPhone">confirmPassword</label></td>
                        <td><input type="text" class="ipt_value" id="confirmPassword" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#3</td>
            <td class="function">申请验证</td>
            <td class="baseUrl">${ctx}/profile/requestRegisterBaseInfo</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="cellPhone" title="cellPhone">cellPhone</label></td>
                        <td><input type="text" class="ipt_value" id="cellPhone" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="provId" title="provId">provId</label></td>
                        <td><input type="text" class="ipt_value" id="provId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="cityId" title="cityId">cityId</label></td>
                        <td><input type="text" class="ipt_value" id="cityId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="schoolId" title="schoolId">schoolId</label></td>
                        <td><input type="text" class="ipt_value" id="schoolId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="classId" title="classId">classId</label></td>
                        <td><input type="text" class="ipt_value" id="classId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="teacherId" title="teacherId">teacherId</label></td>
                        <td><input type="text" class="ipt_value" id="teacherId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="studentName" title="studentName">studentName</label></td>
                        <td><input type="text" class="ipt_value" id="studentName" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="studentSex" title="studentSex">studentSex</label></td>
                        <td><input type="text" class="ipt_value" id="studentSex" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="studentAge" title="studentAge">studentAge</label></td>
                        <td><input type="text" class="ipt_value" id="studentAge" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="parentName" title="parentName">parentName</label></td>
                        <td><input type="text" class="ipt_value" id="parentName" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="relation" title="relation">relation</label></td>
                        <td><input type="text" class="ipt_value" id="relation" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="address" title="address">address</label></td>
                        <td><input type="text" class="ipt_value" id="address" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="braceletCardNumber" title="braceletCardNumber">braceletCardNumber</label></td>
                        <td><input type="text" class="ipt_value" id="braceletCardNumber" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="braceletNumber" title="braceletNumber">braceletNumber</label></td>
                        <td><input type="text" class="ipt_value" id="braceletNumber" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="parentId" title="parentId">parentId</label></td>
                        <td><input type="text" class="ipt_value" id="parentId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="studentId" title="studentId">studentId</label></td>
                        <td><input type="text" class="ipt_value" id="studentId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="addressId" title="addressId">addressId</label></td>
                        <td><input type="text" class="ipt_value" id="addressId" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#4</td>
            <td class="function">登陆</td>
            <td class="baseUrl">${ctx}/profile/requestLogin</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="userAccount" title="userAccount">userAccount</label></td>
                        <td><input type="text" class="ipt_value" id="userAccount" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="password" title="password">password</label></td>
                        <td><input type="text" class="ipt_value" id="password" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#5</td>
            <td class="function">忘记密码接口</td>
            <td class="baseUrl">${ctx}/profile/requestForgetPwd</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="cellPhone" title="cellPhone">cellPhone</label></td>
                        <td><input type="text" class="ipt_value" id="cellPhone" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="authCode" title="authCode">authCode</label></td>
                        <td><input type="text" class="ipt_value" id="authCode" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="password" title="password">password</label></td>
                        <td><input type="text" class="ipt_value" id="password" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#6</td>
            <td class="function">修改密码</td>
            <td class="baseUrl">${ctx}/profile/requestAlterPwd</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="userId" title="userId">userId</label></td>
                        <td><input type="text" class="ipt_value" id="userId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="oldPassword" title="oldPassword">oldPassword</label></td>
                        <td><input type="text" class="ipt_value" id="oldPassword" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="newPassword" title="newPassword">newPassword</label></td>
                        <td><input type="text" class="ipt_value" id="newPassword" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#7</td>
            <td class="function">获取家长关联信息</td>
            <td class="baseUrl">${ctx}/profile/requestGetRelateInfo</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="parentId" title="parentId">parentId</label></td>
                        <td><input type="text" class="ipt_value" id="parentId" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#8</td>
            <td class="function">家长审核</td>
            <td class="baseUrl">${ctx}/profile/requestParentAudit</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="parentIds" title="parentId">parentIds</label></td>
                        <td><input type="text" class="ipt_value" id="parentIds" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="teacherId" title="teacherId">teacherId</label></td>
                        <td><input type="text" class="ipt_value" id="teacherId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="setPass" title="setPass">setPass</label></td>
                        <td><input type="text" class="ipt_value" id="setPass" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#9</td>
            <td class="function">批量审核家长</td>
            <td class="baseUrl">${ctx}/profile/requestParentAuditAll</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="teacherId" title="teacherId">teacherId</label></td>
                        <td><input type="text" class="ipt_value" id="teacherId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="setPass" title="allPass">allPass</label></td>
                        <td><input type="text" class="ipt_value" id="allPass" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#10</td>
            <td class="function">家长列表</td>
            <td class="baseUrl">${ctx}/profile/requestPendingAuditParentList</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="teacherId" title="teacherId">teacherId</label></td>
                        <td><input type="text" class="ipt_value" id="teacherId" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#11</td>
            <td class="function">获取salt</td>
            <td class="baseUrl">${ctx}/profile/requestSalt</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="cellPhone" title="cellPhone">cellPhone</label></td>
                        <td><input type="text" class="ipt_value" id="cellPhone" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
    </table>
</fieldset>

<hr/>
<fieldset>
    <legend>地址</legend>
    <table border="1">
        <tr>
            <th width="5%">Index#</th>
            <th>Function</th>
            <th>BaseURL</th>
            <th>Parameters</th>
            <th>Request</th>
            <th>Reset</th>
            <th width="56%">Result</th>
        </tr>
        <tr>
            <td>#1</td>
            <td class="function">省份</td>
            <td class="baseUrl">${ctx}/location/requestProv</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="filterKey" title="filterKey">filterKey(可空)</label></td>
                        <td><input type="text" class="ipt_value" id="filterKey" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="pageIndex" title="pageIndex">pageIndex(从1开始)</label></td>
                        <td><input type="text" class="ipt_value" id="pageIndex" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="pageSize" title="pageSize">pageSize</label></td>
                        <td><input type="text" class="ipt_value" id="pageSize" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>

        <tr>
            <td>#2</td>
            <td class="function">城市</td>
            <td class="baseUrl">${ctx}/location/requestCities</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="filterKey" title="filterKey">filterKey(可空)</label></td>
                        <td><input type="text" class="ipt_value" id="filterKey" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="provId" title="provId">provId</label></td>
                        <td><input type="text" class="ipt_value" id="provId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="pageIndex" title="pageIndex">pageIndex(从1开始)</label></td>
                        <td><input type="text" class="ipt_value" id="pageIndex" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="pageSize" title="pageSize">pageSize</label></td>
                        <td><input type="text" class="ipt_value" id="pageSize" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
    </table>
</fieldset>

<fieldset>
    <legend>学校</legend>
    <table border="1">
        <tr>
            <th width="5%">Index#</th>
            <th>Function</th>
            <th>BaseURL</th>
            <th>Parameters</th>
            <th>Request</th>
            <th>Reset</th>
            <th width="56%">Result</th>
        </tr>
        <tr>
            <td>#1</td>
            <td class="function">学校</td>
            <td class="baseUrl">${ctx}/school/requestSchools</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="filterKey" title="filterKey">filterKey(可空)</label></td>
                        <td><input type="text" class="ipt_value" id="filterKey" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="cityId" title="cityId">cityId</label></td>
                        <td><input type="text" class="ipt_value" id="cityId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="pageIndex" title="pageIndex">pageIndex(从1开始)</label></td>
                        <td><input type="text" class="ipt_value" id="pageIndex" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="pageSize" title="pageSize">pageSize</label></td>
                        <td><input type="text" class="ipt_value" id="pageSize" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#2</td>
            <td class="function">班级</td>
            <td class="baseUrl">${ctx}/school/requestClasses</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="filterKey" title="filterKey">filterKey(可空)</label></td>
                        <td><input type="text" class="ipt_value" id="filterKey" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="schoolId" title="schoolId">schoolId</label></td>
                        <td><input type="text" class="ipt_value" id="schoolId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="pageIndex" title="pageIndex">pageIndex(从1开始)</label></td>
                        <td><input type="text" class="ipt_value" id="pageIndex" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="pageSize" title="pageSize">pageSize</label></td>
                        <td><input type="text" class="ipt_value" id="pageSize" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#3</td>
            <td class="function">班主任</td>
            <td class="baseUrl">${ctx}/school/requestHeadTeacher</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="classId" title="classId">classId</label></td>
                        <td><input type="text" class="ipt_value" id="classId" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#3</td>
            <td class="function">教师信息查询</td>
            <td class="baseUrl">${ctx}/school/requestTeacherInfo</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="teacherId" title="teacherId">teacherId</label></td>
                        <td><input type="text" class="ipt_value" id="teacherId" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#4</td>
            <td class="function">修改教师信息</td>
            <td class="baseUrl">${ctx}/school/requestModifyTeacherInfo</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="teacherId" title="teacherId">teacherId</label></td>
                        <td><input type="text" class="ipt_value" id="teacherId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="teacherName" title="teacherName">teacherName</label></td>
                        <td><input type="text" class="ipt_value" id="teacherName" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="teacherBirth" title="teacherBirth">teacherBirth</label></td>
                        <td><input type="text" class="ipt_value" id="teacherBirth" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="teacherSex" title="teacherSex">teacherSex</label></td>
                        <td><input type="text" class="ipt_value" id="teacherSex" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="course" title="course">course</label></td>
                        <td><input type="text" class="ipt_value" id="course" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="cellPhone" title="cellPhone">cellPhone</label></td>
                        <td><input type="text" class="ipt_value" id="cellPhone" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="resume" title="resume">resume</label></td>
                        <td><input type="text" class="ipt_value" id="resume" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
    </table>
</fieldset>

<fieldset>
    <legend>首页</legend>
    <table border="1">
        <tr>
            <th width="5%">Index#</th>
            <th>Function</th>
            <th>BaseURL</th>
            <th>Parameters</th>
            <th>Request</th>
            <th>Reset</th>
            <th width="56%">Result</th>
        </tr>
        <tr>
            <td>#1</td>
            <td class="function">获取主页信息--教师信息</td>
            <td class="baseUrl">${ctx}/home/requestHeaderTeacherForHomePage</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="userId" title="userId">userId</label></td>
                        <td><input type="text" class="ipt_value" id="userId" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
    </table>
</fieldset>

<fieldset>
    <legend>新闻动态</legend>
    <table border="1">
        <tr>
            <th width="5%">Index#</th>
            <th>Function</th>
            <th>BaseURL</th>
            <th>Parameters</th>
            <th>Request</th>
            <th>Reset</th>
            <th width="56%">Result</th>
        </tr>
        <tr>
            <td>#1</td>
            <td class="function">获取新闻列表</td>
            <td class="baseUrl">${ctx}/news/requestNewsList</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="userId" title="userId">userId</label></td>
                        <td><input type="text" class="ipt_value" id="userId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="type" title="type">type</label></td>
                        <td><input type="text" class="ipt_value" id="type" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="pageIndex" title="pageIndex">pageIndex(从1开始)</label></td>
                        <td><input type="text" class="ipt_value" id="pageIndex" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="pageSize" title="pageSize">pageSize</label></td>
                        <td><input type="text" class="ipt_value" id="pageSize" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
             <td>#2</td>
             <td class="function">获取幻灯片</td>
             <td class="baseUrl">${ctx}/news/requestSlides</td>
            <td class="parameters">
            </td>
             <td><input type="button" class="btn_request" value="Request"/></td>
             <td><input type="button" class="btn_reset" value="Reset"/></td>
             <td class="result"></td>
        </tr>
         <tr>
           <td>#3</td>
             <td class="function">发表评论</td>
           <td class="baseUrl">${ctx}/news/postComment</td>
             <td class="parameters">
                 <table>
                     <tr>
                         <td align="right"><label for="userId" title="userId">userId</label></td>
                         <td><input type="text" class="ipt_value" id="userId" value=""/></td>
                     </tr>
                     <tr>
                         <td align="right"><label for="newsId" title="newsId">newsId</label></td>
                         <td><input type="text" class="ipt_value" id="newsId" value=""/></td>
                     </tr>
                     <tr>
                         <td align="right"><label for="content" title="content">content</label></td>
                         <td><input type="text" class="ipt_value" id="content" value=""/></td>
                     </tr>
                 </table>
             </td>
           <td><input type="button" class="btn_request" value="Request"/></td>
           <td><input type="button" class="btn_reset" value="Reset"/></td>
           <td class="result"></td>
         </tr>
        <tr>
             <td>#4</td>
            <td class="function">获取新闻详情</td>
            <td class="baseUrl">${ctx}/news/requestNewsDetail</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="userId" title="newsId">userId</label></td>
                        <td><input type="text" class="ipt_value" id="userId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="newsId" title="newsId">newsId</label></td>
                        <td><input type="text" class="ipt_value" id="newsId" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#5</td>
            <td class="function">首页：是否有新消息</td>
            <td class="baseUrl">${ctx}/news/checkNewNews</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="userId" title="newsId">userId</label></td>
                        <td><input type="text" class="ipt_value" id="userId" value=""/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <tr>
            <td>#6</td>
            <td class="function">点赞</td>
            <td class="baseUrl">${ctx}/news/likeNews</td>
            <td class="parameters">
                <table>
                    <tr>
                        <td align="right"><label for="userId" title="newsId">userId</label></td>
                        <td><input type="text" class="ipt_value" id="userId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="newsId" title="newsId">newsId</label></td>
                        <td><input type="text" class="ipt_value" id="newsId" value=""/></td>
                    </tr>
                    <tr>
                        <td align="right"><label for="isLike" title="isLike">isLike</label></td>
                        <td><input type="checkbox" class="ipt_value" id="isLike" value="checked"/></td>
                    </tr>
                </table>
            </td>
            <td><input type="button" class="btn_request" value="Request"/></td>
            <td><input type="button" class="btn_reset" value="Reset"/></td>
            <td class="result"></td>
        </tr>
        <span>
            <td>#7</td>
            <td class="function">发布班级动态</td>
             <td class="baseUrl">${ctx}/news/postClassNews</td>
               <td class="parameters">
                   <form method="POST" enctype="multipart/form-data" action="${ctx}/news/postClassNews">

                          <div>
                              <label for="userId" title="userId">userId</label>
                              <input type="text" class="ipt_value" name="userId" id="userId" value=""/>
                          </div>
                          <div>
                              <label for="subject" title="subject">subject</label>
                              <input type="text" class="ipt_value" name="subject" id="subject" value=""/>
                          </div>
                          <div>
                              <label for="content" title="content">content</label>
                              <input type="text" class="ipt_value" name="content" id="content" value=""/>
                          </div>
                          <div>
                              <label for="img1" title="img1">img1</label>
                              <input type="file" name="img1" class="img1" id="img1"/>
                          </div>
                          <div>
                              <label for="img2" title="img2">img2</label>
                              <input type="file" name="img2" class="img2" id="img2"/>
                          </div>
                          <div>
                              <label for="img3" title="img3">img3</label>
                              <input type="file" name="img3" class="img3" id="img3"/>
                          </div>
                          <div>
                              <label for="img4" title="img4">img4</label>
                              <input type="file" name="img4" class="img4" id="img4"/>
                          </div>

                          <input type="submit" value="Upload">
                      </form>
    </table>
                   </table>
               </td>
        </tr>
    </table>
</fieldset>
</body>
</html>
