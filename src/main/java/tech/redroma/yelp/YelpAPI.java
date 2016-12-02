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
import tech.redroma.yelp.exceptions.YelpBadArgumentException;
import tech.redroma.yelp.exceptions.YelpExcetion;
import tech.redroma.yelp.oauth.OAuthTokenProvider;
import tech.sirwellington.alchemy.annotations.arguments.NonEmpty;
import tech.sirwellington.alchemy.annotations.arguments.Required;
import tech.sirwellington.alchemy.http.AlchemyHttp;

import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;
import static tech.sirwellington.alchemy.arguments.assertions.NetworkAssertions.validURL;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.nonEmptyString;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.stringWithLengthGreaterThanOrEqualTo;

/**
 * The interface used to interact with <a href= "https://www.yelp.com/developers/documentation/v3">Yelp's Developer API</a>.
 * 
 * @author SirWellington
 * @see <a href= "https://www.yelp.com/developers/documentation/v3">Yelp API</a>
 */
public interface YelpAPI 
{
    /**
     * Returns the detail information of a business. Normally, you'll get the business id from the 
     * {@linkplain #searchForBusinesses(tech.redroma.yelp.YelpSearchRequest) search API}. To get 
     * review information for a business, refer to ...
     * 
     * @param business The business to get more details of.
     * @return
     * @throws YelpExcetion 
     * @see #getBusinessDetails(java.lang.String) 
     */
    default YelpBusinessDetails getBusinessDetails(@Required YelpBusiness business) throws YelpExcetion
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
     * Returns the detail information of a business. Normally, you'll get the business id from
     * the {@linkplain #searchForBusinesses(tech.redroma.yelp.YelpSearchRequest) search API}.
     * TO get review information for a business, refer to ...
     * 
     * @param businessId
     * @return
     * @throws YelpExcetion 
     * @see #getBusinessDetails(java.lang.String) 
     * @see #searchForBusinesses(tech.redroma.yelp.YelpSearchRequest) 
     */
    @Required
    YelpBusinessDetails getBusinessDetails(@NonEmpty String businessId) throws YelpExcetion;
    
    /**
     * Returns up to 1,000 businesses based on the provided search criteria. It has some basic information about the businesses
     * that match the search criteria. To get details information, see {@link #getBusinessDetails(java.lang.String) }.
     * <p>
     * To create a search request, see {@link YelpSearchRequest#newBuilder() }.
     * 
     * @param request
     * @return
     * @throws YelpExcetion 
     * @see #getBusinessDetails(java.lang.String) 
     * @see #getBusinessDetails(tech.redroma.yelp.YelpBusiness) 
     * @see YelpSearchRequest
     */
    List<YelpBusiness> searchForBusinesses(@Required YelpSearchRequest request) throws YelpExcetion;
    
    static YelpAPI newInstance()
    {
        return Builder.newInstance().build();
    }
    
    static class Builder
    {
        private static final String DEFAULT_BASE_URL = "https://api.yelp.com/v3";
       
        private OAuthTokenProvider oauthProvider;
        
        private String baseURL = DEFAULT_BASE_URL;
        
        private AlchemyHttp http = AlchemyHttp.newBuilder()
            .usingTimeout(60, TimeUnit.SECONDS)
            .build();
        
        public static Builder newInstance()
        {
            return new Builder();
        }
        
        public Builder withBaseURL(@NonEmpty String baseURL) throws IllegalArgumentException
        {
            checkThat(baseURL)
                .is(validURL());
            
            this.baseURL = baseURL;
            return this;
        }
        
        public Builder withHttpClient(@Required AlchemyHttp http) throws IllegalArgumentException
        {
            checkThat(http).is(notNull());
            
            this.http = http;
            return this;
        }
        
        public Builder withOAuthToken(@NonEmpty String oauthToken) throws IllegalArgumentException
        {
            checkThat(oauthToken)
                .is(nonEmptyString())
                .is(stringWithLengthGreaterThanOrEqualTo(2));
            
            this.oauthProvider = OAuthTokenProvider.newBasicTokenProvider(oauthToken);
            return this;
        }
        
        public Builder withClientCredentials(@NonEmpty String cliendId, @NonEmpty String clientSecret) throws IllegalArgumentException
        {
            checkThat(cliendId, clientSecret)
                .usingMessage("invalid client id and secret")
                .are(nonEmptyString())
                .are(stringWithLengthGreaterThanOrEqualTo(3));
            
            this.oauthProvider = OAuthTokenProvider.newRefreshingTokenProvider(cliendId, clientSecret);
            return this;
        }
        
        public YelpAPI build()
        {
            ensureReadyToBuild();
            
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
