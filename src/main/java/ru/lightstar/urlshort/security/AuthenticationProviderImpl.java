package ru.lightstar.urlshort.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.lightstar.urlshort.exception.AccountNotFoundException;
import ru.lightstar.urlshort.model.Account;
import ru.lightstar.urlshort.service.ConfigService;
import ru.lightstar.urlshort.service.UtilService;

import java.util.Collections;
import java.util.List;

/**
 * Authentication provider bean used for authentication by login and password.
 *
 * @author LightStar
 * @since 0.0.1
 */
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    /**
     * Application configuration service used here to retrieve <code>Account</code> object.
     */
    private final ConfigService configService;

    /**
     * Util service used here to hash password.
     */
    private final UtilService utilService;

    /**
     * Constructs <code>AuthenticationProviderImpl</code> object.
     *
     * @param configService injected configuration service bean.
     * @param utilService injected util service bean.
     */
    @Autowired
    public AuthenticationProviderImpl(final ConfigService configService, final UtilService utilService) {
        this.configService = configService;
        this.utilService = utilService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String login = authentication.getName();
        final String password = this.utilService.getHashedPassword(authentication.getCredentials().toString());

        try {
            final Account account = this.configService.getAccountById(login);

            if (!account.getPassword().equals(password)) {
                return null;
            }

            final GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
            final List<GrantedAuthority> grantedAuthorities = Collections.singletonList(grantedAuthority);

            return new UsernamePasswordAuthenticationToken(login, password, grantedAuthorities);
        } catch (AccountNotFoundException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(final Class<?> authenticationClass) {
        return authenticationClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
