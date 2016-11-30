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
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.GeneratePojo;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 *
 * @author SirWellington
 */
@Repeat(10)
@RunWith(AlchemyTestRunner.class)
public class YelpReviewTest 
{
    
    @GeneratePojo
    private YelpReview instance;
    
    @GeneratePojo
    private YelpReview first;
    
    @GeneratePojo
    private YelpReview second;
    
    @Before
    public void setUp() throws Exception
    {
    }

    @DontRepeat
    @Test
    public void testInstance()
    {
        assertThat(instance, notNullValue());
    }
    
    @Test
    public void testInstanceHasAllData()
    {
        assertThat(instance.text, not(isEmptyOrNullString()));
        assertThat(instance.url, not(isEmptyOrNullString()));
        assertThat(instance.user, notNullValue());
        assertThat(instance.timeCreated, notNullValue());
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
}