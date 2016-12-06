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
import static org.junit.Assert.assertThat;


/**
 *
 * @author SirWellington
 */
@Repeat(10)
@RunWith(AlchemyTestRunner.class)
public class CategoryTest 
{
    @GeneratePojo
    private Category instance;
    
    @GeneratePojo
    private Category first;
    
    @GeneratePojo
    private Category second;
    
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
    public void testInstance()
    {
        assertThat(instance, notNullValue());
        assertThat(instance.alias, not(isEmptyOrNullString()));
        assertThat(instance.title, not(isEmptyOrNullString()));
    }

    @Test
    public void testHashCode()
    {
        int hashCode = instance.hashCode();
        assertThat(hashCode, not(0));
    }

    @Test
    public void testEquals()
    {
        Category copy = new Category();
        copy.alias = instance.alias;
        copy.title = instance.title;
        assertThat(copy, is(instance));
    }
    
    @Test
    public void testEqualsWhenDifferent()
    {
        assertThat(first, not(second));
    }

}