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

 
package tech.redroma.yelp;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.redroma.yelp.exceptions.YelpBadArgumentException;
import tech.redroma.yelp.exceptions.YelpException;
import tech.redroma.yelp.oauth.OAuthTokenProvider;
import tech.sirwellington.alchemy.annotations.arguments.NonEmpty;
import tech.sirwellington.alchemy.annotations.arguments.Required;
import tech.sirwellington.alchemy.annotations.designs.patterns.BuilderPattern;
import tech.sirwellington.alchemy.http.AlchemyHttp;

import static tech.sirwellington.alchemy.annotations.designs.patterns.BuilderPattern.Role.BUILDER;
import static tech.sirwellington.alchemy.annotations.designs.patterns.BuilderPattern.Role.PRODUCT;
import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;
import static tech.sirwellington.alchemy.arguments.assertions.NetworkAssertions.validURL;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.nonEmptyString;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.stringWithLengthGreaterThanOrEqualTo;

/**
 * The interface used to interact with <a href= "https://www.yelp.com/developers/documentation/v3">Yelp's Developer API</a>.
 * <p>
 * To create an instance, see {@link #newInstance(java.lang.String, java.lang.String) } or {@link YelpAPI.Builder}.
 *
 * @author SirWellington
 * @see <a href= "https://www.yelp.com/developers/documentation/v3">Yelp API</a>
 */
@BuilderPattern(role = PRODUCT)
public interface YelpAPI 
{
    /** The Default Yelp API endpoint */
    
    public static final String DEFAULT_BASE_URL = "https://api.yelp.com/v3";

    /**
     * Returns detailed information of a business. This includes things like business hours, additional photos, and price
     * information.
     * <p>
     * Normally, you'll get the {@link YelpBusiness} object from the
     * {@linkplain #searchForBusinesses(tech.redroma.yelp.YelpSearchRequest) search API}.
     * <p>
     * To get review information for a business, refer to ...
     *
     * @param business The business to get more details of.
     * @return
     * @throws YelpException
     *
     * @see YelpBusinessDetails
     * @see #getBusinessDetails(java.lang.String)
     */
    default YelpBusinessDetails getBusinessDetails(@Required YelpBusiness business) throws YelpException
    {
        checkThat(business)
            .throwing(YelpBadArgumentException.class)
            .usingMessage("business cannot be null")
            .is(notNull());

        checkThat(business.id)
            .usingMessage("business is missing it's id")
            .is(nonEmptyString());

        return getBusinessDetails(business.id);
    }

    /**
     * Returns detailed information of a business. This includes things like business hours, additional photos, and price
     * information.
     * <p>
     * Normally, you'll get the business id from the
     * {@linkplain #searchForBusinesses(tech.redroma.yelp.YelpSearchRequest) search API}.
     * <p>
     * To get review information for a business, refer to ...
     *
     * @param businessId The {@linkplain YelpBusiness#id Business ID} to query.
     * @return
     * @throws YelpException 
     * @see #getBusinessDetails(java.lang.String) 
     * @see #searchForBusinesses(tech.redroma.yelp.YelpSearchRequest) 
     */
    @Required
    YelpBusinessDetails getBusinessDetails(@NonEmpty String businessId) throws YelpException;
    
    /**
     * Returns up to 1,000 businesses based on the provided search criteria. It has some basic information about the businesses
     * that match the search criteria. To get details information, see {@link #getBusinessDetails(java.lang.String) }.
     * <p>
     * To create a search request, see {@link YelpSearchRequest#newBuilder() }.
     * 
     * @param request
     * @return
     * @throws YelpException 
     * @see #getBusinessDetails(java.lang.String) 
     * @see #getBusinessDetails(tech.redroma.yelp.YelpBusiness) 
     * @see YelpSearchRequest
     */
    List<YelpBusiness> searchForBusinesses(@Required YelpSearchRequest request) throws YelpException;
    
    /**
     * Gets the reviews, if any, associated with a Business.
     * 
     * @param business The business, obtained from a call to {@link #searchForBusinesses(tech.redroma.yelp.YelpSearchRequest) }.
     * @return
     * @throws YelpException 
     */
    default List<YelpReview> getReviewsForBusiness(@Required YelpBusiness business) throws YelpException
    {
        checkThat(business)
            .throwing(YelpBadArgumentException.class)
            .is(notNull());
            
        checkThat(business.id)
            .throwing(YelpBadArgumentException.class)
            .is(nonEmptyString());
        
        return getReviewsForBusiness(business.id);
    }
    
    /**
     * Gets the reviews, if any, associated with a Business. 
     * 
     * @param businessId The ID of the business. Obtained from calls to {@link #searchForBusinesses(tech.redroma.yelp.YelpSearchRequest) }.
     * @return
     * @throws YelpException 
     */
    List<YelpReview> getReviewsForBusiness(@NonEmpty String businessId) throws YelpException;
    
    static YelpAPI newInstance(@NonEmpty String cliendId, @NonEmpty String clientSecret)
    {
        checkThat(cliendId, clientSecret)
            .are(nonEmptyString());
        
        return Builder.newInstance()
            .withClientCredentials(cliendId, clientSecret)
            .build();
    }
    
    /** A No-Op Instance that returns no results. Suitable for testing purposes. */
    static YelpAPI NO_OP = new NoOpYelp();
    
