package com.meidusa.venus.registry.web.controller;

import com.meidusa.venus.registry.domain.JsonResponse;
import com.meidusa.venus.registry.utils.Constants;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by huawei on 12/18/15.
 */
public abstract class BaseController {

    protected void response(HttpServletResponse response, String contentType, String message) throws IOException {
        response.setContentType(contentType);
        response.setCharacterEncoding(Constants.CHARACTER_ENCODING_UTF8);
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (message != null) {
            try {
                byte[] data = message.getBytes(Constants.CHARACTER_ENCODING_UTF8);

                response.setContentLength(data.length);
                response.getOutputStream().write(data);
                response.getOutputStream().flush();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    public <T> void responseJson(HttpServletResponse response, JsonResponse<T> jsonResponse) throws IOException {
        response(response, Constants.CONTENT_TYPE_JSON, jsonResponse.toJson());
    }
}
