package com.effective.mobile_pr2.dto;

public class Report {
    private String name;
    private String type;
    private String column1;
    private String column2;
    private String column3;
    private String column4;

    public Report(String name, String type, String column1, String column2, String column3, String column4) {
        this.name = name;
        this.type = type;
        this.column1 = column1;
        this.column2 = column2;
        this.column3 = column3;
        this.column4 = column4;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getColumn1() {
        return column1;
    }

    public String getColumn2() {
        return column2;
    }

    public String getColumn3() {
        return column3;
    }

    public String getColumn4() {
        return column4;
    }
}
