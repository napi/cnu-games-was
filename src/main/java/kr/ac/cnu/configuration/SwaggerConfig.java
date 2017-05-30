package kr.ac.cnu.configuration;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.ZonedDateTime;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by rokim on 2017. 5. 30..
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(ZonedDateTime.class, String.class)
                .apiInfo(apiInfo())
                .select()
                .paths(paths())
                .build();
    }


    private Predicate<String> paths() {
        return or(
                regex("/api.*"),
                regex("/hello*"),
                regex("/cnu*"),
                regex("/health"),
                regex("/info")
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("CNU Board API SERVER.")
                .description("This is WAS for CNU Board.")
                .contact("rokim@riotgames.com")
                .version("1.0")
                .build();
    }
}