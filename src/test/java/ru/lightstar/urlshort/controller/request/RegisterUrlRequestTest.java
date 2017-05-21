package ru.lightstar.urlshort.controller.request;

import org.junit.Test;
import ru.lightstar.urlshort.JsonTestHelper;
import ru.lightstar.urlshort.TestConstants;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

/**
 * <code>RegisterUrlRequest</code> class tests.
 *
 * @author Lightstar
 * @since 0.0.1
 */
public class RegisterUrlRequestTest {

    /**
     * <code>RegisterUrlRequest</code> object used in all tests.
     */
    private final RegisterUrlRequest request = new RegisterUrlRequest();

    /**
     * Test correctness of created empty request.
     */
    @Test
    public void whenCreateRequestThenAllFieldsInitialized() {
        assertThat(this.request.getLongUrl(), isEmptyString());
        assertThat(this.request.getRedirectType(), is(0));
    }

    /**
     * Test correctness of <code>setLongUrl</code> and <code>getLongUrl</code> methods.
     */
    @Test
    public void whenSetLongUrlThenItChanges() {
        this.request.setLongUrl(TestConstants.LONG_URL);
        assertThat(this.request.getLongUrl(), is(TestConstants.LONG_URL));
    }

    /**
     * Test correctness of <code>setRedirectType</code> and <code>getRedirectType</code> methods.
     */
    @Test
    public void whenSetRedirectTypeThenItChanges() {
        this.request.setRedirectType(TestConstants.REDIRECT_TYPE);
        assertThat(this.request.getRedirectType(), is(TestConstants.REDIRECT_TYPE));
    }

    /**
     * Test correctness of deserialization from JSON.
     */
    @Test
    public void whenDeserializeFromJsonThenResult() throws IOException {
        final String json = String.format("{\"url\":\"%s\",\"redirectType\":%d}", TestConstants.LONG_URL,
                TestConstants.REDIRECT_TYPE);
        final RegisterUrlRequest request = new JsonTestHelper().decode(json, RegisterUrlRequest.class);
        assertThat(request.getLongUrl(), is(TestConstants.LONG_URL));
        assertThat(request.getRedirectType(), is(TestConstants.REDIRECT_TYPE));
    }
}