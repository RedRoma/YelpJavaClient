
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
import java.util.List;
import java.util.Objects;
import tech.sirwellington.alchemy.annotations.arguments.Optional;
import tech.sirwellington.alchemy.annotations.concurrency.Mutable;
import tech.sirwellington.alchemy.annotations.concurrency.ThreadUnsafe;
import tech.sirwellington.alchemy.annotations.objects.Pojo;

/**
 * Represents a Yelp Business JSON Object, as documented in the Yelp API.
 *
 * @see
 * <a href="https://www.yelp.com/developers/documentation/v3/business_search">https://www.yelp.com/developers/documentation/v3/business_search</a>
 * @author SirWellington
 */
@Pojo
@Mutable
@ThreadUnsafe
public class YelpBusiness
{

    public String id;

    public String name;

    public String url;

    public int rating;

    public String phone;

    public Boolean isClosed;

    public List<Category> categories;

    public int reviewCount;

    public Coordinate coordinates;

    public Address location;

    @SerializedName("image_url")
    public String imageURL;

    @Optional
    public Double distance;

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.url);
        hash = 37 * hash + this.rating;
        hash = 37 * hash + Objects.hashCode(this.phone);
        hash = 37 * hash + Objects.hashCode(this.isClosed);
        hash = 37 * hash + Objects.hashCode(this.categories);
        hash = 37 * hash + this.reviewCount;
        hash = 37 * hash + Objects.hashCode(this.coordinates);
        hash = 37 * hash + Objects.hashCode(this.location);
        hash = 37 * hash + Objects.hashCode(this.imageURL);
        hash = 37 * hash + Objects.hashCode(this.distance);
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
        final YelpBusiness other = (YelpBusiness) obj;
        if (this.rating != other.rating)
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
        if (!Objects.equals(this.url, other.url))
        {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone))
        {
            return false;
        }
        if (!Objects.equals(this.imageURL, other.imageURL))
        {
            return false;
        }
        if (!Objects.equals(this.isClosed, other.isClosed))
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
        if (!Objects.equals(this.distance, other.distance))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "YelpBusiness{" + "id=" + id + ", name=" + name + ", url=" + url + ", rating=" + rating + ", phone=" + phone + ", isClosed=" + isClosed + ", categories=" + categories + ", reviewCount=" + reviewCount + ", coordinates=" + coordinates + ", location=" + location + ", imageURL=" + imageURL + ", distance=" + distance + '}';
    }


}
