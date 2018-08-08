# bobo-security
集成 spring securit, spring security oauth 和 spring social，实现 用户名密码登录，手机验证码登录，社交账号登录，基于jwt的sso，集群session管理等功能。



# 实现自定义功能
向spring容器注册以下接口的实现，可以替换默认的处理逻辑

1.密码加密解密策略
org.springframework.security.crypto.password.PasswordEncoder

2.表单登录用户信息读取逻辑
org.springframework.security.core.userdetails.UserDetailsService

3.社交登录用户信息读取逻辑
org.springframework.social.security.SocialUserDetailsService

4.Session失效时的处理策略
org.springframework.security.web.session.InvalidSessionStrategy

5.并发登录导致前一个session失效时的处理策略配置
org.springframework.security.web.session.SessionInformationExpiredStrategy

6.退出时的处理逻辑
org.springframework.security.web.authentication.logout.LogoutSuccessHandler

7.短信发送的处理逻辑
com.bobo.security.core.validate.code.sms.SmsCodeSender

8.向spring容器注册名为imageValidateCodeGenerator的bean，可以替换默认的图片验证码生成逻辑,bean必须实现以下接口
com.bobo.security.core.validate.code.ValidateCodeGenerator

9.如果spring容器中有下面这个接口的实现，则在社交登录无法确认用户时，用此接口的实现自动注册用户，不会跳到注册页面
org.springframework.social.connect.ConnectionSignUp


# start

1.引入依赖(pom.xml)
<dependency>
	<groupId>com.bobo.security</groupId>
	<artifactId>bobo-security-web</artifactId>
	<version>1.0.0-SNAPSHOT</version>
</dependency>

2.配置系统(参见 application-example.properties)

3.增加UserDetailsService接口实现

4.如果需要记住我功能，需要创建数据库表(参见 db.sql)

5.如果需要社交登录功能，需要以下额外的步骤
1).配置appId和appSecret
2).创建并配置用户注册页面，并实现注册服务(需要配置访问权限)，注意在服务中要调用ProviderSignInUtils的doPostSignUp方法。
3).添加SocialUserDetailsService接口实现
4).创建社交登录用的表 (参见 db.sql)

# 注意

因为spring启动的时候会扫描启动类下的包，所以需要加入

@SpringBootApplication(scanBasePackages = {"com.bobo"})
方便加载实体类