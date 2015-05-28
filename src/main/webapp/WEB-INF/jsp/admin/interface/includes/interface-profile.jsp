<div class="odd">
    <tr>
        <td>#1</td>
        <td class="function">发送验证码</td>
        <td class="baseUrl">${ctx}/profile/requestPhoneIdentify</td>
        <td class="parameters">
            <div class="form-group">
                <input type="text" class="ipt_value form-control" name="cellPhone" placeholder="cellPhone" />
            </div>
        </td>
        <td><input type="button" class="btn_request btn btn-primary" value="Request" /> /
            <input type="button" class="btn_reset btn btn-warning" value="Reset" /></td>
        <td class="result"></td>
    </tr>
    <tr class="even">
        <td>#3</td>
        <td class="function">申请验证</td>
        <td class="baseUrl">${ctx}/profile/requestRegisterBaseInfo</td>
        <td class="parameters">
            <div class="form-group input-group">
                <input type="text" class="ipt_value form-control" name="cellPhone" placeholder="cellPhone" />
                <input type="text" class="ipt_value form-control" name="provId" placeholder="provId" />
                <input type="text" class="ipt_value form-control" name="cityId" placeholder="cityId" />
                <input type="text" class="ipt_value form-control" name="schoolId" placeholder="schoolId" />
                <input type="text" class="ipt_value form-control" name="classId" placeholder="classId" />
                <input type="text" class="ipt_value form-control" name="teacherId" placeholder="teacherId" />
                <input type="text" class="ipt_value form-control" name="studentName" placeholder="studentName" />
                <input type="text" class="ipt_value form-control" name="studentSex" placeholder="studentSex" />
                <input type="text" class="ipt_value form-control" name="studentAge" placeholder="studentAge" />
                <input type="text" class="ipt_value form-control" name="parentName" placeholder="parentName" />
                <input type="text" class="ipt_value form-control" name="relation" placeholder="relation" />
                <input type="text" class="ipt_value form-control" name="address" placeholder="address" />
                <input type="text" class="ipt_value form-control" name="braceletCardNumber" placeholder="braceletCardNumber" />
                <input type="text" class="ipt_value form-control" name="braceletNumber" placeholder="braceletNumber" />
                <input type="text" class="ipt_value form-control" name="parentId" placeholder="parentId (nullable)" />
                <input type="text" class="ipt_value form-control" name="studentId" placeholder="studentId (nullable)" />
                <input type="text" class="ipt_value form-control" name="addressId" placeholder="addressId (nullable)" />
            </div>
        </td>
        <td><input type="button" class="btn_request btn btn-primary" value="Request" /> /
            <input type="button" class="btn_reset btn btn-warning" value="Reset" /></td>
        <td class="result"></td>
    </tr>