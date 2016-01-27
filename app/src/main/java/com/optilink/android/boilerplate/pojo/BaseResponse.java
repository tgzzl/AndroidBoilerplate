package com.optilink.android.boilerplate.pojo;

/**
 * Created by tanner.tan on 2016/1/26.
 */
public class BaseResponse {
    public int return_code;
    public String return_info;

    public boolean success() {
        return 0 == return_code;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "return_code=" + return_code +
                ", return_info='" + return_info + '\'' +
                '}';
    }
}
