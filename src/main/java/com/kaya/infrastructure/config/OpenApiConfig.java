package com.kaya.infrastructure.config;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        tags = {
                @Tag(name="property", description="Property operations."),
                @Tag(name="user", description="User operations.")
        },
        info = @Info(
                title="KaYa API",
                version = "1.0.0",
                contact = @Contact(
                        name = "KaYa Support",
                        url = "http://kaya.com/contact",
                        email = "techsupport@kaya.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class OpenApiConfig extends Application {
}