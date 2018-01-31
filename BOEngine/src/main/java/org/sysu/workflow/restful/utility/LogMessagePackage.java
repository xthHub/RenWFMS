package org.sysu.workflow.restful.utility;

import java.sql.Timestamp;

/**
 * Date  : 2018/1/29
 * Usage : Log package.
 */
final class LogMessagePackage {
    /**
     * Create a new log message package.
     * @param rtid process rtid
     * @param msg message text
     * @param label message label
     * @param type message type
     * @param ts timestamp
     */
    LogMessagePackage(String rtid, String msg, String label, LogUtil.LogLevelType type, java.sql.Timestamp ts) {
        this.Rtid = rtid;
        this.Message = msg;
        this.Label = label;
        this.Level = type;
        this.Timestamp = ts;
    }

    /**
     * Log binding process rtid.
     */
    public String Rtid;

    /**
     * Log message text.
     */
    public String Message;

    /**
     * Log label.
     */
    public String Label;

    /**
     * Log level type.
     */
    public LogUtil.LogLevelType Level;

    /**
     * Log timestamp.
     */
    public Timestamp Timestamp;
}
