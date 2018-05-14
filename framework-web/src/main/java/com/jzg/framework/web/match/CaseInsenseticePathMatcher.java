package com.jzg.framework.web.match;

import org.springframework.util.AntPathMatcher;

import java.util.Map;

/**
 * @description: 忽略大小写
 * @author: JZG
 * @date: 2017/1/3 12:10
 */
public class CaseInsenseticePathMatcher extends AntPathMatcher {
    @Override
    protected boolean doMatch(String pattern, String path, boolean fullMatch, Map<String, String> uriTemplateVariables) {
        return super.doMatch(pattern.toLowerCase(), path.toLowerCase(), fullMatch, uriTemplateVariables);
    }
}

