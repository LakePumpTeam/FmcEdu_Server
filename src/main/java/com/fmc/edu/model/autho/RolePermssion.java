package com.fmc.edu.model.autho;

import com.fmc.edu.model.BaseBean;

import java.io.Serializable;

public class RolePermssion extends BaseBean implements Serializable {

    private int mRoleId;
    private int mPermissionId;

    public int getRoleId() {
        return mRoleId;
    }

    public void setRoleId(int pRoleId) {
        mRoleId = pRoleId;
    }

    public int getPermissionId() {
        return mPermissionId;
    }

    public void setPermissionId(int pPermissionId) {
        mPermissionId = pPermissionId;
    }
}
