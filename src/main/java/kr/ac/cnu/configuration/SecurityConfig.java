package kr.ac.cnu.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by rokim on 2017. 5. 27..
 */
@EnableWebSecurity
//@EnableResourceServer
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll();
    }
//
//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(false);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return bean;
//    }

//    @Autowired
//    @Bean
//    public FilterRegistrationBean serviceMaintenanceFilter(ServiceMaintenanceConfig serviceMaintenanceConfig) {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        ServiceMaintenanceFilter filter = new ServiceMaintenanceFilter(serviceMaintenanceConfig);
//        registration.setFilter(filter);
//        registration.addUrlPatterns("/*");
//        registration.setName("serviceMaintenanceFilter");
//        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return registration;
//    }


}
    //    @Autowired
//    private OAuth2ClientContext oauth2ClientContext;

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .antMatcher("/**")
//                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/helloUser")
//                .authenticated();
//        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
//                .and()
//                .authorizeRequests()
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/favicon.ico", "/health").permitAll();
//    }

//    private static final String RESOURCE_ID = "my_rest_api";

//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId(RESOURCE_ID).stateless(false);
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.
//                anonymous().disable()
//                .requestMatchers().antMatchers("/user/**")
//                .and().authorizeRequests()
//                .antMatchers("/user/**").access("hasRole('ADMIN')")
//                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
//    }

//    private Filter ssoFilter() {
//        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login");
//        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
//        facebookFilter.setRestTemplate(facebookTemplate);
//        facebookFilter.setTokenServices(new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId()));
//        facebookFilter.setAuthenticationSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
//            System.out.println(authentication.getName());
//            System.out.println(authentication.getPrincipal());
//            System.out.println(authentication.isAuthenticated());
//        });
//        facebookFilter.setAuthenticationFailureHandler((httpServletRequest, httpServletResponse, e) -> {
//            System.out.println(httpServletRequest);
//            System.out.println(httpServletResponse);
//        });
//        return facebookFilter;
//    }
//
//    @Bean
//    @ConfigurationProperties("facebook.client")
//    public OAuth2ProtectedResourceDetails facebook() {
//        return new AuthorizationCodeResourceDetails();
//    }
//
//    @Bean
//    @ConfigurationProperties("facebook.resource")
//    public ResourceServerProperties facebookResource() {
//        return new ResourceServerProperties();
//    }
//
//    @Bean
//    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(filter);
//        registration.setOrder(-100);
//        return registration;
//    }
//
//}
