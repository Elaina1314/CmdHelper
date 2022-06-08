package com.cmdhelper.drfun.Extension;

import com.cmdhelper.drfun.Util.FileUtil;

public class ExtensionUtil {
    public static final String MANIFEST_FILE_NAME = "extension_config.json";

    public static boolean isExtensionPackValid(String path) throws ExtensionException {
        // 判断文件是否是一个扩展包
        if (!FileUtil.isExists(path)) {
            throw new ExtensionException("invalid extension pack");
        }

        return true;
    }

}
