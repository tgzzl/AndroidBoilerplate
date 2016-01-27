package com.optilink.android.boilerplate.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import timber.log.Timber;

/**
 * Created by tanner.tan on 2016/1/27.
 */
public class ThrowableUtil {
    public static String printStackTrace(Throwable var1) {
        if (var1 == null) {
            return null;
        }

        String var2 = null;
        try {
            StringWriter var3 = new StringWriter();
            PrintWriter var4 = new PrintWriter(var3);
            var1.printStackTrace(var4);

            for (Throwable var5 = var1.getCause(); var5 != null; var5 = var5.getCause()) {
                var5.printStackTrace(var4);
            }

            var2 = var3.toString();
            var4.close();
            var3.close();
        } catch (Exception var6) {
            Timber.d(var6.getMessage());
        }

        return var2;
    }
}
