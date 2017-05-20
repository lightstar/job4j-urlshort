package ru.lightstar.urlshort.controller.response;

import org.junit.Test;
import ru.lightstar.urlshort.JsonTestHelper;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.*;

/**
 * <code>RegisterUrlResponse</code> class tests.
 *
 * @author Lightstar
 * @since 0.0.1
 */
public class RegisterUrlResponseTest {

    /**
     * <code>RegisterUrlResponse</code> object used in all tests.
     */
    private final RegisterUrlResponse response = new RegisterUrlResponse();

    /**
     * Helper object used to test JSON serialization.
     */
    private final JsonTestHelper jsonTestHelper = new JsonTestHelper();

    /**
     * Test correctness of created empty response.
     */
    @Test
    public void whenCreateResponseThenAllFieldsInitialized() {
        assertThat(this.response.getShortUrl(), isEmptyString());
    }

    /**
     * Test correctness of <code>setShortUrl</code> and <code>getShortUrl</code> methods.
     */
    @Test
    public void whenSetShortUrlThenItChanges() {
        this.response.setShortUrl("shortUrl");
        assertThat(this.response.getShortUrl(), is("shortUrl"));
    }

    /**
     * Test correctness of serialization to JSON.
     */
    @Test
    public void whenSerializeToJsonThenResult() throws IOException {
        this.response.setShortUrl("shortUrl");
        final String json = this.jsonTestHelper.encode(this.response);
        assertThat(this.jsonTestHelper.textField(json, "shortUrl"), is("shortUrl"));
    }
}