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
import java.util.Objects;
import tech.sirwellington.alchemy.annotations.arguments.NonEmpty;
import tech.sirwellington.alchemy.annotations.concurrency.Mutable;
import tech.sirwellington.alchemy.annotations.concurrency.ThreadUnsafe;
import tech.sirwellington.alchemy.annotations.objects.Pojo;

import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.NetworkAssertions.validURL;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.nonEmptyString;


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
     *
     * @see YelpBusiness#rating
     */
    public Double rating;

    /**
     * The user who wrote the review
     *
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
    public String timeCreated;

    /**
     * A URL of this review.
     */
    public String url;

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.rating);
        hash = 13 * hash + Objects.hashCode(this.user);
        hash = 13 * hash + Objects.hashCode(this.text);
        hash = 13 * hash + Objects.hashCode(this.timeCreated);
        hash = 13 * hash + Objects.hashCode(this.url);
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
        final YelpReview other = (YelpReview) obj;
        if (!Objects.equals(this.text, other.text))
        {
            return false;
        }
        if (!Objects.equals(this.timeCreated, other.timeCreated))
        {
            return false;
        }
        if (!Objects.equals(this.url, other.url))
        {
            return false;
        }
        if (!Objects.equals(this.rating, other.rating))
        {
            return false;
        }
        if (!Objects.equals(this.user, other.user))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "YelpReview{" + "rating=" + rating + ", user=" + user + ", text=" + text + ", timeCreated=" + timeCreated + ", url=" + url + '}';
    }

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

        /**
         * Creates a {@link User} from the specified arguments.
         *
         * @param name
         * @param imageURL
         * @return
         */
        public static User with(@NonEmpty String name, @NonEmpty String imageURL)
        {
            checkThat(name).is(nonEmptyString());
            checkThat(imageURL).is(validURL());

            User user = new User();
            user.name = name;
            user.imageURL = imageURL;

            return user;
        }

        public User()
        {
        }

        @Override
        public int hashCode()
        {
            int hash = 7;
            hash = 41 * hash + Objects.hashCode(this.name);
            hash = 41 * hash + Objects.hashCode(this.imageURL);
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
            final User other = (User) obj;
            if (!Objects.equals(this.name, other.name))
            {
                return false;
            }
            if (!Objects.equals(this.imageURL, other.imageURL))
            {
                return false;
            }
            return true;
        }

        @Override
        public String toString()
        {
            return "User{" + "name=" + name + ", imageURL=" + imageURL + '}';
        }

    }
}
