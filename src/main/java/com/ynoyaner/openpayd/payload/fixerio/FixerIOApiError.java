package com.ynoyaner.openpayd.payload.fixerio;

import java.io.Serializable;

public class FixerIOApiError implements Serializable {
    private String code;
    private String type;
    private String info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
