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
import java.util.Objects;
import tech.sirwellington.alchemy.annotations.access.Internal;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.annotations.objects.Pojo;

/**
 * Internal class for extracting Yelp's Responses.
 * @author SirWellington
 */
@Internal
@NonInstantiable
final class YelpResponses 
{

    YelpResponses() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instantiate");
    }
    
    
    /**
     * Designed for the Search API.
     * 
     * @see <a href="https://www.yelp.com/developers/documentation/v3/business_search">https://www.yelp.com/developers/documentation/v3/business_search</a>
     */
    @Pojo
    @Internal
    static class SearchResponse
    {
        
        /** Represents the total number of businesses that matched the search query */
        public int total;
        /** The Businesses returned from Yelp */
        public List<YelpBusiness> businesses;

        SearchResponse()
        {
        }

        @Override
        public int hashCode()
        {
            int hash = 5;
            hash = 67 * hash + this.total;
            hash = 67 * hash + Objects.hashCode(this.businesses);
            return hash;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }
            if (obj == null)
            {
                return false;
            }
            if (getClass() != obj.getClass())
            {
                return false;
            }
            final SearchResponse other = (SearchResponse) obj;
            if (this.total != other.total)
            {
                return false;
            }
            if (!Objects.equals(this.businesses, other.businesses))
            {
                return false;
            }
            return true;
        }

        @Override
        public String toString()
        {
            return "SearchResponse{" + "total=" + total + ", businesses=" + businesses + '}';
        }

    }

    @Internal
    @Pojo
    static class ReviewsResponse
    {

        /**
         * The total number of reviews for the business
         */
        public int total;
        /**
         * The reviews returned from the Yelp API
         */
        public List<YelpReview> reviews;

        ReviewsResponse()
        {
        }

        @Override
        public int hashCode()
        {
            int hash = 7;
            hash = 29 * hash + this.total;
            hash = 29 * hash + Objects.hashCode(this.reviews);
            return hash;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }
            if (obj == null)
            {
                return false;
            }
            if (getClass() != obj.getClass())
            {
                return false;
            }
            final ReviewsResponse other = (ReviewsResponse) obj;
            if (this.total != other.total)
            {
                return false;
            }
            if (!Objects.equals(this.reviews, other.reviews))
            {
                return false;
            }
            return true;
        }

        @Override
        public String toString()
        {
            return "ReviewsResponse{" + "total=" + total + ", reviews=" + reviews + '}';
        }

    }

}
