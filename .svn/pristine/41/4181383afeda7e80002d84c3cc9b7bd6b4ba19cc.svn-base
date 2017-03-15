package com.sumridge.smart.util;

import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liu on 16/8/23.
 */
public class FileUtil {

    public static String toMD5String(InputStream stream) {
        try {
            return DigestUtils.md5DigestAsHex(stream);
        } catch (IOException e) {
            return null;
        }
    }
}
