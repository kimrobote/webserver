package kr.co.kim.model;

import kr.co.kim.helper.HttpStatusEnum;

public class ResponseData {
    private String contentType;
    private HttpStatusEnum status;
    private String redirectUrl;
    private byte[] body;

    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public HttpStatusEnum getStatus() {
        return status;
    }
    public void setStatus(HttpStatusEnum status) {
        this.status = status;
    }
    public String getRedirectUrl() {
        return redirectUrl;
    }
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
    public int getContentLength() {
        return body != null ? body.length : 0;
    }
    public byte[] getBody() {
        return body;
    }
    public void setBody(byte[] body) {
        this.body = body;
    }    
}
