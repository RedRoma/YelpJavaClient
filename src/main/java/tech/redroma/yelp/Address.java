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
public class Address 
{
    public String city;
    public String state;
    public String country;
    public String address1;
    public String address2;
    public String address3;
    @SerializedName("zip_code")
    public String zipCode;

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.city);
        hash = 83 * hash + Objects.hashCode(this.state);
        hash = 83 * hash + Objects.hashCode(this.country);
        hash = 83 * hash + Objects.hashCode(this.address1);
        hash = 83 * hash + Objects.hashCode(this.address2);
        hash = 83 * hash + Objects.hashCode(this.address3);
        hash = 83 * hash + Objects.hashCode(this.zipCode);
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
        final Address other = (Address) obj;
        if (!Objects.equals(this.city, other.city))
        {
            return false;
        }
        if (!Objects.equals(this.state, other.state))
        {
            return false;
        }
        if (!Objects.equals(this.country, other.country))
        {
            return false;
        }
        if (!Objects.equals(this.address1, other.address1))
        {
            return false;
        }
        if (!Objects.equals(this.address2, other.address2))
        {
            return false;
        }
        if (!Objects.equals(this.address3, other.address3))
        {
            return false;
        }
        if (!Objects.equals(this.zipCode, other.zipCode))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Address{" + "city=" + city + ", state=" + state + ", country=" + country + ", address1=" + address1 + ", address2=" + address2 + ", address3=" + address3 + ", zipCode=" + zipCode + '}';
    }

}
