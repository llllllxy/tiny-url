package org.tinycloud.tinyurl.common.utils;

import java.util.regex.Pattern;


/**
 * @author liuxingyu01
 * @date 2022-03-11-16:48
 * @description URL校验
 **/
public class UrlUtils {
    private static final Pattern URL_REG = Pattern.compile("^(((ht|f)tps?):\\/\\/)?[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$");

    public static boolean checkURL(String url) {
        return URL_REG.matcher(url).matches();
    }
}
