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

import java.net.URL;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import tech.redroma.yelp.oauth.OAuthTokenProvider;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.http.AlchemyHttp;
import tech.sirwellington.alchemy.http.HttpResponse;
import tech.sirwellington.alchemy.http.mock.AlchemyHttpMock;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.GenerateString;
import tech.sirwellington.alchemy.test.junit.runners.GenerateURL;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static tech.sirwellington.alchemy.generator.AlchemyGenerator.one;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.generator.GeolocationGenerators.latitudes;
import static tech.sirwellington.alchemy.generator.GeolocationGenerators.longitudes;
import static tech.sirwellington.alchemy.generator.ObjectGenerators.pojos;

/**
 *
 * @author SirWellington
 */
@Repeat(10)
@RunWith(AlchemyTestRunner.class)
public class YelpAPIImplTest
{

    @Mock
    private AlchemyHttp http;

    @Mock
    private OAuthTokenProvider tokenProvider;

    @GenerateURL
    private URL baseURL;

    @GenerateString
    private String token;

    @Mock
    private HttpResponse httpResponse;

    private List<YelpBusiness> businesses;

    private YelpSearchRequest request;

    @GenerateString
    private String searchTerm;

    private double latitude;
    private double longitude;

    private YelpAPIImpl instance;

    @Before
    public void setUp() throws Exception
    {

        setupData();
        setupMocks();

        instance = new YelpAPIImpl(http, tokenProvider, baseURL.toString());
    }

    private void setupData() throws Exception
    {
        AlchemyGenerator<YelpBusiness> pojos = pojos(YelpBusiness.class);
        businesses = listOf(pojos);

        longitude = one(longitudes());
        latitude = one(latitudes());

        request = YelpSearchRequest.newBuilder()
            .withSearchTerm(searchTerm)
            .withCoordinate(Coordinate.of(latitude, longitude))
            .build();
    }

    private void setupMocks() throws Exception
    {
        when(tokenProvider.getToken()).thenReturn(token);

        when(httpResponse.bodyAsArrayOf(YelpBusiness.class))
            .thenReturn(businesses);

    }

    @Test
    public void testGetBusinessDetails()
    {
    }

    @Test
    public void testSearchForBusinesses() throws Exception
    {
        //We need a specific URL for each method, so a mock HTTP must be constructed
        //For each.
        String expectedURL = baseURL + YelpAPIImpl.URLS.BUSINESS_SEARCH;
        http = AlchemyHttpMock.begin()
            .whenGet()
            .anyBody()
            .at(expectedURL)
            .thenReturnResponse(httpResponse)
            .build();

        instance = new YelpAPIImpl(http, tokenProvider, baseURL.toString());

        List<YelpBusiness> results = instance.searchForBusinesses(request);
        assertThat(results, is(businesses));
        AlchemyHttpMock.verifyAllRequestsMade(http);
    }

}