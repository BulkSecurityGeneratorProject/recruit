package com.recruit.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "zh-cn";
    public static final String QINIU_ACCESS_KEY = "b7L5qjJLnOxFqdCbnvDWjDSQQSRnWmlLB1ilPXSI";
    public static final String QINIU_SECRET_KEY = "WOEFIGD6JvN8WoZByF5ohIjhtrRXCfTsi7zpil76";
    public static final String QINIU_BUCKET = "limy";

    private Constants() {
    }
}
