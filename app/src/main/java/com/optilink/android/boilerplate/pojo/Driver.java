package com.optilink.android.boilerplate.pojo;

/**
 * Created by tanner.tan on 2016/1/26.
 */
public class Driver extends BaseResponse {
    public String uid;
    public String token;
    public int sign_count;
    public boolean first_login;
    public DriverInfo driver_info;

    public static class DriverInfo {
        public long id;
        public int star;
        public String uid;
        public String name;
        public String mobile;
        public String status;
        public String baidu_push_device_id;

        @Override
        public String toString() {
            return "DriverInfo{" +
                    "id=" + id +
                    ", star=" + star +
                    ", uid='" + uid + '\'' +
                    ", name='" + name + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", status='" + status + '\'' +
                    ", baidu_push_device_id='" + baidu_push_device_id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Driver{" +
                "uid='" + uid + '\'' +
                ", token='" + token + '\'' +
                ", sign_count=" + sign_count +
                ", first_login=" + first_login +
                ", driver_info=" + driver_info +
                '}';
    }
}