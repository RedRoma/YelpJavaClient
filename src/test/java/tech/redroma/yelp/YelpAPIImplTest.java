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
import tech.redroma.yelp.YelpAPIImpl.URLS;
import tech.redroma.yelp.exceptions.YelpAuthenticationException;
import tech.redroma.yelp.exceptions.YelpBadArgumentException;
import tech.redroma.yelp.exceptions.YelpException;
import tech.redroma.yelp.exceptions.YelpOperationFailedException;
import tech.redroma.yelp.oauth.OAuthTokenProvider;
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
import static tech.sirwellington.alchemy.generator.EnumGenerators.enumValueOf;
import static tech.sirwellington.alchemy.generator.GeolocationGenerators.latitudes;
import static tech.sirwellington.alchemy.generator.GeolocationGenerators.longitudes;
import static tech.sirwellington.alchemy.generator.NumberGenerators.integers;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateString.Type.ALPHABETIC;

/**
 *
 * @author SirWellington
 */
@Repeat(50)
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
    
    @GeneratePojo
    private YelpResponses.SearchResponse searchResponse;
    
    @GeneratePojo
    private YelpResponses.ReviewsResponse reviewsResponse;
    
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
    
    private String expectedReviewsURL;
    
    @Before
    public void setUp() throws Exception
    {
        
        setupData();
        setupMocks();
        
        instance = new YelpAPIImpl(http, tokenProvider, baseURL.toString());
    }
    
    private void setupData() throws Exception
    {
        businesses = searchResponse.businesses;
        
        longitude = one(longitudes());
        latitude = one(latitudes());
        
        request = YelpSearchRequest.newBuilder()
            .withSearchTerm(searchTerm)
            .withCoordinate(Coordinate.of(latitude, longitude))
            .withLimit(one(integers(5, YelpSearchRequest.Builder.MAX_LIMIT)))
            .withLocale(enumValueOf(Locale.Locales.class).get())
            .build();
        
        expectedGetBusinessDetailsURL = baseURL + URLS.BUSINESSES + "/" + businessID;
        expectedSearchURL = baseURL + URLS.BUSINESS_SEARCH;
        expectedReviewsURL = baseURL + URLS.BUSINESSES + "/" + businessID + URLS.REVIEWS;
    }
    
    private void setupMocks() throws Exception
    {
        when(tokenProvider.getToken()).thenReturn(token);
        
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
            .isInstanceOf(YelpException.class);
    }
    
    @DontRepeat
    @Test
    public void testGetBusinessDetailsWhenTokenInvalid() throws Exception
    {
        Exception ex = createAlchemyExceptionWithStatus(401);
        
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
            .thenReturnPOJO(searchResponse)
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
            .isInstanceOf(YelpException.class);
    }
    
    @DontRepeat
    @Test
    public void testSearchForBusinessesWhenTokenInvalid() throws Exception
    {
        AlchemyHttpException ex = createAlchemyExceptionWithStatus(401);
        
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

    @Test
    public void testGetReviewsForBusiness() throws Exception
    {
        
        http = AlchemyHttpMock.begin()
            .whenGet()
            .noBody()
            .at(expectedReviewsURL)
            .thenReturnPOJO(reviewsResponse)
            .build();
        
        instance = new YelpAPIImpl(http, tokenProvider, baseURL.toString());
        
        List<YelpReview> results = instance.getReviewsForBusiness(businessID);
        
        assertThat(results, is(reviewsResponse.reviews));
        
        AlchemyHttpMock.verifyAllRequestsMade(http);
    }
    
    @Test
    public void testGetReviewsForBusinessWhenBadArguments() throws Exception
    {
        AlchemyHttpException ex = createAlchemyExceptionWithStatus(400);
        http = AlchemyHttpMock.begin()
            .whenGet()
            .noBody()
            .at(expectedReviewsURL)
            .thenThrow(ex)
            .build();
        
        instance = new YelpAPIImpl(http, tokenProvider, baseURL.toString());
        
        assertThrows(() -> instance.getReviewsForBusiness(businessID))
            .isInstanceOf(YelpBadArgumentException.class);
    }
    
    @Test
    public void testGetReviewsForBusinessWhenOperationFails() throws Exception
    {
        AlchemyHttpException ex = createAlchemyExceptionWithStatus(500);
        http = AlchemyHttpMock.begin()
            .whenGet()
            .noBody()
            .at(expectedReviewsURL)
            .thenThrow(ex)
            .build();
        
        instance = new YelpAPIImpl(http, tokenProvider, baseURL.toString());
        
        assertThrows(() -> instance.getReviewsForBusiness(businessID))
            .isInstanceOf(YelpOperationFailedException.class);
    }

    @Test
    public void testGetReviewsForBusinessWhenTokenInvalid() throws Exception
    {
        AlchemyHttpException ex = createAlchemyExceptionWithStatus(401);
        
        http = AlchemyHttpMock.begin()
            .whenGet()
            .noBody()
            .at(expectedReviewsURL)
            .thenThrow(ex)
            .build();
        
        instance = new YelpAPIImpl(http, tokenProvider, baseURL.toString());
        
        assertThrows(() -> instance.getReviewsForBusiness(businessID))
            .isInstanceOf(YelpAuthenticationException.class);
    }

    private AlchemyHttpException createAlchemyExceptionWithStatus(int code)
    {
        HttpResponse fakeResponse = createFakeHttpResponseWithCode(code);
        AlchemyHttpException ex = new AlchemyHttpException(fakeResponse);
        return ex;
    }

    private HttpResponse createFakeHttpResponseWithCode(int code)
    {
        HttpResponse fakeResponse = mock(HttpResponse.class);
        when(fakeResponse.statusCode()).thenReturn(code);
        return fakeResponse;
    }



}
