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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sir.wellington.alchemy.collections.lists.Lists;
import tech.redroma.yelp.exceptions.YelpExcetion;
import tech.sirwellington.alchemy.annotations.access.Internal;

/**
 * A No-Op implementation of {@link YelpAPI} that returns no results.
 * 
 * @author SirWellington
 */
@Internal
final class NoOpYelp implements YelpAPI
{
    private final static Logger LOG = LoggerFactory.getLogger(NoOpYelp.class);

    @Override
    public YelpBusinessDetails getBusinessDetails(String businessId) throws YelpExcetion
    {
        return null;
    }

    @Override
    public List<YelpBusiness> searchForBusinesses(YelpSearchRequest request) throws YelpExcetion
    {
        return Lists.emptyList();
    }

}
