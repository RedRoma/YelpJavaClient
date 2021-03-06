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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.GeneratePojo;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 *
 * @author SirWellington
 */
@Repeat(10)
@RunWith(AlchemyTestRunner.class)
public class YelpResponsesTest
{
    
    @GeneratePojo
    private YelpResponses.SearchResponse searchResponse;
    
    @GeneratePojo
    private YelpResponses.ReviewsResponse reviewsResponse;
    
    @Before
    public void setUp() throws Exception
    {
        
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
    public void testSearchResponse() throws Exception
    {
        assertThat(searchResponse, notNullValue());
        assertThat(searchResponse.total, greaterThan(0));
        assertThat(searchResponse.businesses, not(empty()));
    }
    
    @Test
    public void testSearchResponseHashCode() throws Exception
    {
        int hashCode = searchResponse.hashCode();
        assertThat(hashCode, not(0));
    }
    
    @Test
    public void testReviewsResponse() throws Exception
    {
        assertThat(reviewsResponse, notNullValue());
        assertThat(reviewsResponse.total, greaterThan(0));
        assertThat(reviewsResponse.reviews, not(empty()));
    }
    
    @Test
    public void testReviewsResponseHashCode()
    {
        int hashCode = reviewsResponse.hashCode();
        assertThat(hashCode, not(0));
    }
}
