package com.optilink.android.boilerplate;

/**
 * Created by tanner.tan on 2016/1/26.
 */
public interface ActionConf {
    // ============ Action type ============
    String ACTION_REPORT_ERROR = "ACTION_REPORT_ERROR";
    String ACTION_SHOW_PROGRESS_DIALOG = "ACTION_SHOW_PROGRESS_DIALOG";
    String ACTION_DISMISS_PROGRESS_DIALOG = "ACTION_DISMISS_PROGRESS_DIALOG";

    String ACTION_LOGIN = "ACTION_LOGIN";
    String ACTION_WORK_ORDER_TODO_LIST = "ACTION_WORK_ORDER_TODO_LIST";
    String ACTION_SHOW_WORK_ORDER = "ACTION_SHOW_WORK_ORDER";
    String ACTION_UPLOAD_TASK_ATTACHMENT = "ACTION_UPLOAD_TASK_ATTACHMENT";
    String ACTION_DOWNLOAD_ATTACHMENT = "ACTION_DOWNLOAD_ATTACHMENT";
    String ACTION_CONTRIBUTORS = "ACTION_CONTRIBUTORS";


    // ============ Action map key ============
    String ACTION_KEY_ONLY_ONE = "only_one_key";
}
