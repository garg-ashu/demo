package com.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentXls {

    private String fileName;

    private String keyValuePair;

    private List<String> headers = new ArrayList<>();

    private Map<String, String> headerDataType = new HashMap<>();

    private List<List<String>> data = new ArrayList<>();

    private List<Map<String, String>> dataAsMap = new ArrayList<>();

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getKeyValuePair() {
        return keyValuePair;
    }

    public void setKeyValuePair(String keyValuePair) {
        this.keyValuePair = keyValuePair;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaderDataType() {
        return headerDataType;
    }

    public void setHeaderDataType(Map<String, String> headerDataType) {
        this.headerDataType = headerDataType;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public List<Map<String, String>> getDataAsMap() {
        return dataAsMap;
    }

    public void setDataAsMap(List<Map<String, String>> dataAsMap) {
        this.dataAsMap = dataAsMap;
    }

    @Override
    public String toString() {
        return "DocumentXls [fileName=" + fileName + ", keyValuePair=" + keyValuePair + ", headers=" + headers
                + ", headerDataType=" + headerDataType + ", length=" + data.size() + "]";
    }

}
