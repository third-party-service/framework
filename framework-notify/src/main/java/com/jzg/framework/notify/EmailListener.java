package com.jzg.framework.notify;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: JZG
 * @date: 2016/12/30 12:12
 */
class EmailListener {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(EmailListener.class);

    /**
     * 邮件订阅
     *
     * @param emailInfo 邮件信息
     */
    @Subscribe
    public void sendEmail(EmailInfo emailInfo) {
        try {
            EmailUtils.sendMail(emailInfo);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
