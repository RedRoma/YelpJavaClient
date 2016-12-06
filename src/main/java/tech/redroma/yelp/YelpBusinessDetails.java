
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
import tech.sirwellington.alchemy.annotations.concurrency.Mutable;
import tech.sirwellington.alchemy.annotations.concurrency.ThreadUnsafe;
import tech.sirwellington.alchemy.annotations.objects.Pojo;

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
    /** The Yelp ID of this business */
    public String id;

    /** The Name of this business */
    public String name;

    /** An URL photo for this business */
    @SerializedName("image_url")
    public String imageURL;

    /** Whether this business has been claimed by a Business Owner */
    @SerializedName("is_claimed")
    public Boolean isClaimed;

    /** Whether the businesss has been permanently closed */
    @SerializedName("is_closed")
    public Boolean isClosed;

    /** URL for the business on Yelp */
    public String url;

    /** The price level of the business. */
    public String price;

    public double rating;

    public int reviewCount;

    public String phone;

    @SerializedName("photos")
    public List<String> photosURLS;

    public List<Hours> hours;

    public List<Category> categories;

    public Coordinate coordinates;

    public Address location;

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.imageURL);
        hash = 37 * hash + Objects.hashCode(this.isClaimed);
        hash = 37 * hash + Objects.hashCode(this.isClosed);
        hash = 37 * hash + Objects.hashCode(this.url);
        hash = 37 * hash + Objects.hashCode(this.price);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.rating) ^ (Double.doubleToLongBits(this.rating) >>> 32));
        hash = 37 * hash + this.reviewCount;
        hash = 37 * hash + Objects.hashCode(this.phone);
        hash = 37 * hash + Objects.hashCode(this.photosURLS);
        hash = 37 * hash + Objects.hashCode(this.hours);
        hash = 37 * hash + Objects.hashCode(this.categories);
        hash = 37 * hash + Objects.hashCode(this.coordinates);
        hash = 37 * hash + Objects.hashCode(this.location);
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
        if (Double.doubleToLongBits(this.rating) != Double.doubleToLongBits(other.rating))
        {
            return false;
        }
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

        @SerializedName("hours_type")
        public String hoursType;

        @SerializedName("is_open_now")
        public Boolean isOpenNow;

        public List<OpenTimes> open;

        public static class OpenTimes
        {

            public int day;
            public String start;
            public String end;
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
