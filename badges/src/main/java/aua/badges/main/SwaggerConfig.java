package aua.badges.main;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

@SpringBootApplication
@Configuration
public class SwaggerConfig {

    //http://localhost:9095/swagger-ui.html
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Account API")
                .description("From Zero to Hero ")
                .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
                .contact(new Contact("Davit", "", ""))
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
                .version("2.0")
                .build();
    }


}
