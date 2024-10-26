package kr.co.kim.helper;

public enum HttpStatusEnum {
    HTTP200("200"),
    HTTP302("301"),
    HTTP401("401"),
    HTTP404("404"),
    HTTP500("500");

    private final String type;

    HttpStatusEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
