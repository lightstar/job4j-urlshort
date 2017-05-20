package ru.lightstar.urlshort.controller.request;

import org.junit.Test;
import ru.lightstar.urlshort.JsonTestHelper;

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
        this.request.setLongUrl("longUrl");
        assertThat(this.request.getLongUrl(), is("longUrl"));
    }

    /**
     * Test correctness of <code>setRedirectType</code> and <code>getRedirectType</code> methods.
     */
    @Test
    public void whenSetRedirectTypeThenItChanges() {
        this.request.setRedirectType(301);
        assertThat(this.request.getRedirectType(), is(301));
    }

    /**
     * Test correctness of deserialization from JSON.
     */
    @Test
    public void whenDeserializeFromJsonThenResult() throws IOException {
        final String json = "{\"url\":\"test\",\"redirectType\":301}";
        final RegisterUrlRequest request = new JsonTestHelper().decode(json, RegisterUrlRequest.class);
        assertThat(request.getLongUrl(), is("test"));
        assertThat(request.getRedirectType(), is(301));
    }
}