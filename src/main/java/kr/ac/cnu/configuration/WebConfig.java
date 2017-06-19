package kr.ac.cnu.configuration;

import kr.ac.cnu.annotation.CnuLogin;
import kr.ac.cnu.domain.CnuUser;
import kr.ac.cnu.domain.facebook.FacebookUser;
import kr.ac.cnu.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by rokim on 2017. 5. 27..
 */
@Configuration
@EnableWebMvc
@Slf4j
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired private UserRepository userRepository;
    @Autowired private UserOperator userOperator;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE")
                .allowedHeaders("header1", "header2", "header3", "token")
                .exposedHeaders("header1", "header2", "token");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).excludePathPatterns("/swagger-ui.html*", "/**/webjars/**", "/console/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginArgumentResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/WEB-INF/resources/");
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public HandlerMethodArgumentResolver loginArgumentResolver() {
        HandlerMethodArgumentResolver resolver = new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter methodParameter) {
                return methodParameter.hasParameterAnnotation(ApiIgnore.class) && methodParameter.getParameterType().equals(CnuUser.class);
            }

            @Override
            public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
                String accessToken = nativeWebRequest.getHeader("token");

                FacebookUser facebookUser = userOperator.getCnuUserFromAccessToken(accessToken);
                CnuUser cnuUser = findAndCreateCnuUser(facebookUser);

                return cnuUser;
            }
        };

        return resolver;
    }

    @Bean
    public HandlerInterceptor loginInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object obj) throws Exception {
                if (obj instanceof HandlerMethod == false) {
                    return true;
                }

                if (((HandlerMethod)obj).hasMethodAnnotation(CnuLogin.class)) {
                    String accessToken = httpServletRequest.getHeader("token");

                    FacebookUser facebookUser = userOperator.getCnuUserFromAccessToken(accessToken);

                    if (facebookUser == null) {
                        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return false;
                    }

                    CnuUser cnuUser = findAndCreateCnuUser(facebookUser);

                    UserContext.setUser(cnuUser);
                }

                return true;
            }
        };
    }

    private CnuUser findAndCreateCnuUser(FacebookUser facebookUser) {
        CnuUser cnuUser = userRepository.findByUserId(facebookUser.getUserId());
        if (cnuUser == null) {
            cnuUser = new CnuUser();
            cnuUser.setUserId(facebookUser.getUserId());
            cnuUser.setEmail(facebookUser.getEmail());
            cnuUser.setPictureUrl(facebookUser.getPicture());
            cnuUser.setName(facebookUser.getName());
            cnuUser.setGender(facebookUser.getGender());
            cnuUser.setLikeAt(new Date());
            cnuUser = userRepository.save(cnuUser);
        }
        return cnuUser;
    }
}