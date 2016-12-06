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
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.GenerateString;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 *
 * @author SirWellington
 */
@Repeat(10)
@RunWith(AlchemyTestRunner.class)
public class NoOpYelpTest
{
    @GenerateString
    private String businessId;
    
    private NoOpYelp instance;

    @Before
    public void setUp() throws Exception
    {
        instance = new NoOpYelp();
        setupData();
        setupMocks();
    }


    private void setupData() throws Exception
    {
        
    }

    private void setupMocks() throws Exception
    {
        
    }

    @Test
    public void testGetBusinessDetails()
    {
        YelpBusinessDetails result = instance.getBusinessDetails(businessId);
        assertThat(result, nullValue());
    }

    @Test
    public void testSearchForBusinesses()
    {
        List<YelpBusiness> results = instance.searchForBusinesses(null);
        assertThat(results, notNullValue());
        assertThat(results, empty());
            
    }

    @Test
    public void testGetReviewsForBusiness()
    {
        List<YelpReview> results = instance.getReviewsForBusiness(businessId);
        assertThat(results, notNullValue());
        assertThat(results, is(empty()));
    }

}