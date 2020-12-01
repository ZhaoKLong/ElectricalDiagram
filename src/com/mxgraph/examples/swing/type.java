package com.mxgraph.examples.swing;

public class type {
    private String nickname;
    private int id;
    private String name;
    private String path;

    public type(String nickname, int id, String name, String path) {
        this.nickname = nickname;
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public static type[] typeList() {
        type[] list = new type[11];
        list[0] = new type("公变", 1, "publicTransformer", "");
        list[1] = new type("专变", 2, "purposeTransformer", "");
        list[2] = new type("断路器", 3, "circuitBreaker", "");
        list[3] = new type("隔离刀闸", 4, "breaker", "");
        list[4] = new type("负荷开关", 5, "loadSwitch", "");
        list[5] = new type("分支箱", 6, "branchBox", "");
        list[6] = new type("箱变(公变)", 13, "boxChangePublic", "");
        list[7] = new type("箱变(专变)", 7, "boxChange", "");
        list[8] = new type("故障指示器", 9, "faultIndicator", "");
        list[9] = new type("环网柜", 10, "ringNetworkCabinet", "");
        list[10] = new type("跌落式熔断器", 11, "dropFuse", "");
        return list;
    }
}
