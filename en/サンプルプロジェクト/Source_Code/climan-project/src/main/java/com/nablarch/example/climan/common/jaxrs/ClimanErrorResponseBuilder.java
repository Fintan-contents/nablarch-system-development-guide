package com.nablarch.example.climan.common.jaxrs;

import com.nablarch.example.climan.common.exception.DuplicateRegistrationException;
import com.nablarch.example.climan.common.exception.SearchResultUpperLimitException;
import nablarch.common.dao.NoDataException;
import nablarch.core.message.ApplicationException;
import nablarch.core.message.MessageLevel;
import nablarch.core.message.MessageUtil;
import nablarch.core.validation.ValidationResultMessage;
import nablarch.fw.ExecutionContext;
import nablarch.fw.jaxrs.ErrorResponseBuilder;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for customizing {@link ErrorResponseBuilder}.
 *
 * Returns a common error response for exceptions processed together.
 *
 * @author TIS
 */
public class ClimanErrorResponseBuilder extends ErrorResponseBuilder {

    /** Converter for writing response */
    private ClimanJackson2BodyConverter climanJackson2BodyConverter;

    /**
     * Set converter for writing response.
     * @param climanJackson2BodyConverter: Converter for writing response
     */
    public void setClimanJackson2BodyConverter(ClimanJackson2BodyConverter climanJackson2BodyConverter) {
        this.climanJackson2BodyConverter = climanJackson2BodyConverter;
    }

    @Override
    public HttpResponse build(HttpRequest request, ExecutionContext context, Throwable throwable) {
        if (throwable instanceof ApplicationException) {
            return creataHttpResponse((ApplicationException) throwable);
        } else if (throwable instanceof SearchResultUpperLimitException) {
            return createHttpResponse(
                    HttpResponse.Status.BAD_REQUEST, "FB1999902", "errors.upper.limit",
                    ((SearchResultUpperLimitException) throwable).getLimit());
        } else if (throwable instanceof NoDataException) {
            return createHttpResponse(
                    HttpResponse.Status.NOT_FOUND, "FB1999903", "errors.nothing");
        } else if (throwable instanceof DuplicateRegistrationException) {
            return createHttpResponse(
                    HttpResponse.Status.CONFLICT, "FB1999904", "errors.register.duplicate");
        }
        return super.build(request, context, throwable);
    }

    /**
     * Generates HTTP response for common error response.
     * @param e: Application exception
     * @return: HTTP response for common error response
     */
    private HttpResponse creataHttpResponse(ApplicationException e) {
        List<String> messages = e.getMessages().stream().map(message -> {
                                            if (message instanceof ValidationResultMessage) {
                                                ValidationResultMessage vrm = (ValidationResultMessage) message;
                                                return vrm.getPropertyName() + ":" + vrm.formatMessage();
                                            }
                                            return message.formatMessage();
                                        }).collect(Collectors.toList());
        Error error = new Error("FB1999901", messages);
        return createHttpResponse(HttpResponse.Status.BAD_REQUEST, error);
    }

    /**
     * Generates HTTP response for common error response.
     * @param status: Status code
     * @param faultCode: Fault code
     * @param messageId: Message ID
     * @param options: Option for embedding message
     * @return HTTP response for common error response
     */
    private HttpResponse createHttpResponse(
            HttpResponse.Status status,
            String faultCode, String messageId, Object... options) {
        String message = MessageUtil.createMessage(MessageLevel.ERROR, messageId, options).formatMessage();
        Error error = new Error(faultCode, message);
        return createHttpResponse(status, error);
    }

    /**
     * Generates HTTP response for common error response.
     * @param status: Status code
     * @param error: Error
     * @return HTTP response for common error response
     */
    private HttpResponse createHttpResponse(HttpResponse.Status status, Error error) {
        return new HttpResponse().setStatusCode(status.getStatusCode())
                .setContentType(MediaType.APPLICATION_JSON)
                .write(climanJackson2BodyConverter.write(error));
    }

    /**
     * Class for retaining error details.
     */
    public static class Error {

        /** Fault code */
        private String faultCode;

        /** Message */
        private List<String> messages;

        /**
         * Generates error details.
         * @param faultCode: Fault code
         * @param message: Message
         */
        public Error(String faultCode, String message) {
            this(faultCode, Arrays.asList(message));
        }

        /**
         * Generates error details.
         * @param faultCode: Fault code
         * @param messages: Messages
         */
        public Error(String faultCode, List<String> messages) {
            this.faultCode = faultCode;
            this.messages = messages;
        }

        /**
         * Returns fault code.
         * @return: Fault code
         */
        public String getFaultCode() {
            return faultCode;
        }

        /**
         * Returns message.
         * @return: Message
         */
        public List<String> getMessages() {
            return messages;
        }
    }
}
