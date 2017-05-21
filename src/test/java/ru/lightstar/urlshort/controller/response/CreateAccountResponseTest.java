package ru.lightstar.urlshort.controller.response;

import org.junit.Test;
import ru.lightstar.urlshort.JsonTestHelper;
import ru.lightstar.urlshort.TestConstants;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * <code>CreateAccountResponse</code> class tests.
 *
 * @author Lightstar
 * @since 0.0.1
 */
public class CreateAccountResponseTest {

    /**
     * <code>CreateAccountResponse</code> object used in all tests.
     */
    private final CreateAccountResponse response = new CreateAccountResponse();

    /**
     * Helper object used to test JSON serialization.
     */
    private final JsonTestHelper jsonTestHelper = new JsonTestHelper();

    /**
     * Test correctness of created empty response.
     */
    @Test
    public void whenCreateResponseThenAllFieldsInitialized() {
        assertThat(this.response.isSuccess(), is(false));
        assertThat(this.response.getDescription(), isEmptyString());
        assertThat(this.response.getPassword(), isEmptyString());
    }

    /**
     * Test correctness of <code>setSuccess</code> and <code>isSuccess</code> methods.
     */
    @Test
    public void whenSetSuccessThenItChanges() {
        this.response.setSuccess(true);
        assertThat(this.response.isSuccess(), is(true));
    }

    /**
     * Test correctness of <code>setDescription</code> and <code>getDescription</code> methods.
     */
    @Test
    public void whenSetDescriptionThenItChanges() {
        this.response.setDescription(TestConstants.DESCRIPTION);
        assertThat(this.response.getDescription(), is(TestConstants.DESCRIPTION));
    }

    /**
     * Test correctness of <code>setPassword</code> and <code>getPassword</code> methods.
     */
    @Test
    public void whenSetPasswordThenItChanges() {
        this.response.setPassword(TestConstants.PASSWORD);
        assertThat(this.response.getPassword(), is(TestConstants.PASSWORD));
    }

    /**
     * Test correctness of serialization to JSON.
     */
    @Test
    public void whenSerializeToJsonThenResult() throws IOException {
        this.response.setSuccess(true);
        this.response.setDescription(TestConstants.DESCRIPTION);
        this.response.setPassword(TestConstants.PASSWORD);

        final String json = this.jsonTestHelper.encode(this.response);

        assertThat(this.jsonTestHelper.booleanField(json, "success"), is(true));
        assertThat(this.jsonTestHelper.textField(json, "description"), is(TestConstants.DESCRIPTION));
        assertThat(this.jsonTestHelper.textField(json, "password"), is(TestConstants.PASSWORD));
    }

    /**
     * Test correctness of serialization to JSON when password is missing.
     */
    @Test
    public void whenSerializeToJsonWithNotPasswordThenResult() throws IOException {
        this.response.setDescription(TestConstants.DESCRIPTION);

        final String json = this.jsonTestHelper.encode(this.response);

        assertThat(this.jsonTestHelper.booleanField(json, "success"), is(false));
        assertThat(this.jsonTestHelper.textField(json, "description"), is(TestConstants.DESCRIPTION));
        assertFalse(this.jsonTestHelper.hasField(json, "password"));
    }
}