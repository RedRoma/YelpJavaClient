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
 *
 * @author SirWellington
 */
@Pojo
@Mutable
@ThreadUnsafe
public class YelpBusinessDetails
{

    public String id;

    public String name;

    @SerializedName("image_url")
    public String imageURL;

    @SerializedName("is_claimed")
    public Boolean isClaimed;

    @SerializedName("is_closed")
    public Boolean isClosed;

    public String url;

    public String price;

    public int rating;

    public int reviewCount;

    public String phone;

    @SerializedName("photos")
    public List<String> photosURLS;

    public Hours hours;

    public List<Category> categories;

    public Coordinate coordinates;

    public Address location;

    @Pojo
    @ThreadUnsafe
    @Mutable
    public static class Hours
    {

        public Type hoursType;

        @SerializedName("is_open_now")
        public Boolean isOpenNow;

        public List<OpenTimes> open;

        public enum Type
        {
            REGULAR
        }

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
            if (this.hoursType != other.hoursType)
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
