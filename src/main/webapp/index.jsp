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
<table border="1">
    <tr>
        <th width="5%">Index#</th>
        <th>BaseURL</th>
        <th>Parameters</th>
        <th>Request</th>
        <th>Reset</th>
        <th width="56%">Result</th>
    </tr>
    <tr>
        <td>#1</td>
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
</table>
</body>
</html>
