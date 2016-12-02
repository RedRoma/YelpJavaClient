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

import java.net.MalformedURLException;
import java.net.URL;
import tech.sirwellington.alchemy.annotations.arguments.NonEmpty;
import tech.sirwellington.alchemy.annotations.arguments.Required;
import tech.sirwellington.alchemy.http.AlchemyHttp;

import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.nonEmptyString;

/**
 * The OAuthTokenProvider is responsible for providing an OAuth Token used to make API calls.
 * The recommended method is to use the {@linkplain #newRefreshingTokenProvider(java.lang.String, java.lang.String) Refereshing OAUth Provider}.
 * <p>
 * For more information on Yelp's OAuth mechanism.
 * <a href="https://www.yelp.com/developers/documentation/v3/get_started">Click here for more information</a>.
 *
 * @author SirWellington
 * @see
 * <a href="https://www.yelp.com/developers/documentation/v3/get_started">https://www.yelp.com/developers/documentation/v3/get_started</a>
 */
public interface OAuthTokenProvider
{

    /**
     * Creates an {@link OAuthTokenProvider} using the provided key. Use this if you already have an OAuth token that you want to
     * reuse.
     *
     * @param token The OAuth token to use when making API calls.
     * @return
     * @throws IllegalArgumentException
     */
    public static OAuthTokenProvider newBasicTokenProvider(@NonEmpty String token) throws IllegalArgumentException
    {
        checkThat(token)
            .usingMessage("token is required")
            .is(nonEmptyString());

        return new BasicProvider(token);
    }

    /**
     * Creates a refreshing {@link OAuthTokenProvider} that obtains OAuth Tokens from Yelp and periodically renews the token
     * before it expires. This is the recommended method of authentication.
     * 
     * @param clientId     The Client ID obtained from the Yelp Developer Console.
     * @param clientSecret The Client Secret obtained from the Yelp Developer Console.
     * @return
     * @throws IllegalArgumentException
     */
    public static OAuthTokenProvider newRefreshingTokenProvider(@NonEmpty String clientId, @NonEmpty String clientSecret) throws IllegalArgumentException
    {
        final String DEFAULT_AUTH_URL = "https://api.yelp.com/oauth2/token";
        
        try
        {
            URL authURL = new URL(DEFAULT_AUTH_URL);
            return newRefeshingTokenProvider(clientId, clientSecret, authURL);
        }
        catch (MalformedURLException ex)
        {
            throw new IllegalArgumentException("Could not form Auth URL.", ex);
        }
    }

    /**
     * @param clientId     The Client ID obtained from the Yelp Developer Console.
     * @param clientSecret The Client Secret obtained from the Yelp Developer Console.
     * @param authURL
     * @return
     * @throws IllegalArgumentException
     */
    public static OAuthTokenProvider newRefeshingTokenProvider(@NonEmpty String clientId, @NonEmpty String clientSecret,
                                                               @Required URL authURL) throws IllegalArgumentException
    {
        AlchemyHttp http = AlchemyHttp.newDefaultInstance();

        return newRefreshingTokenProvider(clientId, clientSecret, authURL, http);
    }

    /**
     *
     * @param clientId     The Client ID obtained from the Yelp Developer Console.
     * @param clientSecret The Client Secret obtained from the Yelp Developer Console.
     * @param authURL      The Authentication URL to use for authentication.
     * @param http         The Alchemy HTTP Client to use during requests.
     * @return
     * @throws IllegalArgumentException
     * @see #newRefreshingTokenProvider(java.lang.String, java.lang.String, java.net.URL,
     * tech.sirwellington.alchemy.http.AlchemyHttp)
     */
    static OAuthTokenProvider newRefreshingTokenProvider(@NonEmpty String clientId,
                                                         @NonEmpty String clientSecret,
                                                         @Required URL authURL,
                                                         @Required AlchemyHttp http) throws IllegalArgumentException
    {
        checkThat(clientId, clientSecret)
            .usingMessage("cliend ID and client secret are required")
            .are(nonEmptyString());

        checkThat(authURL)
            .usingMessage("Authorization URL is required")
            .is(notNull());

        checkThat(http)
            .usingMessage("Alchemy HTTP cannot be null")
            .is(notNull());

        return new RenewingProvider(http, authURL, clientId, clientSecret);
    }

    /**
     * Obtain an OAuth token used for API calls.
     *
     * @return
     */
    @Required
    String getToken();

}
