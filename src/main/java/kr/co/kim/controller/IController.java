package kr.co.kim.controller;

import kr.co.kim.helper.RequestParser;
import kr.co.kim.model.ResponseData;

public interface IController {
    /**
     * Thie method handle client request.
     * 
     * @param method      HTTP Method
     * @param url         URL
     * @param requestBoby Body
     * @return This method returns the response result as a byte array.
     */
    public ResponseData handleRequest(RequestParser request) throws Exception;
}
