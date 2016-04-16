/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.aop;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import cz.jh.journal.model.DBEntity;
import java.util.List;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interceptor for logging
 *
 * @author jan.horky
 */
public class MethodCallLogger {

    private static final Logger log = LoggerFactory.getLogger(MethodCallLogger.class.getName());

    @AroundInvoke
    public Object log(InvocationContext ctx) throws Exception {
        long start = System.currentTimeMillis();
        final String methodName = ctx.getMethod().getName();
        final String className = ctx.getTarget().getClass().getSimpleName();
        final String label = className.concat(".").concat(methodName);
        final List<String> params = Lists.transform(Lists.newArrayList(ctx.getParameters()), new Function<Object, String>() {

            @Override
            public String apply(Object input) {
                if (input == null) {
                    return "null";
                } else if (input instanceof DBEntity) {
                    return input.toString();
                } else {
                    return StringUtils.substring(input.toString(), 0, 150);
                }
            }
        });
        log.info("+++ BEGIN {}({})", label, Joiner.on(", ").useForNull("null").join(params));
        try {
            return ctx.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("--- END {}({}): {}ms", label, Joiner.on(", ").useForNull("null").join(params), System.currentTimeMillis() - start);
        }
    }

}
