package com.nablarch.example.climan.common.jaxrs;

import com.nablarch.example.climan.common.exception.DuplicateRegistrationException;
import com.nablarch.example.climan.common.exception.SearchResultUpperLimitException;
import nablarch.common.dao.NoDataException;
import nablarch.fw.ExecutionContext;
import nablarch.fw.jaxrs.JaxRsErrorLogWriter;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class for customizing {@link JaxRsErrorLogWriter}.
 *
 * Ensures that exceptions returning a common error response do not output a log.
 *
 * @author TIS
 */
public class ClimanJaxRsErrorLogWriter extends JaxRsErrorLogWriter {

    /** Exceptional class for which logs are not output as responses are returned */
    private static final Set<Class> RESPONSE_EXCEPTIONS = new HashSet<Class>(
            Arrays.asList(SearchResultUpperLimitException.class,
                    DuplicateRegistrationException.class,
                    NoDataException.class));

    @Override
    public void write(HttpRequest request, HttpResponse response, ExecutionContext context, Throwable throwable) {
        if (RESPONSE_EXCEPTIONS.contains(throwable.getClass())) {
            return;
        }
        super.write(request, response, context, throwable);
    }
}
