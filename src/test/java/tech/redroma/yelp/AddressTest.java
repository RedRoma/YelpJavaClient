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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
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
public class AddressTest
{

    @GeneratePojo
    private Address instance;

    @GeneratePojo
    private Address first;

    @GeneratePojo
    private Address second;

    @Before
    public void setUp() throws Exception
    {

    }

    @Test
    public void testInstance()
    {
        assertThat(instance, notNullValue());
        
        assertThat(instance.address1, not(isEmptyOrNullString()));
        assertThat(instance.address2, not(isEmptyOrNullString()));
        assertThat(instance.address3, not(isEmptyOrNullString()));
        assertThat(instance.city, not(isEmptyOrNullString()));
        assertThat(instance.state, not(isEmptyOrNullString()));
        assertThat(instance.country, not(isEmptyOrNullString()));
        assertThat(instance.zipCode, not(isEmptyOrNullString()));
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

    @Test
    public void testHasAddress2()
    {
        assertTrue(instance.hasAddress2());
        instance.address2 = null;
        assertFalse(instance.hasAddress2());
    }

    @Test
    public void testHasAddress3()
    {
        assertTrue(instance.hasAddress3());
        instance.address3 = null;
        assertFalse(instance.hasAddress3());
    }

    @Test
    public void testHasZipCode()
    {
        assertTrue(instance.hasZipCode());
        instance.zipCode = null;
        assertFalse(instance.hasZipCode());
    }

    @Test
    public void testHasCountry()
    {
        assertTrue(instance.hasCountry());
        instance.country = null;
        assertFalse(instance.hasCountry());
    }

    @Test
    public void testHasCity()
    {
        assertTrue(instance.hasCity());
        instance.city = null;
        assertFalse(instance.hasCity());
    }

    @Test
    public void testHasState()
    {
        assertTrue(instance.hasState());
        instance.state = null;
        assertFalse(instance.hasState());
    }

}
