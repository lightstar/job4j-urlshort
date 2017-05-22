 package ru.lightstar.urlshort.service;

 import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.context.SpringBootTest;
 import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 import org.springframework.security.core.authority.SimpleGrantedAuthority;
 import org.springframework.security.core.context.SecurityContextHolder;
 import org.springframework.test.annotation.DirtiesContext;
 import org.springframework.test.context.junit4.SpringRunner;
 import ru.lightstar.urlshort.TestConstants;

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
     * Test correctness of <code>setHashAlg</code> and <code>getHashAlg</code> methods.
     */
    @Test
    @DirtiesContext
    public void whenSetHashAlgThenItChanges() {
        this.utilService.setHashAlg(TestConstants.HASH_ALG);
        assertThat(this.utilService.getHashAlg(), is(TestConstants.HASH_ALG));
    }

    /**
     * Test correctness <code>getHashedPassword</code> method.
     */
    @Test
    public void whenGetHashedPasswordThenResult() {
        final String hashedPassword = this.utilService.getHashedPassword(TestConstants.OPEN_PASSWORD);

        assertThat(hashedPassword, not(equalTo(TestConstants.OPEN_PASSWORD)));
        assertThat(hashedPassword.length(), greaterThan(0));
    }

    /**
     * Test correctness of repeated calls to <code>getHashedPassword</code> method with the same argument.
     */
    @Test
    public void whenGetHashedPasswordTwiceWithSamePasswordThenResultIsTheSame() {
        final String hashedPassword1 = this.utilService.getHashedPassword(TestConstants.OPEN_PASSWORD);
        final String hashedPassword2 = this.utilService.getHashedPassword(TestConstants.OPEN_PASSWORD);

        assertThat(hashedPassword1, equalTo(hashedPassword2));
    }

    /**
     * Test correctness of repeated calls to <code>getHashedPassword</code> method with different arguments.
     */
    @Test
    public void whenGetHashedPasswordTwiceWithDifferentArgumentsThenResultIsDifferent() {
        final String hashedPassword1 = this.utilService.getHashedPassword(TestConstants.OPEN_PASSWORD);
        final String hashedPassword2 = this.utilService.getHashedPassword(TestConstants.OPEN_PASSWORD2);

        assertThat(hashedPassword1, not(equalTo(hashedPassword2)));
    }

    /**
     * Test correctness <code>getHashedPassword</code> method with empty argument.
     */
    @Test
    public void whenGetHashedEmptyPasswordThenEmptyResult() {
        final String hashedPassword = this.utilService.getHashedPassword("");

        assertThat(hashedPassword, isEmptyString());
    }

    /**
     * Test exception in <code>getHashedPassword</code> method when wrong algorithm is set.
     */
    @Test(expected = RuntimeException.class)
    @DirtiesContext
    public void whenGetHashedPasswordWithWrongAlgThenException() {
        this.utilService.setHashAlg(TestConstants.HASH_ALG);
        this.utilService.getHashedPassword(TestConstants.OPEN_PASSWORD);
    }

    /**
     * Test correctness of <code>getAuthName</code> method.
     */
    @Test
    @DirtiesContext
    public void whenGetAuthNameThenResult() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                TestConstants.ID, TestConstants.PASSWORD));
        final String authName = this.utilService.getAuthName();

        assertThat(authName, is(TestConstants.ID));
    }

    /**
     * Test correctness of <code>getAuthName</code> method when authentication object is null.
     */
    @Test
    @DirtiesContext
    public void whenGetAuthNameWithNullAuthenticationThenEmptyResult() {
        SecurityContextHolder.getContext().setAuthentication(null);
        final String authName = this.utilService.getAuthName();

        assertThat(authName, isEmptyString());
    }

    /**
     * Test correctness of <code>getAuthRole</code> method.
     */
    @Test
    @DirtiesContext
    public void whenGetAuthRoleThenResult() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                TestConstants.ID, TestConstants.PASSWORD,
                Collections.singleton(new SimpleGrantedAuthority(TestConstants.ROLE_USER))));
        final String authRole = this.utilService.getAuthRole();

        assertThat(authRole, is(TestConstants.ROLE_USER));
    }

    /**
     * Test correctness of <code>getAuthRole</code> method when authentication object is null.
     */
    @Test
    @DirtiesContext
    public void whenGetAuthRoleWithNullAuthenticationThenEmptyResult() {
        SecurityContextHolder.getContext().setAuthentication(null);
        final String authRole = this.utilService.getAuthRole();

        assertThat(authRole, isEmptyString());
    }

    /**
     * Test correctness of <code>getAuthRole</code> method when authorities are empty.
     */
    @Test
    @DirtiesContext
    public void whenGetAuthRoleWithEmptyAuthoritiesThenEmptyResult() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                TestConstants.ID, TestConstants.PASSWORD, Collections.emptyList()));
        final String authRole = this.utilService.getAuthRole();

        assertThat(authRole, isEmptyString());
    }

    /**
     * Test correctness of <code>getAuthRole</code> method when authority is null.
     */
    @Test
    @DirtiesContext
    public void whenGetAuthRoleWithNullAuthorityThenEmptyResult() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                TestConstants.ID, TestConstants.PASSWORD, Collections.singleton(() -> null)));
        final String authRole = this.utilService.getAuthRole();

        assertThat(authRole, isEmptyString());
    }
}