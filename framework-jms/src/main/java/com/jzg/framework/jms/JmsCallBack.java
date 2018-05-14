package com.jzg.framework.jms;

/**
 * JmsCallBack
 */
public interface JmsCallBack {
    /**
     * 具体执行业务逻辑
     * @param json  jms传输的消息
     * @return
     */
    boolean run(String json);
}
