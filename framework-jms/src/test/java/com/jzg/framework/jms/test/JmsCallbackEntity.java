package com.jzg.framework.jms.test;

import com.jzg.framework.jms.JmsCallBack;

public class JmsCallbackEntity implements JmsCallBack {
    /**
     * 具体执行业务逻辑
     *
     * @param json jms传输的消息
     * @return
     */
    @Override
    public boolean run(String json) {
        System.out.println("running bussiness process.. " + json);
        return false;
    }
}
