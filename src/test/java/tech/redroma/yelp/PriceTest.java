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

import com.google.common.base.Strings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.GenerateEnum;
import tech.sirwellington.alchemy.test.junit.runners.GenerateInteger;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static tech.sirwellington.alchemy.generator.AlchemyGenerator.one;
import static tech.sirwellington.alchemy.generator.StringGenerators.alphabeticString;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateInteger.Type.NEGATIVE;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateInteger.Type.RANGE;


/**
 *
 * @author SirWellington
 */
@Repeat(10)
@RunWith(AlchemyTestRunner.class)
public class PriceTest 
{
    @GenerateEnum
    private Price price;

    @GenerateInteger(value = RANGE, min = 1, max = 4)
    Integer validNumber;

    @GenerateInteger(value = RANGE, min = 5, max = 100)
    Integer invalidNumber;

    @GenerateInteger(value = NEGATIVE)
    Integer negativeNumber;
    
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
        assertThat(price, notNullValue());
    }

    @Test
    public void testFromNumberWithValid()
    {
        Price result = Price.fromNumber(validNumber);
        assertThat(result, notNullValue());
    }

    @Test
    public void testFromNumberWithInvalid()
    {
        assertThrows(() -> Price.fromNumber(invalidNumber))
            .isInstanceOf(IllegalArgumentException.class);
        
        assertThrows(() -> Price.fromNumber(negativeNumber))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValues()
    {
    }

    @Test
    public void testValueOf()
    {
    }

    @Test
    public void testAsString()
    {
        String result = price.asString();
        assertThat(result, is(price.toString()));
    }

    @Test
    public void testFromString()
    {
        String string = Strings.repeat("$", validNumber);
        
        Price result = Price.fromString(string);
        assertThat(result, notNullValue());
    }

    @DontRepeat
    @Test
    public void testFromStringWithInvalidArguments()
    {
        assertThrows(() -> Price.fromString(""))
            .isInstanceOf(IllegalArgumentException.class);
        
        assertThrows(() -> Price.fromString(null))
            .isInstanceOf(IllegalArgumentException.class);
        
        String invalid = one(alphabeticString());
        assertThrows(() -> Price.fromString(invalid))
            .isInstanceOf(IllegalArgumentException.class);
    }

}