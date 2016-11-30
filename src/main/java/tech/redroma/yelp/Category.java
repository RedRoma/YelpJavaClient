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
public class Category 
{
    public String alias;
    public String title;

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.alias);
        hash = 61 * hash + Objects.hashCode(this.title);
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
        final Category other = (Category) obj;
        if (!Objects.equals(this.alias, other.alias))
        {
            return false;
        }
        if (!Objects.equals(this.title, other.title))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Category{" + "alias=" + alias + ", title=" + title + '}';
    }

}