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

package tech.redroma.yelp.oauth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.GenerateString;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static tech.sirwellington.alchemy.generator.AlchemyGenerator.one;
import static tech.sirwellington.alchemy.generator.StringGenerators.hexadecimalString;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateString.Type.HEXADECIMAL;

/**
 *
 * @author SirWellington
 */
@Repeat(50)
@RunWith(AlchemyTestRunner.class)
public class BasicProviderTest
{

    private BasicProvider instance;

    @GenerateString(HEXADECIMAL)
    private String token;

    @Before
    public void setUp() throws Exception
    {
        instance = new BasicProvider(token);
    }

    @DontRepeat
    @Test
    public void testConstructor()
    {
        assertThrows(() -> new BasicProvider(null)).isInstanceOf(IllegalArgumentException.class);
        assertThrows(() -> new BasicProvider("")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetToken()
    {
        String result = instance.getToken();
        assertThat(result, is(token));
    }

    @Test
    public void testHashCode()
    {
        BasicProvider other = new BasicProvider(token);
        assertThat(other.hashCode(), is(instance.hashCode()));
    }

    @Test
    public void testHashCodeWhenDifferent()
    {
        String otherToken = one(hexadecimalString(10));
        BasicProvider other = new BasicProvider(otherToken);

        assertThat(other.hashCode(), not(instance.hashCode()));
    }

    @Test
    public void testEquals()
    {
        BasicProvider other = new BasicProvider(token);
        assertThat(other, is(instance));
    }

    @Test
    public void testEqualsWhenDifferent()
    {
        String otherToken = one(hexadecimalString(10));
        BasicProvider other = new BasicProvider(otherToken);

        assertThat(other, not(instance));
    }

}
