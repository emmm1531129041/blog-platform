package org.example.springblogdemo.pojo.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


//接收前端参数
@Data
public class AddBlogInfoRequest {
    @NotNull(message = "作者ID不能为空")
    @Min(1)
    private Integer userId;
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "内容不能为空")
    private String content;

}
