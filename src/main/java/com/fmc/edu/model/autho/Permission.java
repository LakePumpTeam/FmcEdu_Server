package com.fmc.edu.model.autho;

import com.fmc.edu.model.BaseBean;

import java.io.Serializable;

public class Permission extends BaseBean implements Serializable {
    private String mPermission;
    private String mDescription;
    private Boolean mAvailable = Boolean.FALSE;

    public Permission() {
    }

    public Permission(String pPermission, String pDescription, Boolean pAvailable) {
        this.mPermission = pDescription;
        this.mDescription = pDescription;
        this.mAvailable = pAvailable;
    }

    public String getPermission() {
        return mPermission;
    }

    public void setPermission(String pPermission) {
        mPermission = pPermission;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String pDescription) {
        mDescription = pDescription;
    }

    public Boolean getAvailable() {
        return mAvailable;
    }

    public void setAvailable(Boolean pAvailable) {
        mAvailable = pAvailable;
    }
}
