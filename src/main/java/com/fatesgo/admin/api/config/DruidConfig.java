package com.fatesgo.admin.api.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置Driud数据源
 */
@Configuration
public class DruidConfig {

    @Bean
    //下面的注解表示将属性文件中前缀是spring.datasource的属性绑定到当前数据源
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid() {
        return new DruidDataSource();
    }

    /**
     * druid的强大在于有一套完整的监控配置,我们可以在这里配置一下,配置druid的后台监控需要配置
     * 一个servlet,我们可以直接使用servletRegistrationBean来配置,配置的servlet的名称
     * 是statViewServlet,
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean
                = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //可以在这个servlet中设置参数来定义后台的一些参数
        Map<String, String> initParms = new HashMap<>();
        //配置登录用户名
        initParms.put("loginUsername", "admin");
        //配置密码
        initParms.put("loginPassword", "123456");
        //配置访问权限,默认是所有都能访问
        initParms.put("allow", "");
        //配置拒绝访问的ip
        initParms.put("deny", "");
        bean.setInitParameters(initParms);
        return bean;
    }

    /**
     * 要使用druid的后台监控功能,还可以配置一个filter,它的名称是webStatFilter
     *
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String, String> initParms = new HashMap<>();
        //不拦截的资源
        initParms.put("exclusions", "*.js,*.css,/druid/*");
        bean.setInitParameters(initParms);
        //要拦截的请求
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }

}