    /**
     * Used to create a more customized {@link YelpAPI}.
     * 
     * @see #newInstance() 
     * @author SirWellington
     */
    @BuilderPattern(role = BUILDER)
    static final class Builder
    {
        
        private static final Logger LOG = LoggerFactory.getLogger(Builder.class);
       
        //Used to connect to authenticate calls with Yelp
        private OAuthTokenProvider oauthProvider;
        
        private String baseURL = DEFAULT_BASE_URL;
        
        //The HTTP Client to use; starts with a default client
        private AlchemyHttp http = AlchemyHttp.newBuilder()
            .usingTimeout(60, TimeUnit.SECONDS)
            .build();
           
        //Determins whether an OAuth token is fetched immediately after the client is built
        private boolean requestTokenImmediately = false;

        /**
         * Creates a new instance of a Builder.
         * <p>
         * The only thing worth customizing is the
         * {@linkplain #withClientCredentials(java.lang.String, java.lang.String) OAuth Authentication}
         *
         * @return
         */
        public static Builder newInstance()
        {
            return new Builder();
        }
        
        /**
         * Sets the base URL to make calls to. Note that this should only be used for testing purposes.
         * <p>
         * If this method isn't called, it defaults to {@link #DEFAULT_BASE_URL}.
         * 
         * @param baseURL The base URL to use when making API calls. Must be a valid URL and cannot be empty.
         * @return
         * @throws IllegalArgumentException 
         */
        public Builder withBaseURL(@NonEmpty String baseURL) throws IllegalArgumentException
        {
            checkThat(baseURL).is(validURL());
            
            this.baseURL = baseURL;
            return this;
        }
        
        /**
         * Sets the {@linkplain AlchemyHttp HTTP Client} to use when making requests.
         * 
         * @param http The Alchemy HTTP client to use.
         * @return
         * @throws IllegalArgumentException If the client is null
         * @see AlchemyHttp#newBuilder() 
         * @see AlchemyHttp#newInstance(org.apache.http.client.HttpClient, java.util.concurrent.ExecutorService, java.util.Map) 
         */
        public Builder withHttpClient(@Required AlchemyHttp http) throws IllegalArgumentException
        {
            checkThat(http).is(notNull());
            
            this.http = http;
            return this;
        }
        
        /**
         * If you prefer to obtain an OAuth Token yourself, you can provide it using this method.
         * <p>
         * Note that unlike {@link #withClientCredentials(java.lang.String, java.lang.String) }, this token
         * will not be reloaded when it expires.
         * 
         * @param oauthToken
         * @return
         * @throws IllegalArgumentException 
         */
        public Builder withOAuthToken(@NonEmpty String oauthToken) throws IllegalArgumentException
        {
            checkThat(oauthToken)
                .is(nonEmptyString())
                .is(stringWithLengthGreaterThanOrEqualTo(2));
            
            this.oauthProvider = OAuthTokenProvider.newBasicTokenProvider(oauthToken);
            return this;
        }
        
        /**
         * Uses the provided Client ID and Client Secret to obtain an OAuth token from Yelp.
         * Note that these are also known as {@code 'App ID'} and {@code 'App Secret'}.
         * <p>
         * See <a href="https://www.yelp.com/developers/documentation/v3/get_started">Yelp Documentation</a> for more
         * information.
         * 
         * @param cliendId Client ID, also known as 'App ID'.
         * @param clientSecret Client Secret, also known as 'App Secret'.
         * @return
         * @throws IllegalArgumentException 
         * @see <a href="https://www.yelp.com/developers/documentation/v3/get_started">https://www.yelp.com/developers/documentation/v3/get_started</a>
         */
        public Builder withClientCredentials(@NonEmpty String cliendId, @NonEmpty String clientSecret) throws IllegalArgumentException
        {
            checkThat(cliendId, clientSecret)
                .usingMessage("invalid client id and secret")
                .are(nonEmptyString())
                .are(stringWithLengthGreaterThanOrEqualTo(3));
            
            this.oauthProvider = OAuthTokenProvider.newRefreshingTokenProvider(cliendId, clientSecret);
            return this;
        }
        
        /**
         * When used with {@link #withClientCredentials(java.lang.String, java.lang.String) }, ensures that
         * an OAuth token is obtained eagerly, before the {@link YelpAPI} is created.
         * @return 
         */
        public Builder withEagerAuthentication()
        {
            requestTokenImmediately = true;
            return this;
        }

        /**
         * Builds a usable {@link YelpAPI}.
         * <p>
         * Note that the client credentials must be set using either 
         * {@link #withClientCredentials(java.lang.String, java.lang.String) } or {@link #withOAuthToken(java.lang.String) }.
         *
         * @return
         * @throws IllegalStateException If the Yelp API is not ready to build.
         */
        public YelpAPI build() throws IllegalStateException
        {
            ensureReadyToBuild();
            
            if (requestTokenImmediately)
            {
                LOG.debug("Obtaining OAuth Token in advance.");
                oauthProvider.getToken();
            }
            
            return new YelpAPIImpl(http, oauthProvider, baseURL);
        }

        private void ensureReadyToBuild()
        {
            checkThat(baseURL)
                .usingMessage("invalid base URL: " + baseURL)
                .is(nonEmptyString())
                .is(validURL());
            
            checkThat(oauthProvider)
                .usingMessage("OAuth Provider missing")
                .is(notNull());
            
            checkThat(http)
                .usingMessage("missing Alchemy HTTP client")
                .is(notNull());
        }
        
    }
}
