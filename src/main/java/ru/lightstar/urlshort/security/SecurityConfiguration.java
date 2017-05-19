package ru.lightstar.urlshort.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Configuration class for spring security.
 *
 * @author LightStar
 * @since 0.0.1
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Authentication entry point bean needed for basic authentication.
     */
    private final AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * Authentication provider bean used for authentication by login and password.
     */
    private final AuthenticationProvider authenticationProvider;

    /**
     * Constructs <code>SecurityConfiguration</code> object.
     *
     * @param authenticationEntryPoint authentication entry point bean.
     * @param authenticationProvider authentication provider bean.
     */
    @Autowired
    public SecurityConfiguration(final AuthenticationEntryPoint authenticationEntryPoint,
                                 final AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/register").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/statistic/*").hasRole("USER")
                .antMatchers("/").permitAll()
                .and()
                .csrf()
                .disable()
                .httpBasic()
                .authenticationEntryPoint(this.authenticationEntryPoint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationProvider);
    }
}
