package com.jzg.framework.notify;

import com.jzg.framework.core.event.Event;

/**
 * @description: 邮件信息实体
 * @author: JZG
 * @date: 2016/12/30 13:42
 */
public class EmailInfo extends Event {
    /**
     * Getter for property 'fromEmail'.
     *
     * @return Value for property 'fromEmail'.
     */
    public String getFromEmail() {
        return fromEmail;
    }

    /**
     * Setter for property 'fromEmail'.
     *
     * @param fromEmail Value to set for property 'fromEmail'.
     */
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    /**
     * Getter for property 'password'.
     *
     * @return Value for property 'password'.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for property 'password'.
     *
     * @param password Value to set for property 'password'.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for property 'html'.
     *
     * @return Value for property 'html'.
     */
    public Boolean getHtml() {
        return isHtml;
    }

    /**
     * Setter for property 'html'.
     *
     * @param html Value to set for property 'html'.
     */
    public void setHtml(Boolean html) {
        isHtml = html;
    }

    /**
     * Getter for property 'toEmail'.
     *
     * @return Value for property 'toEmail'.
     */
    public String getToEmail() {
        return toEmail;
    }

    /**
     * Setter for property 'toEmail'.
     *
     * @param toEmail Value to set for property 'toEmail'.
     */
    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    /**
     * Getter for property 'subject'.
     *
     * @return Value for property 'subject'.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Setter for property 'subject'.
     *
     * @param subject Value to set for property 'subject'.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Getter for property 'content'.
     *
     * @return Value for property 'content'.
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter for property 'content'.
     *
     * @param content Value to set for property 'content'.
     */
    public void setContent(String content) {
        this.content = content;
    }

    private String fromEmail;
    private String password;
    private Boolean isHtml;
    private String toEmail;
    private String subject;
    private String content;
}
