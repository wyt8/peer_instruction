package com.zch1001.pi.utils;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
public class GlobalRequestInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(GlobalRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 包装请求，支持缓存请求体
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }

        // 读取请求体内容
        if ("application/json".equalsIgnoreCase(request.getContentType())) {
            String body = getRequestBody((ContentCachingRequestWrapper) request);
            logger.info("Intercepted Request Body:\n{}", body);

            // 如果需要进一步解析，可以通过 ObjectMapper 或其他工具类解析 JSON
            // 例如，将 JSON 转换为 Java 对象
            // ObjectMapper mapper = new ObjectMapper();
            // UserRequest userRequest = mapper.readValue(body, UserRequest.class);
            // logger.info("Parsed Object: {}", ObjectReader.readObjectFields(userRequest));
        }

        return true; // 继续发送请求到控制器
    }

    /**
     * 获取请求体内容
     */
    private String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
        try {
            byte[] buffer = requestWrapper.getContentAsByteArray();
            if (buffer.length > 0) {
                return new String(buffer, requestWrapper.getCharacterEncoding());
            }
        } catch (Exception e) {
            logger.error("Failed to read request body", e);
        }
        return "";
    }
}
