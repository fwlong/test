package cn.jsjst.reimbursement.http;

import com.google.gson.Gson;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.jsjst.reimbursement.BuildConfig;
import cn.jsjst.reimbursement.utils.Logger;
import cn.jsjst.reimbursement.utils.UrlsFiled;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 类说明
 *
 * @author fengwl
 * @version [版本]
 * @see [相关类]
 * @since [模块]
 */
public class RetrofitManager {

    private static final int DEFAULT_TIME_OUT = 10;

    private Map<String, String> headers = new HashMap<String, String>();

    private OkHttpClient.Builder clientBuilder;
    private OkHttpClient mDefaultClient;
    private OkHttpClient mFixTimeOutClient;

    private final Retrofit.Builder retrofitBuilder;


    //Gson 的单例
    private final Gson mGson = new Gson();

    public static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    private RetrofitManager() {
        clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        retrofitBuilder = new Retrofit.Builder();
        initLog();
    }

    public Gson getGson() {
        return mGson;
    }

    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取默认的Retrofit对象，10s默认的连接超时时间,访问管家服务器接口地址
     *
     * @return m默认的Retrofit对象
     */
    public Retrofit getDefaultRetrofit() {
        if (mDefaultClient == null) {
            clientBuilder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
            mDefaultClient = clientBuilder.build();
        }
        return retrofitBuilder.baseUrl(UrlsFiled.SERVICE_URL)
                .client(mDefaultClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    /**
     * 获取特定超时时间的Retrofit 访问的URL为管家后台服务器地址
     *
     * @param timeout 自定义的超时时间
     * @return Retrofit对象
     */
    public Retrofit getFixedTimeoutRetrofit(int timeout) {
        if (mFixTimeOutClient == null) {
            clientBuilder.connectTimeout(timeout, TimeUnit.SECONDS);
            mFixTimeOutClient = clientBuilder.build();
        }
        return retrofitBuilder.baseUrl(UrlsFiled.SERVICE_URL)
                .client(mFixTimeOutClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 获取特定URL的Retrofit 超时时间默认为10s
     *
     * @param baseUrl 指定的URL
     * @return Retrofit对象
     */
    public Retrofit getSpecialUrlRetrofit(String baseUrl) {
        if (mDefaultClient == null) {
            clientBuilder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
            mDefaultClient = clientBuilder.build();
        }
        return retrofitBuilder.baseUrl(baseUrl)
                .client(mDefaultClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    /**
     * 添加请求头
     *
     * @param key   添加请求头的key
     * @param value 添加请求头的value
     */
    public void addHeader(String key, String value) {
        if (key == null) return;
        headers.put(key, value);
    }

    /**
     * 设置请求头
     *
     * @param headers 请求头的map
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * 移除某个请求头
     *
     * @param key 请求头的key值
     */
    public void removeHeader(String key) {
        if (key == null) return;
        if (headers.containsKey(key)) {
            headers.remove(key);
        }
    }

    private void syncRequestHeader() {
        if (clientBuilder == null) {
            clientBuilder = new OkHttpClient.Builder();
        }
        if (headers.size() > 0) {
            clientBuilder.addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request.Builder builder = request.newBuilder();
                    builder.headers(Headers.of(headers));
                    request = builder.build();
                    return chain.proceed(request);

                }
            });
        }
    }

    public Map<String, String> createHeaders() {
        headers.put(UrlsFiled.FLAG_HEADER_MPTSP, String.valueOf(System.currentTimeMillis()));
        return headers;
    }

    public OkHttpClient getOkHttpClient() {
        if (mDefaultClient == null) {
            clientBuilder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
            mDefaultClient = clientBuilder.build();
        }
        return mDefaultClient;
    }

    private void initLog() {
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(new LogIntercept());
        }
    }

    private class LogIntercept implements Interceptor {
        private final Charset UTF8 = Charset.forName("UTF-8");

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            RequestBody requestBody = request.body();
            boolean hasRequestBody = requestBody != null;

            Connection connection = chain.connection();
            Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
            Logger.d("start request" +
                    "\nrequest method = [" + request.method() + "]" +
                    "\nrequest url = [" + request.url() + "]" +
                    "\nrequest protocol = [" + protocol + "]");
            if (hasRequestBody) {
                if (requestBody.contentType() != null) {
                    Logger.d("request content type = [" + requestBody.contentType() + "]");
                }
            }

            Headers headers = request.headers();
            StringBuilder sBuilder = new StringBuilder("Headers:\n");
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(
                        name)) {
                    sBuilder.append(name);
                    sBuilder.append(" : ");
                    sBuilder.append(headers.value(i));
                    sBuilder.append("\n");
                }
            }
            Logger.d("request string builder = [" + sBuilder.toString() + "]");

            long startNs = System.nanoTime();
            Response response;
            try {
                response = chain.proceed(request);
            } catch (Exception e) {
                Logger.e("request failed, throw error = [" + e.getMessage() + "]");
                throw e;
            }
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
            String bodyResponse = "Response Code = [" + response.code() + "];\nResponse Size = ["
                    + bodySize + "];\nTook Request Time = [" + tookMs + "ms]";
            Logger.d(bodyResponse);
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    Logger.e(
                            "end request, couldn't decode the response body, charset is likely "
                                    + "malformed.");
                    return response;
                }
            }
            if (!isPlaintext(buffer)) {
                Logger.d("end request, the binary = [" + buffer.size() + "] byte body omitted)");
                return response;
            }

            if (contentLength != 0) {
                Logger.json("request result = " + buffer.clone().readString(charset));
            }

            Logger.d("the binary = [" + buffer.size() + "] byte body\nend request");
            return response;
        }

        private boolean isPlaintext(Buffer buffer) {
            try {
                Buffer prefix = new Buffer();
                long byteCount = buffer.size() < 64 ? buffer.size() : 64;
                buffer.copyTo(prefix, 0, byteCount);
                for (int i = 0; i < 16; i++) {
                    if (prefix.exhausted()) {
                        break;
                    }
                    int codePoint = prefix.readUtf8CodePoint();
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false;
                    }
                }
                return true;
            } catch (EOFException e) {
                return false; // Truncated UTF-8 sequence.
            }
        }
    }

}
