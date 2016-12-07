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

import com.google.gson.JsonElement;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import sir.wellington.alchemy.collections.lists.Lists;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.generator.StringGenerators;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.GeneratePojo;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static tech.redroma.yelp.Resources.GSON;
import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.GeolocationAssertions.validLatitude;
import static tech.sirwellington.alchemy.arguments.assertions.GeolocationAssertions.validLongitude;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.nonEmptyString;
import static tech.sirwellington.alchemy.generator.AlchemyGenerator.one;
import static tech.sirwellington.alchemy.generator.GeolocationGenerators.latitudes;
import static tech.sirwellington.alchemy.generator.GeolocationGenerators.longitudes;

/**
 *
 * @author SirWellington
 */
@Repeat(10)
@RunWith(AlchemyTestRunner.class)
public class YelpBusinessDetailsTest
{

    @GeneratePojo
    private YelpBusinessDetails instance;

    @GeneratePojo
    private YelpBusinessDetails first;

    @GeneratePojo
    private YelpBusinessDetails second;

    @Before
    public void setUp()
    {
        setupData();
    }

    private void setupData()
    {
        instance.coordinates.latitude = one(latitudes());
        instance.coordinates.longitude = one(longitudes());
        instance.price = one(prices());

        first.coordinates.latitude = one(latitudes());
        first.coordinates.longitude = one(longitudes());
        first.price = one(prices());

        second.coordinates.latitude = one(latitudes());
        second.coordinates.longitude = one(longitudes());
        second.price = one(prices());
    }

    @DontRepeat
    @Test
    public void testInstanceIsNotNull()
    {
        assertThat(instance, notNullValue());
    }

    @Test
    public void testInstanceHasAllData()
    {
        checkBusiness(instance);
        checkBusiness(first);
        checkBusiness(second);
    }

    private void checkBusiness(YelpBusinessDetails business)
    {
        assertThat(business.id, not(isEmptyOrNullString()));
        assertThat(business.imageURL, not(isEmptyOrNullString()));
        assertThat(business.name, not(isEmptyOrNullString()));
        assertThat(business.phone, not(isEmptyOrNullString()));
        assertThat(business.price, not(isEmptyOrNullString()));
        assertThat(business.url, not(isEmptyOrNullString()));
        assertThat(business.isClosed, notNullValue());
        assertThat(business.isClaimed, notNullValue());
        assertThat(business.categories, notNullValue());
        assertThat(business.rating, is(greaterThan(0.0)));
        assertThat(business.hours, not(empty()));
        
        assertThat(business.photosURLS, not(empty()));
        business.photosURLS.forEach(p -> checkThat(p).is(nonEmptyString()));

        business.categories.forEach(this::checkCategory);
        checkCoordinate(business.coordinates);
        checkAddress(business.location);
        business.hours.forEach(this::checkHours);
    }

    private void checkCategory(Category category)
    {
        assertThat(category, notNullValue());
        assertThat(category.alias, not(isEmptyOrNullString()));
        assertThat(category.title, not(isEmptyOrNullString()));
    }

    private void checkCoordinate(Coordinate coordinate)
    {
        assertThat(coordinate, notNullValue());
        checkThat(coordinate.latitude)
            .is(validLatitude());
        checkThat(coordinate.longitude)
            .is(validLongitude());
    }

    private void checkAddress(Address address)
    {
        assertThat(address, notNullValue());
        assertThat(address.address1, not(isEmptyOrNullString()));
        assertThat(address.city, not(isEmptyOrNullString()));
        assertThat(address.country, not(isEmptyOrNullString()));
        assertThat(address.state, not(isEmptyOrNullString()));
        assertThat(address.zipCode, not(isEmptyOrNullString()));
    }

    private void checkHours(YelpBusinessDetails.Hours hours)
    {
        assertThat(hours, notNullValue());
        assertThat(hours.hoursType, notNullValue());
        assertThat(hours.isOpenNow, notNullValue());
        assertThat(hours.open, not(empty()));
        hours.open.forEach(this::checkHour);
    }
    
    private void checkHour(YelpBusinessDetails.Hours.OpenTimes times)
    {
        assertThat(times, notNullValue());
        assertThat(times.start, not(isEmptyOrNullString()));
        assertThat(times.end, not(isEmptyOrNullString()));
        assertThat(times.day, notNullValue());
        assertThat(times.isOvernight, notNullValue());
    }
    
    @Test
    public void testEqualsWhenNotEqual()
    {
        assertThat(first, not(second));
    }

    @Test
    public void testEqualsWhenEquals()
    {
        assertThat(first, is(first));
    }

    @Test
    public void testHashCode()
    {
        assertThat(first.hashCode(), is(first.hashCode()));
        assertThat(first.hashCode(), not(second.hashCode()));
    }

    @Repeat(25)
    @Test
    public void testSerialization()
    {
        JsonElement json = GSON.toJsonTree(instance);
        assertThat(json, notNullValue());
        assertThat(json.isJsonObject(), is(true));
    }

    @DontRepeat
    @Test
    public void testDeserialization() throws IOException
    {
        String json = Resources.loadResource("business-details.json");

        YelpBusinessDetails result = GSON.fromJson(json, YelpBusinessDetails.class);
        assertThat(result, notNullValue());
        checkBusiness(result);
    }

    @Test
    public void testGetPriceLevel()
    {
        Price price = instance.getPriceLevel();
        assertThat(price, notNullValue());
    }

    private AlchemyGenerator<String> prices()
    {
        return StringGenerators.stringsFromFixedList("$", "$$", "$$$", "$$$$");
    }

    @DontRepeat
    @Test
    public void testIsOpenNowWhenOpen()
    {
        YelpBusinessDetails.Hours hours = new YelpBusinessDetails.Hours();
        hours.isOpenNow = true;
        hours.hoursType = "REGULAR";
        hours.open = Lists.emptyList();
        
        instance.hours = Lists.createFrom(hours);
        
        assertTrue(instance.isOpenNow());
    }
   
    @DontRepeat
    @Test
    public void testIsOpenNowWhenClosed()
    {
        YelpBusinessDetails.Hours hours = new YelpBusinessDetails.Hours();
        hours.isOpenNow = false;
        hours.hoursType = "REGULAR";
        hours.open = Lists.emptyList();

        instance.hours = Lists.createFrom(hours);

        assertFalse(instance.isOpenNow());
    }
}
