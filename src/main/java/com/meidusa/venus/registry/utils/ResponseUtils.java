package com.meidusa.venus.registry.utils;

import com.meidusa.venus.registry.domain.JsonResponse;

/**
 * Created by huawei on 12/18/15.
 */
public class ResponseUtils {

    public static <T> JsonResponse getResponse(int code, String message, T data) {
        JsonResponse<T> jsonResponse = new JsonResponse<>();
        jsonResponse.setCode(code);
        jsonResponse.setMessage(message);
        jsonResponse.setData(data);
        return jsonResponse;
    }

    public static JsonResponse getBadRequestResponse(String message) {
        return getResponse(Constants.REGISTRY_WEB_CODE_BAD_REQUEST, message, null);
    }

    public static JsonResponse getInternalErrorResponse(String message) {
        return getResponse(Constants.REGISTRY_WEB_CODE_INTERNAL_ERROR, message, null);
    }

    public static JsonResponse getNotFoundResponse(String message) {
        return getResponse(Constants.REGISTRY_WEB_CODE_NOT_FOUND, message, null);
    }

    public static <T> JsonResponse getOkResponse(T data) {
        return getResponse(Constants.REGISTRY_WEB_CODE_OK, null, data);
    }
}
