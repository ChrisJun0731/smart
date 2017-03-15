package com.sumridge.smart.bean;

import com.sumridge.smart.entity.SaleInfo;

/**
 * Created by liu on 16/7/11.
 */
public class SaleDisplayBean extends SaleInfo {

    private String contactName;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
