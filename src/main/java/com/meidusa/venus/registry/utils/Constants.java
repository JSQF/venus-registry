package com.meidusa.venus.registry.utils;

/**
 * Created by huawei on 12/18/15.
 */
public class Constants {

    public static final int REGISTRY_WEB_CODE_OK = 200;
    public static final int REGISTRY_WEB_CODE_BAD_REQUEST = 400;
    public static final int REGISTRY_WEB_CODE_NOT_FOUND = 404;
    public static final int REGISTRY_WEB_CODE_INTERNAL_ERROR = 500;

    public static final String CHARACTER_ENCODING_UTF8 = "UTF-8";

    public static final String CONTENT_TYPE_JSON = "text/json";

    public static final String SERVICE_ACTIVE_DESC = "启用";
    public static final String SERVICE_INACTIVE_DESC = "停用";

    public static final int SERVICE_NAME_EXISTS = 0;
    public static final int SERVICE_NAME_NOT_EXISTS = 1;
    public static final int SERVICE_NAME_GET_ERROR = 2;
}
