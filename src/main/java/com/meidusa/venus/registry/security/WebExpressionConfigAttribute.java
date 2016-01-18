package com.meidusa.venus.registry.security;

import org.springframework.expression.Expression;
import org.springframework.security.access.ConfigAttribute;

/**
 * Created by huawei on 1/7/16.
 */
public class WebExpressionConfigAttribute implements ConfigAttribute {
    private final Expression authorizeExpression;

    public WebExpressionConfigAttribute(Expression authorizeExpression) {
        this.authorizeExpression = authorizeExpression;
    }

    Expression getAuthorizeExpression() {
        return authorizeExpression;
    }

    public String getAttribute() {
        return null;
    }

    @Override
    public String toString() {
        return authorizeExpression.getExpressionString();
    }
}
