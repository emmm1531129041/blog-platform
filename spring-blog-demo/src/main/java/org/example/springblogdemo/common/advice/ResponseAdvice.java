package org.example.springblogdemo.common.advice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.example.springblogdemo.pojo.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

//我想在 Controller 返回数据之前，做一点处理...
//返回统一格式
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //如果已经是Result：
        //不要重复包装
        if (body instanceof Result<?>){
            return body;
        }
        if (body instanceof String){
            try {
                return objectMapper.writeValueAsString(Result.ok(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return Result.ok(body);
    }
}

