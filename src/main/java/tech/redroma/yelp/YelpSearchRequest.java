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
import java.util.Objects;
import java.util.stream.Collectors;
import sir.wellington.alchemy.collections.lists.Lists;
import tech.redroma.yelp.exceptions.YelpAreaTooLargeException;
import tech.redroma.yelp.exceptions.YelpBadArgumentException;
import tech.sirwellington.alchemy.annotations.arguments.NonEmpty;
import tech.sirwellington.alchemy.annotations.arguments.Optional;
import tech.sirwellington.alchemy.annotations.arguments.Positive;
import tech.sirwellington.alchemy.annotations.arguments.Required;
import tech.sirwellington.alchemy.annotations.concurrency.Immutable;
import tech.sirwellington.alchemy.annotations.concurrency.ThreadSafe;
import tech.sirwellington.alchemy.annotations.designs.patterns.BuilderPattern;
import tech.sirwellington.alchemy.annotations.objects.Pojo;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.stream.Collectors.joining;
import static tech.sirwellington.alchemy.annotations.designs.patterns.BuilderPattern.Role.BUILDER;
import static tech.sirwellington.alchemy.annotations.designs.patterns.BuilderPattern.Role.PRODUCT;
import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;
import static tech.sirwellington.alchemy.arguments.assertions.CollectionAssertions.nonEmptyList;
import static tech.sirwellington.alchemy.arguments.assertions.GeolocationAssertions.validLatitude;
import static tech.sirwellington.alchemy.arguments.assertions.GeolocationAssertions.validLongitude;
import static tech.sirwellington.alchemy.arguments.assertions.NumberAssertions.greaterThanOrEqualTo;
import static tech.sirwellington.alchemy.arguments.assertions.NumberAssertions.lessThanOrEqualTo;
import static tech.sirwellington.alchemy.arguments.assertions.NumberAssertions.positiveInteger;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.nonEmptyString;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.stringWithLength;

/**
 * Use to make search requests to the Yelp API. Use {@link #newBuilder() } to create a request object.
 * <p>
 * Official Yelp documentation can be found <a href="https://www.yelp.com/developers/documentation/v3/business_search">here</a>.
 * 
 * @author SirWellington
 * @see YelpSearchRequest.Builder
 * @see <a href="https://www.yelp.com/developers/documentation/v3/business_search">https://www.yelp.com/developers/documentation/v3/business_search</a>
 */
@Pojo
@Immutable
@ThreadSafe
@BuilderPattern(role = PRODUCT)
public final class YelpSearchRequest
{
    
    public static YelpSearchRequest.Builder newBuilder()
    {
        return Builder.newInstance();
    }

    @Optional
    private final String searchTerm;

    private final String location;

    /**
     * The latitude of the location you want to search near by. Required if location is not provided.
     */
    private final Double latitude;

    /**
     * Longitude of the location you want to search near by. required if the location is not provided.
     */
    private final Double longitude;

    @Optional
    private final Integer radius;

    @Optional
    private final String categories;

    @Optional
    private final String locale;

    @Optional
    private final Integer limit;

    @Optional
    private final Integer offset;

    @Optional
    private final String sortBy;

    @Optional
    private final String prices;

    @Optional
    private final Boolean openNow;
    
    @Optional
    private final Integer openAt;

    @Optional
    private final String attributes;

    YelpSearchRequest(String searchTerm,
                    String location,
                    Double latitude,
                    Double longitude,
                    Integer radius,
                    String categories,
                    String locale,
                    Integer limit,
                    Integer offset,
                    String sortBy,
                    String price,
                    Boolean openNow,
                    Integer openAt,
                    String attributes)
    {
        this.searchTerm = searchTerm;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.categories = categories;
        this.locale = locale;
        this.limit = limit;
        this.offset = offset;
        this.sortBy = sortBy;
        this.prices = price;
        this.openNow = openNow;
        this.openAt = openAt;
        this.attributes = attributes;
    }
    
    public boolean hasSearchTerm()
    {
        return !isNullOrEmpty(searchTerm);
    }
    
    public boolean hasLocation()
    {
        return !isNullOrEmpty(location);
    }
    
    public boolean hasLatitude()
    {
        return latitude != null;
    }
    
    public boolean hasLongitude()
    {
        return longitude != null;
    }
    
    public boolean hasRadius()
    {
        return radius != null;
    }
    
    public boolean hasCategories()
    {
        return !isNullOrEmpty(categories);
    }
    
    public boolean hasLocale()
    {
        return !isNullOrEmpty(locale);
    }
    
