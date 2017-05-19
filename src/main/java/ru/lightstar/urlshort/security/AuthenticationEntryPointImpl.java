package ru.lightstar.urlshort.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Authentication entry point bean used for basic authentication.
 *
 * @author LightStar
 * @since 0.0.1
 */
@Component
public class AuthenticationEntryPointImpl extends BasicAuthenticationEntryPoint {

    /**
     * Inject realm name for basic authentication.
     *
     * @param realmName realm name.
     */
    @Value("URL shortener")
    @Override
    public void setRealmName(final String realmName) {
        super.setRealmName(realmName);
    }
}