# 📝 SpringBlogdemo - 个人博客系统
> 基于 Spring Boot + MyBatis-Plus 开发的前后端分离博客系统:
本练习项目实现了一个简单的个人博客管理系统，用户可以进行
注册登录、博客文章发布、博客查看、博客修改、博客删除等操作。

----------------------------------------
# 📦 项目结构说明
spring-blog-demo
│
├── src
│   └── main
│       ├── java
│       │   └── org.example.springblogdemo
│       │
│       │       ├── SpringBlogDemoApplication.java
│       │       │       🚀 Spring Boot启动类
│       │       │
│       │       ├── config
│       │       │       ⚙️ Spring配置模块
│       │       │       └── AppConfig
│       │       │              - 注册登录拦截器
│       │       │              - 配置MVC相关功能
│       │       │
│       │       ├── controller
│       │       │       🎮 控制层
│       │       │       ├── UserController
│       │       │       │      - 用户登录
│       │       │       │      - 获取用户信息
│       │       │       │
│       │       │       └── BlogController
│       │       │              - 博客列表查询
│       │       │              - 博客详情查询
│       │       │              - 博客新增修改删除
│       │       │
│       │       ├── service
│       │       │       🔥 业务逻辑层
│       │       │       │
│       │       │       ├── UserInfoService
│       │       │       │      - 用户业务接口
│       │       │       │
│       │       │       ├── BlogInfoService
│       │       │       │      - 博客业务接口
│       │       │       │
│       │       │       └── impl
│       │       │              - Service接口实现类
│       │       │              - 编写核心业务逻辑
│       │       │
│       │       ├── mapper
│       │       │       🗄 数据访问层
│       │       │       ├── UserInfoMapper
│       │       │       │      - 操作用户表
│       │       │       │
│       │       │       └── BlogInfoMapper
│       │       │              - 操作博客表
│       │       │
│       │       ├── pojo
│       │       │       📦 数据对象模块
│       │       │       │
│       │       │       ├── dataobject
│       │       │       │      - 数据库实体类
│       │       │       │      - 对应数据库表字段
│       │       │       │
│       │       │       ├── request
│       │       │       │      - 接收前端请求参数
│       │       │       │
│       │       │       └── response
│       │       │              - 返回给前端的数据结构
│       │       │
│       │       └── common
│       │               🛠 公共功能模块
│       │
│       │               ├── advice
│       │               │      ⚠️ 全局处理
│       │               │      - 统一异常处理
│       │               │      - 统一返回格式
│       │               │
│       │               ├── enums
│       │               │      🔢 枚举类
│       │               │      - 管理业务状态码
│       │               │
│       │               ├── exception
│       │               │      ❌ 自定义异常
│       │               │      - BlogException
│       │               │
│       │               ├── interceptor
│       │               │      🔐 登录拦截
│       │               │      - JWT身份校验
│       │               │
│       │               └── util
│       │                      🔧 工具类
│       │                      ├── BeanTrans
│       │                      │      - 对象转换
│       │                      │
│       │                      ├── JwtUtils
│       │                      │      - JWT生成解析
│       │                      │
│       │                      └── SecurityUtils
│       │                             - 密码加密验证
│       │
│       └── resources
│               📁 项目资源文件
│
│               ├── application.yml
│               │      - Spring Boot配置文件
│               │
│               ├── static
│               │      - 前端静态资源
│               │      ├── html页面
│               │      ├── css
│               │      ├── js
│               │      └── 图片资源
│               │
│               └── mapper
│                      - MyBatis XML文件(本项目未使用)
│
├── pom.xml
│       📦 Maven依赖管理文件
│
└── README.md
        📖 项目说明文档
----------------------------------------------
🔄 项目整体流程
这个博客系统本质就是一个典型的 Spring Boot 后端项目：
                用户操作
                   ↓
      前端页面(html + js + ajax)   
                   ↓
              Controller层
                   ↓
               Service层
                   ↓
                Mapper层
                   ↓
              MySQL数据库
         
🛠 公共模块辅助流程
                   用户请求
                      ↓
              LoginInterceptor
                      ↓
                  JWT登录认证
                      ↓
用户请求 → Controller → Service → Mapper → MySQL

⚠️ 异常处理流程
                业务代码发生异常
                      ↓
                 BlogException
                      ↓
                ExceptionAdvice
                      ↓ 
             统一返回 Result 格式

📦 返回格式统一
              Controller返回数据
                      ↓
                ResponseAdvice
                      ↓
                  Result<T>
                      ↓
               返回给前端统一格式
🔐 登录认证流程
用户登录
    ↓
UserController.java
    ↓
UserInfoServiceImpl.java
    ↓
SecurityUtils.java
(密码校验)
    ↓
JwtUtils.java
(生成Token)
    ↓
返回UserLoginResponse.java
-----
用户后续请求：
前端携带Token
        ↓
LoginInterceptor.java
        ↓
JwtUtils.java
        ↓
验证成功
        ↓
放行请求


