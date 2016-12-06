/*
 * Copyright 2016 BlackWholeLabs.
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


import com.google.gson.annotations.SerializedName;
import java.time.Instant;
import tech.sirwellington.alchemy.annotations.concurrency.Mutable;
import tech.sirwellington.alchemy.annotations.concurrency.ThreadUnsafe;
import tech.sirwellington.alchemy.annotations.objects.Pojo;


/**
 * Represents a Yelp Review JSON Object, as documented in the Yelp API.
 * 
 * @see <a href="https://www.yelp.com/developers/documentation/v3/business_reviews">https://www.yelp.com/developers/documentation/v3/business_reviews</a>
 * @author SirWellington
 */
@Pojo
@Mutable
@ThreadUnsafe
public class YelpReview 
{

    /**
     * The raring of the business associated with this review
     * @see YelpBusiness#rating
     */
    public int rating;
    
    /**
     * The user who wrote the review
     * @see User
     */
    public User user;
   
    /**
     * A Text excerpt of this review.
     */
    public String text;
   
    /**
     * The time that the review was created, in PST.
     */
    public Instant timeCreated;
    
    /**
     * A URL of this review.
     */
    public String url;
    
    @Pojo
    @Mutable
    @ThreadUnsafe
    public static class User 
    {
        /**
         * The name of the user.
         */
        public String name;
        
        /**
         * A URL of the user's profile picture.
         */
        @SerializedName("image_url")
        public String imageURL;
    }
}
