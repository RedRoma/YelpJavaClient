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
import tech.sirwellington.alchemy.test.junit.runners.GenerateBoolean;
import tech.sirwellington.alchemy.test.junit.runners.GenerateDouble;
import tech.sirwellington.alchemy.test.junit.runners.GenerateInteger;
import tech.sirwellington.alchemy.test.junit.runners.GenerateString;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author SirWellington
 */
@Repeat(10)
@RunWith(AlchemyTestRunner.class)
public class YelpSearchRequestTest
{

    @GenerateString
    private String searchTerm;

    @GenerateString
    private String location;

    @GenerateDouble
    private Double latitude;

    @GenerateDouble
    private Double longitude;

    @GenerateInteger
    private Integer radius;

    @GenerateString
    private String categories;

    @GenerateString
    private String locale;

    @GenerateInteger
    private Integer limit;

    @GenerateInteger
    private Integer offset;

    @GenerateString
    private String sortBy;

    @GenerateString
    private String prices;

    @GenerateBoolean
    private Boolean openNow;

    @GenerateInteger
    private Integer openAt;

    @GenerateString
    private String attributes;

    private YelpSearchRequest instance;

    @Before
    public void setUp() throws Exception
    {

        setupData();
        setupMocks();

        instance = new YelpSearchRequest(searchTerm,
                                         location,
                                         latitude,
                                         longitude,
                                         radius,
                                         categories,
                                         locale,
                                         limit,
                                         offset,
                                         sortBy,
                                         prices,
                                         openNow,
                                         openAt,
                                         attributes);
    }

    private void setupData() throws Exception
    {

    }

    private void setupMocks() throws Exception
    {

    }

    @Test
    public void testNewBuilder()
    {
        YelpSearchRequest.Builder builder = YelpSearchRequest.newBuilder();
        assertThat(builder, notNullValue());
    }

    @Test
    public void testHasSearchTerm()
    {
        assertTrue(instance.hasSearchTerm());

        instance = new YelpSearchRequest(null,
                                         location,
                                         latitude,
                                         longitude,
                                         radius,
                                         categories,
                                         locale,
                                         limit,
                                         offset,
                                         sortBy,
                                         prices,
                                         openNow,
                                         openAt,
                                         attributes);

        assertFalse(instance.hasSearchTerm());
    }

    @Test
    public void testHasLocation()
    {
        assertTrue(instance.hasLocation());

        instance = new YelpSearchRequest(searchTerm,
                                         null,
                                         latitude,
                                         longitude,
                                         radius,
                                         categories,
                                         locale,
                                         limit,
                                         offset,
                                         sortBy,
                                         prices,
                                         openNow,
                                         openAt,
                                         attributes);

        assertFalse(instance.hasLocation());
    }

    @Test
    public void testHasLatitude()
    {
        assertTrue(instance.hasLatitude());

        instance = new YelpSearchRequest(searchTerm,
                                         location,
                                         null,
                                         longitude,
                                         radius,
                                         categories,
                                         locale,
                                         limit,
                                         offset,
                                         sortBy,
                                         prices,
                                         openNow,
                                         openAt,
                                         attributes);

        assertFalse(instance.hasLatitude());
    }

    @Test
    public void testHasLongitude()
    {
        assertTrue(instance.hasLongitude());

        instance = new YelpSearchRequest(searchTerm,
                                         location,
                                         latitude,
                                         null,
                                         radius,
                                         categories,
                                         locale,
                                         limit,
                                         offset,
                                         sortBy,
                                         prices,
                                         openNow,
                                         openAt,
                                         attributes);

        assertFalse(instance.hasLongitude());
    }

    @Test
    public void testHasRadius()
    {
        assertTrue(instance.hasRadius());

        instance = new YelpSearchRequest(searchTerm,
                                         location,
                                         latitude,
                                         longitude,
                                         null,
                                         categories,
                                         locale,
                                         limit,
                                         offset,
                                         sortBy,
                                         prices,
                                         openNow,
                                         openAt,
                                         attributes);

        assertFalse(instance.hasRadius());

    }

    @Test
    public void testHasCategories()
    {
        assertTrue(instance.hasCategories());
        
        instance = new YelpSearchRequest(searchTerm,
                                         location,
                                         latitude,
                                         longitude,
                                         radius,
                                         null,
                                         locale,
                                         limit,
                                         offset,
                                         sortBy,
                                         prices,
                                         openNow,
                                         openAt,
                                         attributes);

        assertFalse(instance.hasCategories());
    }

    @Test
    public void testHasLocale()
    {
        assertTrue(instance.hasLocale());

        instance = new YelpSearchRequest(searchTerm,
                                         location,
                                         latitude,
                                         longitude,
                                         radius,
                                         categories,
                                         null,
                                         limit,
                                         offset,
                                         sortBy,
                                         prices,
                                         openNow,
                                         openAt,
                                         attributes);
        
        assertFalse(instance.hasLocale());
    }

