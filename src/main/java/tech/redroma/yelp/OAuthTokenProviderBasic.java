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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sirwellington.alchemy.annotations.access.Internal;
import tech.sirwellington.alchemy.arguments.assertions.StringAssertions;

import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;

/**
 * Stores a permanent token and returns it on each call.
 * 
 * @author SirWellington
 */
@Internal
final class OAuthTokenProviderBasic implements OAuthTokenProvider 
{
    private final static Logger LOG = LoggerFactory.getLogger(OAuthTokenProviderBasic.class);
    
    private final String token;

    OAuthTokenProviderBasic(String token)
    {
        checkThat(token).is(StringAssertions.nonEmptyString());
        this.token = token;
    }

    @Override
    public String getToken()
    {
        return token;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.token);
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
        final OAuthTokenProviderBasic other = (OAuthTokenProviderBasic) obj;
        if (!Objects.equals(this.token, other.token))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "OAuthTokenProviderBasic{" + "token=" + token + '}';
    }

 
}
