package br.com.gra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        SecurityScheme securityScheme = new SecurityScheme()
                .type(Type.HTTP)
                .scheme("basic")
                .in(In.HEADER)
                .name("Authorization");

        return new OpenAPI()
                .info(new Info()
                        .title("Golden Raspberry Awards API")
                        .version("1.0.0")
                        .contact(new Contact().name("Eleandro Vanelli").email("vanelli26@gmail.com").url("https://www.vanelli.dev/"))
                        .description("API para obter informações sobre os vencedores do prêmio Pior Filme do Golden Raspberry Awards.")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org"))
                        .termsOfService("https://swagger.io/terms/"))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("basicAuth", securityScheme));
    }
}
