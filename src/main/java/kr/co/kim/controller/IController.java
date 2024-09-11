package kr.co.kim.controller;

public interface IController {
    /**
     * Thie method handle client request.
     * @param method HTTP Method
     * @param url URL
     * @param requestBoby Body
     * @return This method returns the response result as a byte array.
     */
    public byte[] handleRequest(String method, String url, String requestBoby) throws Exception;
}
