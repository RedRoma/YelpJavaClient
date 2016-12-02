/*
 * Copyright 2016 RedRoma, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.redroma.yelp.oauth;

import com.google.gson.JsonObject;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import tech.sirwellington.alchemy.http.AlchemyHttp;
import tech.sirwellington.alchemy.http.mock.AlchemyHttpMock;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.GenerateInteger;
import tech.sirwellington.alchemy.test.junit.runners.GenerateString;
import tech.sirwellington.alchemy.test.junit.runners.GenerateURL;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateInteger.Type.RANGE;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateString.Type.ALPHANUMERIC;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateString.Type.HEXADECIMAL;

/**
 *
 * @author SirWellington
 */
@Repeat(50)
@RunWith(AlchemyTestRunner.class)
public class RenewingProviderTest
{

    @Mock
    private AlchemyHttp http;

    @GenerateURL
    private URL authURL;

    private RenewingProvider instance;
    private RenewingProvider other;

    @GenerateString(HEXADECIMAL)
    private String cliendId;

    @GenerateString(ALPHANUMERIC)
    private String clientSecret;

    @GenerateString(HEXADECIMAL)
    private String otherClientId;

    @GenerateString(ALPHANUMERIC)
    private String otherClientSecret;
    
    private JsonObject authResponse;
    
    @GenerateInteger(value = RANGE, min = 1_000, max = 100_000)
    private Integer expiration;
    
    @GenerateString
    private String token;

    @Before
    public void setUp() throws Exception
    {

        setupData();
        setupMocks();
        
        instance = new RenewingProvider(http, authURL, cliendId, clientSecret);
        other = new RenewingProvider(http, authURL, otherClientId, otherClientSecret);
    }

    private void setupData() throws Exception
    {
        authResponse = new JsonObject();
        authResponse.addProperty(RenewingProvider.Keys.CLIENT_ID, cliendId);
        authResponse.addProperty(RenewingProvider.Keys.CLIENT_SECRET, clientSecret);
        authResponse.addProperty(RenewingProvider.Keys.GRANT_TYPE, "client_credentials");
    }

    private void setupMocks() throws Exception
    {
        http = AlchemyHttpMock.begin()
            .whenPost()
            .noBody()
            .at(authURL)
            .thenReturnJson(authResponse)
            .build();
    }
    
    @Test
    public void testGetToken()
    {
    }
    
    @Test
    public void testHashCode()
    {
        RenewingProvider copy = new RenewingProvider(http, authURL, cliendId, clientSecret);
        assertThat(copy.hashCode(), is(instance.hashCode()));
    }
    
    @Test
    public void testHashCodeWhenDifferent()
    {
        assertThat(instance.hashCode(), not(other.hashCode()));
    }
    
    @Test
    public void testEquals()
    {
        RenewingProvider copy = new RenewingProvider(http, authURL, cliendId, clientSecret);
        assertThat(copy, is(instance));
    }
    
    @Test
    public void testEqualsWhenIsDifferent()
    {
        assertThat(instance, not(other));
    }
    
    @Test
    public void testToString()
    {
        String string = instance.toString();
        assertThat(string, not(containsString(clientSecret)));
    }

}
