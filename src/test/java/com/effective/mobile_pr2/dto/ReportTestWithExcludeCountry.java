package com.effective.mobile_pr2.dto;


import com.effective.mobile_pr2.annotation.ExcludeColumn;

@ExcludeColumn(nameOfColumns = {"type"})
public class ReportTestWithExcludeCountry {
    private String name;
    private String type;

    public ReportTestWithExcludeCountry(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
