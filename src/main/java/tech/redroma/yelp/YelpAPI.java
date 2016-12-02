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
import tech.redroma.yelp.exceptions.YelpBadArgumentException;
import tech.redroma.yelp.exceptions.YelpExcetion;
import tech.sirwellington.alchemy.annotations.arguments.NonEmpty;
import tech.sirwellington.alchemy.annotations.arguments.Required;

import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.nonEmptyString;

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
        private String apiKey = "";

        public static Builder newInstance()
        {
            return new Builder();
        }
        
        public YelpAPI build()
        {
            return null;
        }
        
    }
}
