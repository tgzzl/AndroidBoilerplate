package com.optilink.android.boilerplate.pojo;

import java.util.List;

/**
 * Created by tanner.tan on 2016/1/26.
 */
public class WorkOrderTodo extends BaseResponse {
    public List<WorkOrder> work_orders;

    public static class WorkOrder {
        public long id;
        public String status;
        public String sender_expected_time;
        public String sender_note;
        public String sender_enquiry_address;
        public String receiver_enquiry_address;

        @Override
        public String toString() {
            return "WorkOrder{" +
                    "id=" + id +
                    ", status='" + status + '\'' +
                    ", sender_expected_time='" + sender_expected_time + '\'' +
                    ", sender_note='" + sender_note + '\'' +
                    ", sender_enquiry_address='" + sender_enquiry_address + '\'' +
                    ", receiver_enquiry_address='" + receiver_enquiry_address + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WorkOrderTodo{" +
                "work_orders=" + work_orders +
                '}';
    }
}
