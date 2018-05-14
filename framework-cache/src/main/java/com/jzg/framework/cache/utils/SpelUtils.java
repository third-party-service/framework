package com.jzg.framework.cache.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

/**
 *
 */
public final class SpelUtils {
    /**
     * SPEL解析器
     */
    private static ExpressionParser parser = new SpelExpressionParser();

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(SpelUtils.class);


    private SpelUtils() {

    }


    // String key = "'User.' + #userId";
    // int userId = 100001;

    /**
     * 生成缓存主键  支持 SPEL 语法
     * 例如: 属性 key = "'User.' + #userId";
     * 参数 long userId = 100001;
     * 则解析后key = "User.100001"
     *
     * @param key        主键
     * @param condition  条件
     * @param paramNames 参数名数组
     * @param arguments  参数值
     * @return
     */
    public static String getKey(String key, String condition, String[] paramNames, Object[] arguments) {
        Assert.notNull(key);

        try {
            if (!checkCondition(condition, paramNames, arguments)) {
                return null;
            }
            Expression expression = parser.parseExpression(key);
            if (paramNames == null || paramNames.length <= 0) {
                return expression.getExpressionString();
            } else {
                EvaluationContext context = new StandardEvaluationContext();
                int length = paramNames.length;
                if (length > 0) {
                    for (int i = 0; i < length; i++) {
                        context.setVariable(paramNames[i], arguments[i]);
                    }
                }

                return expression.getValue(context, String.class);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 校验条件
     *
     * @param condition  条件表达式
     * @param paramNames 参数数组
     * @param arguments  参数值
     * @return
     */
    public static boolean checkCondition(String condition, String[] paramNames, Object[] arguments) {
        if (condition.length() < 1) {
            return true;
        }
        Expression expression = parser.parseExpression(condition);
        EvaluationContext context = new StandardEvaluationContext();
        int length = paramNames.length;
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                context.setVariable(paramNames[i], arguments[i]);
            }
        }
        return expression.getValue(context, boolean.class);
    }

}