    public boolean hasLimit()
    {
        return limit != null && limit > 0;
    }
    
    public boolean hasOffset()
    {
        return offset != null && offset > 0;
    }
    
    public boolean hasSortBy()
    {
        return !isNullOrEmpty(sortBy);
    }
    
    public boolean hasPrices()
    {
        return !isNullOrEmpty(prices);
    }
    
    public boolean hasIsOpenNow()
    {
        return openNow != null;
    }
    
    public boolean hasOpenAt()
    {
        return openAt != null;
    }
    
    public boolean hasAttributes()
    {
        return !isNullOrEmpty(attributes);
    }
    
    public String getSearchTerm()
    {
        return searchTerm;
    }

    public String getLocation()
    {
        return location;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public Integer getRadius()
    {
        return radius;
    }

    public String getCategories()
    {
        return categories;
    }

    public String getLocale()
    {
        return locale;
    }

    public Integer getLimit()
    {
        return limit;
    }

    public Integer getOffset()
    {
        return offset;
    }

    public String getSortBy()
    {
        return sortBy;
    }

    public String getPrices()
    {
        return prices;
    }

    public Boolean getOpenNow()
    {
        return openNow;
    }

    public Integer getOpenAt()
    {
        return openAt;
    }

    public String getAttributes()
    {
        return attributes;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.searchTerm);
        hash = 17 * hash + Objects.hashCode(this.location);
        hash = 17 * hash + Objects.hashCode(this.latitude);
        hash = 17 * hash + Objects.hashCode(this.longitude);
        hash = 17 * hash + Objects.hashCode(this.radius);
        hash = 17 * hash + Objects.hashCode(this.categories);
        hash = 17 * hash + Objects.hashCode(this.locale);
        hash = 17 * hash + Objects.hashCode(this.limit);
        hash = 17 * hash + Objects.hashCode(this.offset);
        hash = 17 * hash + Objects.hashCode(this.sortBy);
        hash = 17 * hash + Objects.hashCode(this.prices);
        hash = 17 * hash + Objects.hashCode(this.openNow);
        hash = 17 * hash + Objects.hashCode(this.openAt);
        hash = 17 * hash + Objects.hashCode(this.attributes);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final YelpSearchRequest other = (YelpSearchRequest) obj;
        if (!Objects.equals(this.searchTerm, other.searchTerm))
        {
            return false;
        }
        if (!Objects.equals(this.location, other.location))
        {
            return false;
        }
        if (!Objects.equals(this.categories, other.categories))
        {
            return false;
        }
        if (!Objects.equals(this.locale, other.locale))
        {
            return false;
        }
        if (!Objects.equals(this.sortBy, other.sortBy))
        {
            return false;
        }
        if (!Objects.equals(this.prices, other.prices))
        {
            return false;
        }
        if (!Objects.equals(this.attributes, other.attributes))
        {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude))
        {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude))
        {
            return false;
        }
        if (!Objects.equals(this.radius, other.radius))
        {
            return false;
        }
        if (!Objects.equals(this.limit, other.limit))
        {
            return false;
        }
        if (!Objects.equals(this.offset, other.offset))
        {
            return false;
        }
        if (!Objects.equals(this.openNow, other.openNow))
        {
            return false;
        }
        if (!Objects.equals(this.openAt, other.openAt))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "YelpSearchQuery{" + "searchTerm=" + searchTerm + ", location=" + location + ", latitude=" + latitude + ", longitude=" + longitude + ", radius=" + radius + ", categories=" + categories + ", locale=" + locale + ", limit=" + limit + ", offset=" + offset + ", sortBy=" + sortBy + ", price=" + prices + ", openNow=" + openNow + ", openAt=" + openAt + '}';
    }

    public enum SortType
    {
        BEST_MATCH("best_match"),
        RATING("rating"),
        REVIEW_COUNT("review_count"),
        DISTANCE("distance");

        public final String text;

        private SortType(String text)
        {
            this.text = text;
        }
    }

    public enum PricingLevel
    {
        $(1),
        $$(2),
        $$$(3),
        $$$$(4);

        final int number;

        private PricingLevel(int number)
        {
            this.number = number;
        }
    }
    
    public enum Attribute
    {
        HOT_AND_NEW("hot_and_new"),
        DEALS("deals")
        ;
        
        private final String text;

        private Attribute(String text)
        {
            this.text = text;
        }
        
    }

    @BuilderPattern(role = BUILDER)
    public final static class Builder
    {

        /**
         * The maximum limit that can used per query.
         */
        public final static int MAX_LIMIT = 50;
        
        /**
         * The maximum number of offset that can used in a query.
         */
        public final static int MAX_OFFSET = 1000;
        
        /**
         * The widest radius that can be used in a Search.
         * Set to 40,000 meters, or 25 miles.
         */
        public final static int MAX_RADIUS_IN_METERS = 40_000;

        private String searchTerm;
        private String location;
        private Double latitude;
        private Double longitude;
        private Integer radiusInMeters;
        private String categories;
        private String locale;
        private Integer limit;
        private Integer offset;
        private String sortBy;
        private String prices;
        private Boolean isOpenNow;
        private Integer openAt;
        private String attributes;

        /**
         * Creates a new instance of the Builder.
         * @return 
         */
        public static Builder newInstance()
        {
            return new Builder();
        }

        /**
         * Creates a new instance of the Builder using the data in the existing {@link YelpSearchRequest}.
         * 
         * @param request Initiates the builder with the data in this variable. It cannot be null.
         * @return
         * 
         * @throws IllegalArgumentException If the argument is null
         */
        public static Builder from(@Required YelpSearchRequest request) throws IllegalArgumentException
        {
            checkThat(request).is(notNull());

            Builder builder = Builder.newInstance();
            builder.searchTerm = request.searchTerm;
            builder.locale = request.locale;
            builder.latitude = request.latitude;
            builder.longitude = request.longitude;
            builder.radiusInMeters = request.radius;
            builder.categories = request.categories;
            builder.location = request.location;
            builder.limit = request.limit;
            builder.offset = request.offset;
            builder.sortBy = request.sortBy;
            builder.prices = request.prices;
            builder.isOpenNow = request.openNow;
            builder.openAt = request.openAt;
            builder.attributes = request.attributes;

            return builder;
        }

        /**
         * Optional. Search term (e.g. "food", "restaurants"). If the term isn't included we search everything. The term keyword
         * also accepts business names such as "Starbucks".
         *
         * @param searchTerm The search term to use in the request.
         * @return
         * @throws IllegalArgumentException *
         */
        @Optional
        public Builder withSearchTerm(@NonEmpty String searchTerm) throws IllegalArgumentException
        {
            checkThat(searchTerm).is(nonEmptyString());

            this.searchTerm = searchTerm;
            return this;
        }
        
        /**
         * Required if either latitude or longitude is not provided. 
         * Specifies the combination of:
         * <pre>
         * + Address
         * + Neighborhood
         * + City
         * + State
         * + Zip
         * + Country
         * </pre>
         *
         * @param address
         * @return
         * @throws IllegalArgumentException 
         */
        @Required
        public Builder withLocation(@Required Address address) throws IllegalArgumentException
        {
            checkThat(address)
                .usingMessage("location cannot be null")
                .is(notNull());

            String text = address.address1;

            if (address.hasAddress2())
            {
                text += " " + address.address2;
            }

            if (address.hasAddress3())
            {
                text += " " + address.address3;
            }

            text += " " + address.city;
            text += " " + address.state;
            text += " " + address.country;

            if (address.hasZipCode())
            {
                text += " " + address.zipCode;
            }

            this.location = text;
            return this;
        }

        /**
         * Specifies the latitude and longitude of the location you want to search near by.
         * Required if {@linkplain #withLocation(tech.redroma.yelp.Address) Location} is not 
         * provided.
         * 
         * @param coordinate Cannot be empty, and must contain valid longitude and latitude;
         * @return
         * @throws IllegalArgumentException 
         */
        @Required
        public Builder withCoordinate(@Required Coordinate coordinate) throws IllegalArgumentException
        {
            checkThat(coordinate)
                .usingMessage("coordinate cannot be null")
                .is(notNull());

            double lat = coordinate.latitude;
            double lon = coordinate.longitude;

            checkThat(lat).is(validLatitude());
            checkThat(lon).is(validLongitude());

            this.latitude = lat;
            this.longitude = lon;
            return this;
        }

        /**
         * Search radius, in meters. If the value is too large, a {@link YelpAreaTooLargeException} is thrown.
         *
         * @param radius
         * @return
         * @throws IllegalArgumentException 
         */
        @Optional
        public Builder withRadiusInMeters(@Positive int radius) throws IllegalArgumentException, YelpAreaTooLargeException
        {
            checkThat(radius)
                .is(positiveInteger())
                .throwing(YelpAreaTooLargeException.class)
                .usingMessage("search radius cannot exceed: " + radius)
                .is(lessThanOrEqualTo(MAX_RADIUS_IN_METERS));

            this.radiusInMeters = radius;
            return this;
        }

        /**

         *
         * @param first The first category (required)
         * @param rest A Varargs of the rest.
         * @return
         * @throws IllegalArgumentException If the first is null.
         * 
         * @see Category
         * @see #withCategories(java.util.List) 
         */
        @Optional
        public Builder withCategories(@Required Category first, Category... rest) throws IllegalArgumentException
        {
            checkThat(first)
                .usingMessage("category is null")
                .is(notNull());

            List<Category> categoriesList = Lists.createFrom(first, rest);
            return withCategories(categoriesList);
        }

        /**
         * Categories to filter the search results with. 
         * See the {@linkplain Category list of supported categories}.
         * <p>
         * The category filter can be a list of
         * comma-delimited categories. For example, "bars, french", will filter Bars and French. The category identifier should be
         * used (e.g. "discgolf", instead of "Disc Golf").
         * 
         * @param categories
         * @return
         * @throws IllegalArgumentException 
         * @see #withCategories(tech.redroma.yelp.Category, tech.redroma.yelp.Category...) 
         */
        @Optional
        public Builder withCategories(@NonEmpty List<Category> categories) throws IllegalArgumentException
        {
            checkThat(categories)
                .is(notNull())
                .is(nonEmptyList());

            String joined = categories.stream()
                .map(c -> c.alias)
                .distinct()
                .collect(Collectors.joining(","));

            this.categories = joined;
            return this;
        }

        /**
         * Specify the locale to return the business information in.
         * <p>
         * To see the possible Locales, refer to {@link Locale.Locales}.
         *
         * @param locale The locale (cannot be empty).
         * @return
         * @throws IllegalArgumentException
         * @see Locale.Locales
         * @see
         * <a href="https://www.yelp.com/developers/documentation/v3/supported_locales">https://www.yelp.com/developers/documentation/v3/supported_locales</a>
         */
        @Optional
        public Builder withLocale(@Required Locale locale) throws IllegalArgumentException
        {
            checkThat(locale)
                .usingMessage("locale cannot be empty")
                .is(notNull());
            
            String code = locale.code();
            checkThat(code)
                .usingMessage("Country Code cannot be empty")
                .is(nonEmptyString())
                .usingMessage("Country must take the form: {language code}_{country code}")
                .is(stringWithLength(5));
            this.locale = locale.code();
            return this;
        }

        /**
         * Specify the maximum number of businesses to return. By default, it will return 20.
         * Cannot exceed {@link #MAX_LIMIT}.
         *
         * @param limit Must be {@code > 0} and cannot exceed {@link #MAX_LIMIT}.
         * @return
         * @throws IllegalArgumentException 
         */
        @Optional
        public Builder withLimit(@Positive int limit) throws IllegalArgumentException
        {
            checkThat(limit)
                .usingMessage("limit must be > 0")
                .is(positiveInteger())
                .usingMessage("limit cannot exceed 50")
                .is(lessThanOrEqualTo(MAX_LIMIT));

            this.limit = limit;
            return this;
        }

        /**
         * Offset the list of returned business by this amount.
         * <p>
         * For example, if you have seen results 1-10, setting this to '10'
         * will return the next 11-20.
         * 
         * @param offset Must be {@code > 0}
         * @return
         * @throws IllegalArgumentException 
         */
        @Optional
        public Builder withOffset(@Positive int offset) throws IllegalArgumentException
        {
            checkThat(offset)
                .usingMessage("offset must be > 0")
                .is(positiveInteger())
                .usingMessage("Per Yelp's API rules, the offset cannot exceed: " + MAX_OFFSET)
                .is(lessThanOrEqualTo(MAX_OFFSET));
                

            this.offset = offset;
            return this;
        }
        
        /**
         * Sort the results by one of these modes: {@link SortType}.
         *
         * @param sortType The type of sorting order desired. Cannot be null.
         * @return
         * @throws IllegalArgumentException 
         */
        @Optional
        public Builder withSortBy(@Required SortType sortType) throws IllegalArgumentException
        {
            checkThat(sortType).is(notNull());
            
            this.sortBy = sortType.text;
            return this;
        }
        
        /**
         * Pricing levels to filter the search result with.
         *
         * @param first The first pricing level, cannot be null.
         * @param others Varargs allowing you to specify other pricing levels.
         * @return
         * @throws IllegalArgumentException 
         * @see #withPrices(java.util.List) 
         */
        @Optional
        public Builder withPrices(@Required PricingLevel first, PricingLevel...others) throws IllegalArgumentException
        {
            checkThat(first).is(notNull());
            
            List<PricingLevel> pricingLevels = Lists.createFrom(first, others);
            return withPrices(pricingLevels);
        }
        
        /**
         * Filters results to include businesses at the specified pricing points.
         *
         * @param pricingLevels The pricing levels to the filter the search results with (cannot be empty).
         * @return
         * @throws IllegalArgumentException 
         * @see #withPrices(tech.redroma.yelp.YelpSearchRequest.PricingLevel, tech.redroma.yelp.YelpSearchRequest.PricingLevel...) 
         */
        @Optional
        public Builder withPrices(@NonEmpty List<PricingLevel> pricingLevels) throws IllegalArgumentException
        {
            checkThat(pricingLevels)
                .is(notNull())
                .is(nonEmptyList());
            
            String priceLevels = pricingLevels.stream()
                .map(p -> p.number)
                .map(String::valueOf)
                .distinct()
                .collect(Collectors.joining(", "));
            
            this.prices = priceLevels;
            return this;
        }
        
        /**
         * Returns results that only includes businesses that are open now.
         * <p>
         * Notice that {@link #withBusinessesOpenAt(int) } and {@link #lookingForOpenNow() } cannot be used together.
         *
         * @return
         */
        @Optional
        public Builder lookingForOpenNow()
        {
            this.isOpenNow = true;
            return this;
        }
        
        /**
         * Returns results taht only include businesses that are open at the specified time.
         * <p>
         * Notice that {@link #withBusinessesOpenAt(int) } and {@link #lookingForOpenNow() } cannot be used together.
         *
         * @param hour The Unix timestamp in the same time-zone of the search location.
         * @return
         * @throws IllegalArgumentException 
         */
        @Optional
        public Builder withBusinessesOpenAt(int hour) throws IllegalArgumentException
        {
            checkThat(hour)
                .is(greaterThanOrEqualTo(0))
                .is(lessThanOrEqualTo(24));
            
            this.openAt = hour;
            return this;
        }
        
        /**
         * Convenience method for {@link #withAttributes(java.util.List) }.
         * 
         * @param first The first Attribute is required.
         * @param rest The rest are optional
         * @return
         * @throws IllegalArgumentException 
         */
        public Builder withAttribute(@Required Attribute first, Attribute...rest) throws IllegalArgumentException
        {
            checkThat(first).is(notNull());
            
            List<Attribute> attributes = Lists.createFrom(first, rest);
            return withAttributes(attributes);
        }
        
        /**
         * Additional filters to search businesses. 
         * <p>
         * See {@link Attribute} for possible values.
         * 
         * @param attributes The attributes to filter search results with. Cannot be empty.
         * @throws IllegalArgumentException
         * @return 
         * @see #withAttribute(tech.redroma.yelp.YelpSearchRequest.Attribute, tech.redroma.yelp.YelpSearchRequest.Attribute...) 
         */
        public Builder withAttributes(@NonEmpty List<Attribute> attributes) throws IllegalArgumentException
        {
            checkThat(attributes).is(nonEmptyList());
            
            this.attributes = attributes.stream()
                .map(a -> a.text)
                .distinct()
                .collect(joining(","));
            
            return this;
        }
        
        /**
         * Builds the final {@link YelpSearchRequest} from the parameters stored so far. Note that a Search Request requires at
         * least a {@linkplain #withLocation(tech.redroma.yelp.Address) Location} or a
         * {@linkplain #withCoordinate(tech.redroma.yelp.Coordinate) Coordinate}.
         *
         * @return
         * @throws YelpBadArgumentException
         */
        public YelpSearchRequest build() throws YelpBadArgumentException
        {
            checkThatLocationIsSet();
            checkThatOnlyOneOfOpenNowOrOpenAtIsSet();

            return new YelpSearchRequest(searchTerm,
                                       location,
                                       latitude,
                                       longitude,
                                       radiusInMeters,
                                       categories,
                                       locale,
                                       limit,
                                       offset,
                                       sortBy,
                                       prices,
                                       isOpenNow,
                                       openAt,
                                       attributes);
        }

        private void checkThatLocationIsSet()
        {
            if (location != null)
            {
                return;
            }

            if (longitude != null && latitude != null)
            {
                return;
            }

            throw new YelpBadArgumentException("Either a location or a coordinate must be set.");
        }

        private void checkThatOnlyOneOfOpenNowOrOpenAtIsSet()
        {
            if (openAt != null && isOpenNow != null)
            {
                throw new IllegalArgumentException("You can use 'open_now' or 'open_at', but not both.");
            }
        }
    }

}
