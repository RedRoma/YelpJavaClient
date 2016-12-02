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
import tech.redroma.yelp.exceptions.YelpBadArgumentException;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;

import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;

/**
 *
 * @author SirWellington
 */
@RunWith(AlchemyTestRunner.class)
public class YelpSearchRequestBuilderTest 
{
    private YelpSearchRequest.Builder instance;
    
    @Before
    public void setUp()
    {
        instance = YelpSearchRequest.Builder.newInstance();
    }
    
    @Test
    public void testBuildWithNoParameters()
    {
        assertThrows(() -> instance.build()).isInstanceOf(YelpBadArgumentException.class);
    }
    
    @Test
    public void testWhenBothOpenAtAndOpenNowAreSet()
    {
        instance.lookingForOpenNow()
            .withBusinessesOpenAt(10);
        
        assertThrows(() -> instance.build()).isInstanceOf(YelpBadArgumentException.class);
    }
}
