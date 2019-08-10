package com.meiliwu.installer.rx;

import android.content.Context;
import android.net.ParseException;
import android.nfc.FormatException;

import com.google.gson.JsonParseException;
import com.meiliwu.installer.exception.ServerException;
import com.meiliwu.installer.exception.UnifyThrowable;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;


/**
 * @description :通过builder方式创建RxErrorHandler
 */
public class RxErrorHandler {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    private static final int ACCESS_DENIED = 302;
    private static final int HANDLE_ERROR = 417;
    private ResponseErrorListener responseErrorListener;
    private Context context;

    public Context getContext() {
        return context;
    }

    public RxErrorHandler(Builder builder) {
        this.responseErrorListener = builder.responseErrorListener;
        this.context = builder.context;
    }

    public static Builder builder() {

        return new Builder();
    }

    public void handleError(Throwable e) {
        if (responseErrorListener != null) {
            UnifyThrowable ex;
            /*ConnectException*/
            if (e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                ex = new UnifyThrowable(e, ERROR.HTTP_ERROR);
                switch (httpException.code()) {
                    case UNAUTHORIZED:
                        ex.setMessage("未授权的请求");
                        break;
                    case FORBIDDEN:
                        ex.setMessage("禁止访问");
                        break;
                    case NOT_FOUND:
                        ex.setMessage("服务器地址未找到");
                        break;
                    case REQUEST_TIMEOUT:
                        ex.setMessage("请求超时");
                        break;
                    case GATEWAY_TIMEOUT:
                        ex.setMessage("网关响应超时");
                        break;
                    case INTERNAL_SERVER_ERROR:
                        ex.setMessage("服务器出错");
                        break;
                    case BAD_GATEWAY:
                        ex.setMessage("无效的请求");
                        break;
                    case SERVICE_UNAVAILABLE:
                        ex.setMessage("服务器不可用");
                        break;
                    case ACCESS_DENIED:
                        ex.setMessage("访问拒绝");
                        break;
                    case HANDLE_ERROR:
                        ex.setMessage("接口处理失败");
                        break;
                    default:
                        ex.setMessage(e.getMessage());
                        break;
                }
                ex.setCode(httpException.code());

            } else if (e instanceof ServerException) { //server exception
                ServerException resultException = (ServerException) e;
                ex = new UnifyThrowable(resultException, resultException.code);
                ex.setMessage(resultException.getMessage());

            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException) {
                ex = new UnifyThrowable(e, ERROR.PARSE_ERROR);
                ex.setMessage("解析错误");

            } else if (e instanceof ConnectException) {
                ex = new UnifyThrowable(e, ERROR.NETWORK_ERROR);
                ex.setMessage("连接失败");

            } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
                ex = new UnifyThrowable(e, ERROR.SSL_ERROR);
                ex.setMessage("证书验证失败");

            } else if (e instanceof java.security.cert.CertPathValidatorException) {
                ex = new UnifyThrowable(e, ERROR.SSL_NOT_FOUND);
                ex.setMessage("证书路径没找到");

            } else if (e instanceof ConnectTimeoutException) {
                ex = new UnifyThrowable(e, ERROR.TIMEOUT_ERROR);
                ex.setMessage("连接超时");

            } else if (e instanceof java.net.SocketTimeoutException) {
                ex = new UnifyThrowable(e, ERROR.TIMEOUT_ERROR);
                ex.setMessage("连接超时");

            } else if (e instanceof ClassCastException) {
                ex = new UnifyThrowable(e, ERROR.FORMAT_ERROR);
                ex.setMessage("类型转换出错");

            } else if (e instanceof NullPointerException) {
                ex = new UnifyThrowable(e, ERROR.NULL);
                ex.setMessage("数据有空");

            } else if (e instanceof FormatException) {
                FormatException resultException = (FormatException) e;
                ex = new UnifyThrowable(resultException, ERROR.FORMAT_ERROR);
                ex.setMessage(resultException.getMessage());

            } else {
                ex = new UnifyThrowable(e, ERROR.UNKNOWN);
                ex.setMessage(e.getLocalizedMessage());
            }
            if (ex != null) {
                responseErrorListener.handlerResponseError(context,ex);
            }
        }
    }

    public static class Builder {
        private ResponseErrorListener responseErrorListener;

        private Context context;

        public Builder() {
        }

        public Builder with(Context context) {
            this.context = context;
            return this;
        }

        public Builder responseErrorListener(ResponseErrorListener responseErrorListener) {
            this.responseErrorListener = responseErrorListener;
            return this;
        }

        public RxErrorHandler build() {
            if (responseErrorListener == null) {
                throw new IllegalStateException("responseErrorListener is required");
            }
            return new RxErrorHandler(this);
        }

    }

    /**
     * 约定异常
     */
    public static class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORK_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;

        /**
         * 证书未找到
         */
        public static final int SSL_NOT_FOUND = 1007;

        /**
         * 出现空值
         */
        public static final int NULL = -100;

        /**
         * 格式错误
         */
        public static final int FORMAT_ERROR = 1008;
    }
}
