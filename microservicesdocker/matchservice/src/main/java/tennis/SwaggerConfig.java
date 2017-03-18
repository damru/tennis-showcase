package tennis;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by damien on 05/03/2017.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerSettings() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                                                      .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
                                                      .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Kata Tennis API").description("API pour gérer un match de tennis")
                                   .contact(new Contact("Damien Rubio", "https://github.com/damru/kata-tennis/", "rubio.damien@gmail.com"))
                                   .license("Apache License Version 2.0").licenseUrl("https://github.com/damru/kata-tennis/LICENSE")
                                   .version("1.0").build();
    }

}
