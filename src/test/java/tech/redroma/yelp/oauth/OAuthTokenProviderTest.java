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

import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import tech.sirwellington.alchemy.http.AlchemyHttp;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.GenerateString;
import tech.sirwellington.alchemy.test.junit.runners.GenerateURL;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateString.Type.ALPHANUMERIC;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateString.Type.HEXADECIMAL;

/**
 *
 * @author SirWellington
 */
@Repeat(10)
@RunWith(AlchemyTestRunner.class)
public class OAuthTokenProviderTest
{

    @GenerateString(HEXADECIMAL)
    private String token;

    @GenerateString(HEXADECIMAL)
    private String cliendId;

    @GenerateString(ALPHANUMERIC)
    private String cliendSecret;

    @Mock
    private AlchemyHttp http;

    @GenerateURL
    private URL authURL;

    @Before
    public void setUp() throws Exception
    {

        setupData();
        setupMocks();
    }

    private void setupData() throws Exception
    {

    }

    private void setupMocks() throws Exception
    {

    }

    @Test
    public void testNewBasicTokenProvider()
    {
        OAuthTokenProvider result = OAuthTokenProvider.newBasicTokenProvider(token);
        assertThat(result, notNullValue());
        assertThat(result.getToken(), is(token));
    }

    @Test
    public void testNewRefreshingTokenProvider_String_String()
    {
        OAuthTokenProvider result = OAuthTokenProvider.newRefeshingTokenProvider(cliendId, cliendSecret, authURL);
        assertThat(result, notNullValue());
    }

    @Test
    public void testNewRefeshingTokenProvider()
    {
        OAuthTokenProvider result = OAuthTokenProvider.newRefreshingTokenProvider(cliendId, cliendSecret);
        assertThat(result, notNullValue());
    }

    @Test
    public void testNewRefreshingTokenProvider_4args()
    {
        OAuthTokenProvider result = OAuthTokenProvider.newRefreshingTokenProvider(cliendId, cliendSecret, authURL, http);
        assertThat(result, notNullValue());
    }

}
