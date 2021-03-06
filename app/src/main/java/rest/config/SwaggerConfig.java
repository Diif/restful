package rest.config;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rest.model.entities.Error;
import rest.model.entities.ShopUnitWithChildren;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api(TypeResolver typeResolver){
        return new Docket(DocumentationType.SWAGGER_2)
                .additionalModels(
                        typeResolver.resolve(Void.class),
                        typeResolver.resolve(Error.class),
                        typeResolver.resolve(ShopUnitWithChildren.class)
                )
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(Void.class)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }
}
