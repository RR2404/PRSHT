package moe.prashast.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "app.login.session")
@Data
public class LoginSessionProperties {
    @Value("${app.login.session.db.enabled}")
    private boolean dbEnabled;


}