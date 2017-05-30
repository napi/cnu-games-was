package kr.ac.cnu.configuration;

import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.restclient.FacebookClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Created by rokim on 2017. 5. 27..
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "OPTION", "PUT", "DELETE")
                .allowedHeaders("header1", "header2", "header3")
                .exposedHeaders("header1", "header2");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(facebookArgumentResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("**/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("**/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    @Autowired
    public FacebookClient facebookClient;

    @Bean
    public HandlerMethodArgumentResolver facebookArgumentResolver() {
        HandlerMethodArgumentResolver resolver = new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter methodParameter) {
                return methodParameter.hasParameterAnnotation(ApiIgnore.class) && methodParameter.getParameterType().equals(CnuUser.class);
            }

            @Override
            public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
                System.out.println("resolveArgument");
                String accessToken = nativeWebRequest.getHeader("token");
                System.out.println(accessToken);

                String result = facebookClient.callFacebookProfile(accessToken);

                CnuUser cnuUser = new CnuUser();
                cnuUser.setUserId(result);
                System.out.println(result);
                return cnuUser;
            }
        };

        return resolver;
    }

}