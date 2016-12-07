
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

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;
import sir.wellington.alchemy.collections.lists.Lists;
import tech.sirwellington.alchemy.annotations.concurrency.Mutable;
import tech.sirwellington.alchemy.annotations.concurrency.ThreadUnsafe;
import tech.sirwellington.alchemy.annotations.objects.Pojo;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Detailed information about a Yelp Business. This can be obtained by calling 
 * {@link YelpAPI#getBusinessDetails(java.lang.String) }
 *
 * <p>
 * See <a href="https://www.yelp.com/developers/documentation/v3/business">Yelp's Documentaion</a> for more information.
 *
 * @author SirWellington
 * @see
 * <a href="https://www.yelp.com/developers/documentation/v3/business">https://www.yelp.com/developers/documentation/v3/business</a>
 */
@Pojo
@Mutable
@ThreadUnsafe
public class YelpBusinessDetails
{
    /** The Yelp ID of this business. */
    public String id;

    /** The Name of this business. */
    public String name;

    /** An URL photo for this business. */
    @SerializedName("image_url")
    public String imageURL;

    /** Whether this business has been claimed by a Business Owner. */
    @SerializedName("is_claimed")
    public Boolean isClaimed;

    /** Whether the businesses has been permanently closed. */
    @SerializedName("is_closed")
    public Boolean isClosed;

    /** URL for the business on Yelp. */
    public String url;

    /** The price level of the business. */
    public String price;

    /** Rating for this business. Ranges from (1...5). */
    public Double rating;

    /** The number of reviews for this business. */
    public int reviewCount;

    /** The phone number of this business, if available. */
    public String phone;

    /** The URLs of up to 3 photos. */
    @SerializedName("photos")
    public List<String> photosURLS;

    /** Opening hours of the business. */
    public List<Hours> hours;

    /** A list of categories associated with this business. */
    public List<Category> categories;

    /** The Geo-Coordinate (latitude-longitude) of this business. */
    public Coordinate coordinates;

    /** The physical address of this business. */
    public Address location;

    /**
     * @return The {@link #price} as a {@link Price}.
     */
    public Price getPriceLevel()
    {
        if (isNullOrEmpty(price))
        {
            return null;
        }
        
        return Price.fromString(price);
    }
    
    /**
     * @return Whether this business is open now or not.
     */
    public boolean isOpenNow()
    {
        if (Lists.isEmpty(hours))
        {
            return false;
        }
       
        return hours.stream()
            .anyMatch(h -> h.isOpenNow);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.imageURL);
        hash = 89 * hash + Objects.hashCode(this.isClaimed);
        hash = 89 * hash + Objects.hashCode(this.isClosed);
        hash = 89 * hash + Objects.hashCode(this.url);
        hash = 89 * hash + Objects.hashCode(this.price);
        hash = 89 * hash + Objects.hashCode(this.rating);
        hash = 89 * hash + this.reviewCount;
        hash = 89 * hash + Objects.hashCode(this.phone);
        hash = 89 * hash + Objects.hashCode(this.photosURLS);
        hash = 89 * hash + Objects.hashCode(this.hours);
        hash = 89 * hash + Objects.hashCode(this.categories);
        hash = 89 * hash + Objects.hashCode(this.coordinates);
        hash = 89 * hash + Objects.hashCode(this.location);
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
        final YelpBusinessDetails other = (YelpBusinessDetails) obj;
        if (this.reviewCount != other.reviewCount)
        {
            return false;
        }
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        if (!Objects.equals(this.imageURL, other.imageURL))
        {
            return false;
        }
        if (!Objects.equals(this.url, other.url))
        {
            return false;
        }
        if (!Objects.equals(this.price, other.price))
        {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone))
        {
            return false;
        }
        if (!Objects.equals(this.isClaimed, other.isClaimed))
        {
            return false;
        }
        if (!Objects.equals(this.isClosed, other.isClosed))
        {
            return false;
        }
        if (!Objects.equals(this.rating, other.rating))
        {
            return false;
        }
        if (!Objects.equals(this.photosURLS, other.photosURLS))
        {
            return false;
        }
        if (!Objects.equals(this.hours, other.hours))
        {
            return false;
        }
        if (!Objects.equals(this.categories, other.categories))
        {
            return false;
        }
        if (!Objects.equals(this.coordinates, other.coordinates))
        {
            return false;
        }
        if (!Objects.equals(this.location, other.location))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "YelpBusinessDetails{" + "id=" + id + ", name=" + name + ", imageURL=" + imageURL + ", isClaimed=" + isClaimed + ", isClosed=" + isClosed + ", url=" + url + ", price=" + price + ", rating=" + rating + ", reviewCount=" + reviewCount + ", phone=" + phone + ", photosURLS=" + photosURLS + ", hours=" + hours + ", categories=" + categories + ", coordinates=" + coordinates + ", location=" + location + '}';
    }

    @Pojo
    @ThreadUnsafe
    @Mutable
    public static class Hours
    {

        /**
         * The type of opening hours information. Right now, it is always {@code 'REGULAR'}.
         */
        @SerializedName("hours_type")
        public String hoursType;

        /**
         * Whether the business is open at the time of the request.
         */
        @SerializedName("is_open_now")
        public Boolean isOpenNow;

        /**
         * A detailed list of opening hours for the business throughout the week.
         */
        public List<OpenTimes> open;

        public static class OpenTimes
        {

            /**
             * From 0 to 6, represents the day of the week from Monday to Sunday (respectively).
             * Notice that you may get the same
             * day of the week more than once if the business has more than one opening time slots.
             */
            public int day;
            /**
             * Start of the opening hours in a day, in 24-hour clock notation.
             * <pre>
             * 300 = 3am
             * 1000 = 10am
             * 1800 = 6pm
             * </pre>
             */
            public String start;
            /**
             * End of the opening hours in a day, in 24-hour clock notation.
             * <pre>
             * 300 = 3am
             * 1000 = 10am
             * 1800 = 6pm
             * </pre>
             */
            public String end;
            
            /**
             * Whether the business opens overnight or not. When this is true, the end time will be lower than the start time, as
             * it signifies the end time is in the following day.
             */
            @SerializedName("is_overnight")
            public Boolean isOvernight;

            @Override
            public int hashCode()
            {
                int hash = 7;
                hash = 71 * hash + this.day;
                hash = 71 * hash + Objects.hashCode(this.start);
                hash = 71 * hash + Objects.hashCode(this.end);
                hash = 71 * hash + Objects.hashCode(this.isOvernight);
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
                final OpenTimes other = (OpenTimes) obj;
                if (this.day != other.day)
                {
                    return false;
                }
                if (!Objects.equals(this.start, other.start))
                {
                    return false;
                }
                if (!Objects.equals(this.end, other.end))
                {
                    return false;
                }
                if (!Objects.equals(this.isOvernight, other.isOvernight))
                {
                    return false;
                }
                return true;
            }

            @Override
            public String toString()
            {
                return "OpenTimes{" + "day=" + day + ", start=" + start + ", end=" + end + ", isOvernight=" + isOvernight + '}';
            }

        }

        @Override
        public int hashCode()
        {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.hoursType);
            hash = 97 * hash + Objects.hashCode(this.isOpenNow);
            hash = 97 * hash + Objects.hashCode(this.open);
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
            final Hours other = (Hours) obj;
            if (!Objects.equals(this.hoursType, other.hoursType))
            {
                return false;
            }
            if (!Objects.equals(this.isOpenNow, other.isOpenNow))
            {
                return false;
            }
            if (!Objects.equals(this.open, other.open))
            {
                return false;
            }
            return true;
        }

        @Override
        public String toString()
        {
            return "Hours{" + "hoursType=" + hoursType + ", isOpenNow=" + isOpenNow + ", open=" + open + '}';
        }

    }

}
