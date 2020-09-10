package example.demo.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class SwaggerConfig {

    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.SWAGGER_2).host("").apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/.*")).build().protocols(Sets.newHashSet("http"));
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("Client-Address REST API (build time:" + FORMAT.format(new Date()) + ")")
                .description("Swagger interface")
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {

        return UiConfigurationBuilder.builder()
                .displayRequestDuration(true)
                .validatorUrl(null)
                .build();
    }
}
