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
                    <td align="right"><label for="cellPhone" title="cellPhone">password</label></td>
                    <td><input type="text" class="ipt_value" id="password" value=""/></td>
                </tr>
                <tr>
                    <td align="right"><label for="cellPhone" title="cellPhone">confirmPassword</label></td>
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
                    <td><input type="text" class="ipt_value" id="cellPhone" value="" /></td>
                </tr>
                <tr>
                    <td align="right"><label for="provId" title="provId">provId</label></td>
                    <td><input type="text" class="ipt_value" id="provId" value="" /></td>
                </tr>
                <tr>
                    <td align="right"><label for="cityId" title="cityId">cityId</label></td>
                    <td><input type="text" class="ipt_value" id="cityId" value="" /></td>
                </tr>
                <tr>
                    <td align="right"><label for="schoolId" title="schoolId">schoolId</label></td>
                    <td><input type="text" class="ipt_value" id="schoolId" value="" /></td>
                </tr>
                <tr>
                    <td align="right"><label for="classId" title="classId">classId</label></td>
                    <td><input type="text" class="ipt_value" id="classId" value="" /></td>
                </tr>
                <tr>
                    <td align="right"><label for="teacherId" title="teacherId">teacherId</label></td>
                    <td><input type="text" class="ipt_value" id="teacherId" value="" /></td>
                </tr>
                <tr>
                    <td align="right"><label for="studentName" title="studentName">studentName</label></td>
                    <td><input type="text" class="ipt_value" id="studentName" value="" /></td>
                </tr>
                <tr>
                    <td align="right"><label for="studentSex" title="studentSex">studentSex</label></td>
                    <td><input type="text" class="ipt_value" id="studentSex" value="" /></td>
                </tr>
                <tr>
                    <td align="right"><label for="studentAge" title="studentAge">studentAge</label></td>
                    <td><input type="text" class="ipt_value" id="studentAge" value="" /></td>
                </tr>
                <tr>
                    <td align="right"><label for="parentName" title="parentName">parentName</label></td>
                    <td><input type="text" class="ipt_value" id="parentName" value="" /></td>
                </tr>
                <tr>
                    <td align="right"><label for="relation" title="relation">relation</label></td>
                    <td><input type="text" class="ipt_value" id="relation" value="" /></td>
                </tr>
                <tr>
                    <td align="right"><label for="address" title="address">address</label></td>
                    <td><input type="text" class="ipt_value" id="address" value="" /></td>
                </tr>
                <tr>
                    <td align="right"><label for="braceletCardNumber" title="braceletCardNumber">braceletCardNumber</label></td>
                    <td><input type="text" class="ipt_value" id="braceletCardNumber" value="" /></td>
                </tr>
                <tr>
                    <td align="right"><label for="braceletNumber" title="braceletNumber">braceletNumber</label></td>
                    <td><input type="text" class="ipt_value" id="braceletNumber" value="" /></td>
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
     </table>
</fieldset>
</body>
</html>
