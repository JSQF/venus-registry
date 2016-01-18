package com.meidusa.venus.registry.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.ExpressionBasedFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by huawei on 1/3/16.
 */
//@Component
public class UcmFilterInvocationSecurityMetadataSource extends
        DefaultFilterInvocationSecurityMetadataSource {
    private final static Log logger = LogFactory
            .getLog(ExpressionBasedFilterInvocationSecurityMetadataSource.class);
    private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        System.out.println("get all attr");
        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
                .entrySet()) {
            allAttributes.addAll(entry.getValue());
        }
        return allAttributes;
    }

    public Collection<ConfigAttribute> getAttributes(Object object) {
        System.out.println("get attr");
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
                .entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public UcmFilterInvocationSecurityMetadataSource(
            LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap,
            SecurityExpressionHandler<FilterInvocation> expressionHandler) {
        super(processMap(requestMap, expressionHandler.getExpressionParser()));
        requestMap = processMap(requestMap, expressionHandler.getExpressionParser());

        Assert.notNull(expressionHandler,
                "A non-null SecurityExpressionHandler is required");
    }

    private static LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> processMap(
            LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap,
            ExpressionParser parser) {
        Assert.notNull(parser, "SecurityExpressionHandler returned a null parser object");

        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestToExpressionAttributesMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>(
                requestMap);

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
                .entrySet()) {
            RequestMatcher request = entry.getKey();
            Assert.isTrue(entry.getValue().size() == 1,
                    "Expected a single expression attribute for " + request);
            ArrayList<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>(1);
            String expression = entry.getValue().toArray(new ConfigAttribute[1])[0]
                    .getAttribute();
            logger.debug("Adding web access control expression '" + expression
                    + "', for " + request);
            try {
                attributes.add(new WebExpressionConfigAttribute(parser
                        .parseExpression(expression)));
            }
            catch (ParseException e) {
                throw new IllegalArgumentException("Failed to parse expression '"
                        + expression + "'");
            }

            requestToExpressionAttributesMap.put(request, attributes);
        }

        return requestToExpressionAttributesMap;
    }
}
