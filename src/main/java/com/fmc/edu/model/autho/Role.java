package com.fmc.edu.model.autho;

import com.fmc.edu.model.BaseBean;

import java.io.Serializable;

public class Role extends BaseBean implements Serializable {
    private String mRole;
    private String mDescription;
    private Boolean mAvailable = Boolean.FALSE;

    public Role() {
    }

    public Role(String pRole, String pDescription, Boolean pAvailable) {
        this.mRole = pRole;
        this.mDescription = pDescription;
        this.mAvailable = pAvailable;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String pRole) {
        mRole = pRole;
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
