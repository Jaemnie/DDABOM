package com.example.team.perser;

import java.util.List;

public class LicenseSearchRequest {
    private String operationName;
    private Variables variables;
    private String query;

    // Getter and Setter methods
    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public Variables getVariables() {
        return variables;
    }

    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}

// 변수 객체 정의
class Variables {
    private int page;
    private int itemsPerPage;
    private Request request;

    // Getter and Setter methods
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}

// 요청 세부사항 객체 정의
class Request {
    private String field;
    private String keyword;
    private List<String> ncsCd;
    private List<String> type;
    private List<String> scheduleStatus;

    // Getter and Setter methods
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getNcsCd() {
        return ncsCd;
    }

    public void setNcsCd(List<String> ncsCd) {
        this.ncsCd = ncsCd;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<String> getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(List<String> scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }
}
class LicenseSearchResponse {
    private Data data;

    // getters and setters


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

class Data {
    private JnLicenseSearchList jnLicenseSearchList;

    public JnLicenseSearchList getJnLicenseSearchList() {
        return jnLicenseSearchList;
    }

    public void setJnLicenseSearchList(JnLicenseSearchList jnLicenseSearchList) {
        this.jnLicenseSearchList = jnLicenseSearchList;
    }
    // getters and setters
}
class JnLicenseSearchList {
    private Page page;
    private List<License> data;
    private String __typename;

    // getters and setters

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<License> getData() {
        return data;
    }

    public void setData(List<License> data) {
        this.data = data;
    }

    public String get__typename() {
        return __typename;
    }

    public void set__typename(String __typename) {
        this.__typename = __typename;
    }
}

class Page {
    private int totalPage;
    private int totalItems;
    private int itemsPerPage;
    private int page;
    private String __typename;

    // getters and setters

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String get__typename() {
        return __typename;
    }

    public void set__typename(String __typename) {
        this.__typename = __typename;
    }
}

class License {
    private int ldId;
    private String jmfldnm;
    private String rgName;
    private String rgImg;
    private String licenseType;
    private String licenseTypeColor;
    private boolean always;
    private String receiptUrl;
    private String description;
    private String examRegStart;
    private String examRegEnd;
    private String examStart;
    private String examEnd;
    private String passStart;
    private String passEnd;
    private boolean bigData;
    private boolean hopeLics;
    private String licenseTags;
    private String __typename;

    // getters and setters

    public int getLdId() {
        return ldId;
    }

    public void setLdId(int ldId) {
        this.ldId = ldId;
    }

    public String getJmfldnm() {
        return jmfldnm;
    }

    public void setJmfldnm(String jmfldnm) {
        this.jmfldnm = jmfldnm;
    }

    public String getRgName() {
        return rgName;
    }

    public void setRgName(String rgName) {
        this.rgName = rgName;
    }

    public String getRgImg() {
        return rgImg;
    }

    public void setRgImg(String rgImg) {
        this.rgImg = rgImg;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getLicenseTypeColor() {
        return licenseTypeColor;
    }

    public void setLicenseTypeColor(String licenseTypeColor) {
        this.licenseTypeColor = licenseTypeColor;
    }

    public boolean isAlways() {
        return always;
    }

    public void setAlways(boolean always) {
        this.always = always;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExamRegStart() {
        return examRegStart;
    }

    public void setExamRegStart(String examRegStart) {
        this.examRegStart = examRegStart;
    }

    public String getExamRegEnd() {
        return examRegEnd;
    }

    public void setExamRegEnd(String examRegEnd) {
        this.examRegEnd = examRegEnd;
    }

    public String getExamStart() {
        return examStart;
    }

    public void setExamStart(String examStart) {
        this.examStart = examStart;
    }

    public String getExamEnd() {
        return examEnd;
    }

    public void setExamEnd(String examEnd) {
        this.examEnd = examEnd;
    }

    public String getPassStart() {
        return passStart;
    }

    public void setPassStart(String passStart) {
        this.passStart = passStart;
    }

    public String getPassEnd() {
        return passEnd;
    }

    public void setPassEnd(String passEnd) {
        this.passEnd = passEnd;
    }

    public boolean isBigData() {
        return bigData;
    }

    public void setBigData(boolean bigData) {
        this.bigData = bigData;
    }

    public boolean isHopeLics() {
        return hopeLics;
    }

    public void setHopeLics(boolean hopeLics) {
        this.hopeLics = hopeLics;
    }

    public String getLicenseTags() {
        return licenseTags;
    }

    public void setLicenseTags(String licenseTags) {
        this.licenseTags = licenseTags;
    }

    public String get__typename() {
        return __typename;
    }

    public void set__typename(String __typename) {
        this.__typename = __typename;
    }
}
