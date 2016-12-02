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
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import tech.redroma.yelp.exceptions.YelpExcetion;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author SirWellington
 */
@Repeat(10)
@RunWith(AlchemyTestRunner.class)
public class YelpAPITest 
{

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
    public void testGetBusinessDetails_YelpBusiness()
    {
    }

    @Test
    public void testGetBusinessDetails_String()
    {
    }

    @Test
    public void testSearchForBusinesses()
    {
    }

    @Test
    public void testNewInstance()
    {
    }

    public class YelpAPIImpl implements YelpAPI
    {

        public YelpBusinessDetails getBusinessDetails(String businessId) throws YelpExcetion
        {
            return null;
        }

        public List<YelpBusiness> searchForBusinesses(YelpSearchRequest request) throws YelpExcetion
        {
            return null;
        }
    }

}