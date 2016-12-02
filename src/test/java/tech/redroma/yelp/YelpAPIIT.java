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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sirwellington.alchemy.annotations.testing.IntegrationTest;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;

import static com.google.common.base.Strings.isNullOrEmpty;
import static tech.redroma.yelp.YelpSearchRequest.Builder.MAX_LIMIT;

/**
 * Manually run this test to make sure your client key and secret work.
 * 
 * @author SirWellington
 */
@IntegrationTest
@RunWith(AlchemyTestRunner.class)
public class YelpAPIIT 
{

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    //Set to your own credentials to enable the test
    //BE SURE NOT TO CHECK THIS INTO A SOURCE REPOSITORY.
    private final String clientId = "";
    private final String clientSecret = "";

    private YelpSearchRequest request;

    private YelpAPI yelp;

    @Before
    public void setUp() throws Exception
    {
        if (!isNullOrEmpty(clientId) && !isNullOrEmpty(clientSecret))
        {
            yelp = YelpAPI.newInstance(clientId, clientSecret);
        }
    }

    @Test
    public void testSearch()
    {
        if (yelp == null)
        {
            return;
        }

//        34.018363, -118.492343
        request = YelpSearchRequest.newBuilder()
            .withSearchTerm("Deli")
            .withCoordinate(Coordinate.of(34.018363, -118.492343))
            .withLimit(MAX_LIMIT)
            .build();

        List<YelpBusiness> results = yelp.searchForBusinesses(request);
        LOG.info("Found {} results for request: {}", results.size(), request);
    }
    
}