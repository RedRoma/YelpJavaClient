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
import tech.redroma.yelp.exceptions.YelpAuthenticationException;
import tech.redroma.yelp.exceptions.YelpExcetion;
import tech.redroma.yelp.oauth.OAuthTokenProvider;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.http.AlchemyHttp;
import tech.sirwellington.alchemy.http.HttpResponse;
import tech.sirwellington.alchemy.http.exceptions.AlchemyHttpException;
import tech.sirwellington.alchemy.http.mock.AlchemyHttpMock;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.GeneratePojo;
import tech.sirwellington.alchemy.test.junit.runners.GenerateString;
import tech.sirwellington.alchemy.test.junit.runners.GenerateURL;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tech.sirwellington.alchemy.generator.AlchemyGenerator.one;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.generator.GeolocationGenerators.latitudes;
import static tech.sirwellington.alchemy.generator.GeolocationGenerators.longitudes;
import static tech.sirwellington.alchemy.generator.ObjectGenerators.pojos;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateString.Type.ALPHABETIC;

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
    
    @GenerateString(ALPHABETIC)
    private String businessID;
    
    @GeneratePojo
    private YelpBusinessDetails businessDetails;
    
    private YelpAPIImpl instance;
    
    private String expectedGetBusinessDetailsURL;
    
    private String expectedSearchURL;
    
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
        
        expectedGetBusinessDetailsURL = baseURL + YelpAPIImpl.URLS.BUSINESSES + "/" + businessID;
        expectedSearchURL = baseURL + YelpAPIImpl.URLS.BUSINESS_SEARCH;
    }
    
    private void setupMocks() throws Exception
    {
        when(tokenProvider.getToken()).thenReturn(token);
        
        when(httpResponse.bodyAsArrayOf(YelpBusiness.class))
            .thenReturn(businesses);
        
    }
    
    @Test
    public void testGetBusinessDetails() throws Exception
    {
        http = AlchemyHttpMock.begin()
            .whenGet()
            .noBody()
            .at(expectedGetBusinessDetailsURL)
            .thenReturnPOJO(businessDetails)
            .build();
        
        instance = new YelpAPIImpl(http, tokenProvider, baseURL.toString());
        
        YelpBusinessDetails result = instance.getBusinessDetails(businessID);
        assertThat(result, is(businessDetails));
    }
    
    @DontRepeat
    @Test
    public void testGetBusinessDetailsWhenFails() throws Exception
    {
        Exception ex = new AlchemyHttpException();
        
        http = AlchemyHttpMock.begin()
            .whenGet()
            .noBody()
            .at(expectedGetBusinessDetailsURL)
            .thenThrow(ex)
            .build();
        
        instance = new YelpAPIImpl(http, tokenProvider, baseURL.toString());
        
        assertThrows(() -> instance.getBusinessDetails(businessID))
            .isInstanceOf(YelpExcetion.class);
    }
    
    @DontRepeat
    @Test
    public void testGetBusinessDetailsWhenTokenInvalid() throws Exception
    {
        HttpResponse fakeResponse = mock(HttpResponse.class);
        when(fakeResponse.statusCode()).thenReturn(401);
        Exception ex = new AlchemyHttpException(fakeResponse);
        
        http = AlchemyHttpMock.begin()
            .whenGet()
            .noBody()
            .at(expectedGetBusinessDetailsURL)
            .thenThrow(ex)
            .build();
        
        instance = new YelpAPIImpl(http, tokenProvider, baseURL.toString());
        
        assertThrows(() -> instance.getBusinessDetails(businessID))
            .isInstanceOf(YelpAuthenticationException.class);
    }
    
    @Test
    public void testSearchForBusinesses() throws Exception
    {
        http = AlchemyHttpMock.begin()
            .whenGet()
            .anyBody()
            .at(expectedSearchURL)
            .thenReturnResponse(httpResponse)
            .build();
        
        instance = new YelpAPIImpl(http, tokenProvider, baseURL.toString());
        
        List<YelpBusiness> results = instance.searchForBusinesses(request);
        assertThat(results, is(businesses));
        AlchemyHttpMock.verifyAllRequestsMade(http);
    }
    
    @DontRepeat
    @Test
    public void testSearchForBusinessesWhenFails() throws Exception
    {
        Exception ex = new AlchemyHttpException();
        
        http = AlchemyHttpMock.begin()
            .whenGet()
            .anyBody()
            .at(expectedSearchURL)
            .thenThrow(ex)
            .build();
        
        instance = new YelpAPIImpl(http, tokenProvider, baseURL.toString());
        
        assertThrows(() -> instance.searchForBusinesses(request))
            .isInstanceOf(YelpExcetion.class);
    }
    
    @DontRepeat
    @Test
    public void testSearchForBusinessesWhenTokenInvalid() throws Exception
    {
        HttpResponse fakeResponse = mock(HttpResponse.class);
        when(fakeResponse.statusCode()).thenReturn(401);
        Exception ex = new AlchemyHttpException(fakeResponse);

        http = AlchemyHttpMock.begin()
            .whenGet()
            .noBody()
            .at(expectedSearchURL)
            .thenThrow(ex)
            .build();
        
        instance = new YelpAPIImpl(http, tokenProvider, baseURL.toString());
        
        assertThrows(() -> instance.searchForBusinesses(request))
            .isInstanceOf(YelpAuthenticationException.class);
    }
    
}
