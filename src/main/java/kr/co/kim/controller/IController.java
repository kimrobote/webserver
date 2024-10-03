package kr.co.kim.controller;

import kr.co.kim.helper.RequestParser;

public interface IController {
    /**
     * Thie method handle client request.
     * 
     * @param method      HTTP Method
     * @param url         URL
     * @param requestBoby Body
     * @return This method returns the response result as a byte array.
     */
    public byte[] handleRequest(RequestParser request) throws Exception;
}