    @Test
    public void testHasLimit()
    {

        assertTrue(instance.hasLimit());
        
        instance = new YelpSearchRequest(searchTerm,
                                         location,
                                         latitude,
                                         longitude,
                                         radius,
                                         categories,
                                         locale,
                                         null,
                                         offset,
                                         sortBy,
                                         prices,
                                         openNow,
                                         openAt,
                                         attributes);
        
        assertFalse(instance.hasLimit());
    }

    @Test
    public void testHasOffset()
    {

        instance = new YelpSearchRequest(searchTerm,
                                         location,
                                         latitude,
                                         longitude,
                                         radius,
                                         categories,
                                         locale,
                                         limit,
                                         null,
                                         sortBy,
                                         prices,
                                         openNow,
                                         openAt,
                                         attributes);
    }

    @Test
    public void testHasSortBy()
    {
        assertTrue(instance.hasSortBy());
        
        instance = new YelpSearchRequest(searchTerm,
                                         location,
                                         latitude,
                                         longitude,
                                         radius,
                                         categories,
                                         locale,
                                         limit,
                                         offset,
                                         null,
                                         prices,
                                         openNow,
                                         openAt,
                                         attributes);
        
        assertFalse(instance.hasSortBy());
    }

    @Test
    public void testHasPrices()
    {
        assertTrue(instance.hasPrices());

        instance = new YelpSearchRequest(searchTerm,
                                         location,
                                         latitude,
                                         longitude,
                                         radius,
                                         categories,
                                         locale,
                                         limit,
                                         offset,
                                         sortBy,
                                         null,
                                         openNow,
                                         openAt,
                                         attributes);
        
        assertFalse(instance.hasPrices());
    }
    

    @Test
    public void testHasIsOpenNow()
    {
        assertTrue(instance.hasIsOpenNow());
        
        instance = new YelpSearchRequest(searchTerm,
                                         location,
                                         latitude,
                                         longitude,
                                         radius,
                                         categories,
                                         locale,
                                         limit,
                                         offset,
                                         sortBy,
                                         prices,
                                         null,
                                         openAt,
                                         attributes);
        
        assertFalse(instance.hasIsOpenNow());
    }

    @Test
    public void testHasOpenAt()
    {
        assertTrue(instance.hasOpenAt());
        
        instance = new YelpSearchRequest(searchTerm,
                                         location,
                                         latitude,
                                         longitude,
                                         radius,
                                         categories,
                                         locale,
                                         limit,
                                         offset,
                                         sortBy,
                                         prices,
                                         openNow,
                                         null,
                                         attributes);
        
        assertFalse(instance.hasOpenAt());
    }

    @Test
    public void testHasAttributes()
    {
        
        assertTrue(instance.hasAttributes());
        
        instance = new YelpSearchRequest(searchTerm,
                                         location,
                                         latitude,
                                         longitude,
                                         radius,
                                         categories,
                                         locale,
                                         limit,
                                         offset,
                                         sortBy,
                                         prices,
                                         openNow,
                                         openAt,
                                         null);
        
        assertFalse(instance.hasAttributes());
    }

    @Test
    public void testGetSearchTerm()
    {
        assertThat(instance.getSearchTerm(), is(searchTerm));
    }

    @Test
    public void testGetLocation()
    {
        assertThat(instance.getLocation(), is(location));
    }

    @Test
    public void testGetLatitude()
    {
        assertThat(instance.getLatitude(), is(latitude));
    }

    @Test
    public void testGetLongitude()
    {
        assertThat(instance.getLongitude(), is(longitude));
    }

    @Test
    public void testGetRadius()
    {
        assertThat(instance.getRadius(), is(radius));
    }

    @Test
    public void testGetCategories()
    {
        assertThat(instance.getCategories(), is(categories));
    }

    @Test
    public void testGetLocale()
    {
        assertThat(instance.getLocale(), is(locale));
    }

    @Test
    public void testGetLimit()
    {
        assertThat(instance.getLimit(), is(limit));
    }

    @Test
    public void testGetOffset()
    {
        assertThat(instance.getOffset(), is(offset));
    }

    @Test
    public void testGetSortBy()
    {
        assertThat(instance.getSortBy(), is(sortBy));
    }

    @Test
    public void testGetPrices()
    {
        assertThat(instance.getPrices(), is(prices));
    }

    @Test
    public void testGetOpenNow()
    {
        assertThat(instance.getOpenNow(), is(openNow));
    }

    @Test
    public void testGetOpenAt()
    {
        assertThat(instance.getOpenAt(), is(openAt));
    }

    @Test
    public void testGetAttributes()
    {
        assertThat(instance.getAttributes(), is(attributes));
    }


}
