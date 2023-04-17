package com.study.contest.model.enums;

public enum ColumEnum {


    ID(0, "ID"),
    TITLE(1, "TITLE"),
    CONTENT(2, "CONTENT"),
    DATE(3, "DATE");


    private final Integer columnIndex;
    private final String name;

    ColumEnum(Integer columnIndex, String name) {
        this.columnIndex = columnIndex;
        this.name = name;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public String getName() {
        return name;
    }
}
