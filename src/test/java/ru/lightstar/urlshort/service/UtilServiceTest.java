 package ru.lightstar.urlshort.service;

 import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.context.SpringBootTest;
 import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 import org.springframework.security.core.authority.SimpleGrantedAuthority;
 import org.springframework.security.core.context.SecurityContextHolder;
 import org.springframework.test.context.junit4.SpringRunner;

 import java.util.Collections;

 import static org.hamcrest.Matchers.*;
 import static org.junit.Assert.assertThat;

/**
 * <code>UtilService</code> tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilServiceTest {

    /**
     * <code>UtilService</code> object used in all tests.
     */
    @Autowired
    private UtilService utilService;

    /**
     * Test correctness of content returned from <code>getRandomString</code> method.
     */
    @Test
    public void whenGetRandomStringThenItHasExpectedContent() {
        final String generatedString = this.utilService.getRandomString(10);

        assertThat(generatedString.length(), is(10));
        for (int i = 0; i < generatedString.length(); i++) {
            assertThat(generatedString.charAt(i), anyOf(
                    both(greaterThanOrEqualTo('0')).and(lessThanOrEqualTo('9')),
                    both(greaterThanOrEqualTo('a')).and(lessThanOrEqualTo('z')),
                    both(greaterThanOrEqualTo('A')).and(lessThanOrEqualTo('Z'))));
        }
    }

    /**
     * Test difference of content returned from repeated calls to <code>getRandomString</code> method.
     */
    @Test
    public void whenGetRandomStringTwiceThenTheyAreDifferent() {
        final String generatedString1 = this.utilService.getRandomString(10);
        final String generatedString2 = this.utilService.getRandomString(10);

        assertThat(generatedString1, not(equalTo(generatedString2)));
    }

    /**
     * Test correctness <code>getHashedPassword</code> method.
     */
    @Test
    public void whenGetHashedPasswordThenResult() {
        final String hashedPassword = this.utilService.getHashedPassword("testPassword");

        assertThat(hashedPassword, not(equalTo("testPassword")));
        assertThat(hashedPassword.length(), greaterThan(0));
    }

    /**
     * Test correctness of repeated calls to <code>getHashedPassword</code> method with the same argument.
     */
    @Test
    public void whenGetHashedPasswordTwiceWithSamePasswordThenResultIsTheSame() {
        final String hashedPassword1 = this.utilService.getHashedPassword("testPassword");
        final String hashedPassword2 = this.utilService.getHashedPassword("testPassword");

        assertThat(hashedPassword1, equalTo(hashedPassword2));
    }

    /**
     * Test correctness of repeated calls to <code>getHashedPassword</code> method with different arguments.
     */
    @Test
    public void whenGetHashedPasswordTwiceWithDifferentArgumentsThenResultIsDifferent() {
        final String hashedPassword1 = this.utilService.getHashedPassword("testPassword1");
        final String hashedPassword2 = this.utilService.getHashedPassword("testPassword2");

        assertThat(hashedPassword1, not(equalTo(hashedPassword2)));
    }

    /**
     * Test correctness of <code>getAuthName</code> method.
     */
    @Test
    public void whenGetAuthNameThenResult() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                "testUser", "testPassword"));
        final String authName = this.utilService.getAuthName();

        assertThat(authName, is("testUser"));
    }

    /**
     * Test correctness of <code>getAuthRole</code> method.
     */
    @Test
    public void whenGetAuthRoleThenResult() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                "testUser", "testPassword",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))));
        final String authRole = this.utilService.getAuthRole();

        assertThat(authRole, is("ROLE_USER"));
    }
}