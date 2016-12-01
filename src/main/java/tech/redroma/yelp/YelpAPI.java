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
    
    @Required
    YelpBusinessDetails getBusinessDetails(@NonEmpty String businessId) throws YelpExcetion;
    
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
