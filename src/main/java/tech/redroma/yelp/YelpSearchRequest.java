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

/**
 *
 * @author SirWellington
 */
@Pojo
@Immutable
@ThreadSafe
@BuilderPattern(role = PRODUCT)
public final class YelpSearchRequest
{

    /*
     * Optional search term (e.g. "food", "restaurants"). If the term isn't included we search everything. The term keyword also
     * accepts business names such as "Starbucks".
     */
    @Optional
    private final String searchTerm;

    /**
     * Required if either latitude or longitude is not provided. Specifies the combination of:
     * <pre>
     * + Address
     * + Neighborhood
     * + City
     * + State
     * + Zip
     * + Country
     * </pre>
     */
    private final String location;

    /**
     * The latitude of the location you want to search near by. Required if location is not provided.
     */
    private final Double latitude;

    /**
     * Longitude of the location you want to search near by. required if the location is not provided.
     */
    private final Double longitude;

    /**
     * Search radius, in meters. If the value is too large, a {@link YelpAreaTooLargeException} is thrown.
     */
    @Optional
    private final Integer radius;

    /**
     * categories to filter the serach results with. See the list of supported categories. The category filter can be a ist of
     * comma-delimited categories. For example, "bars, french", will filter Bars and French. The category identifier should be
     * used (e.g. "discgolf", instead of "Disc Golf").
     */
    @Optional
    private final String categories;

    /**
     * Specify the locale to return the business information in.
     *
     * @see
     * <a href="https://www.yelp.com/developers/documentation/v3/supported_locales">https://www.yelp.com/developers/documentation/v3/supported_locales</a>
     */
    @Optional
    private final String locale;

    /**
     * Specify the maximum number of businesses to return. By default, it will return 20. The maximum is 50.
     */
    @Optional
    private final Integer limit;

    /**
     * Offset the list of returned businesses by the amount. For, example, if you have seen results 1-10, specify '10' to see the
     * next 11-20.
     */
    @Optional
    private final Integer offset;

    /**
     * Sort the results by one of these modes: {@link SortType}.
     *
     */
    @Optional
    private final String sortBy;

    /**
     * Pricing levels to filter the search result with.
     */
    @Optional
    private final String prices;

    /**
     * Defaults to false When set tot rue, only return the businesses open now. Notice that {@link #openNow} and {@link #openAt}
     * cannot be used together.
     */
    @Optional
    private final Boolean openNow;

    /**
     * An integer representing the Unix timestamp in the same time-zone of the search location.
     *
     * If specified, it will return businesses that are open at the given time. Note that {@link #openNow} and {@link #openAt}
     * cannot be used together.
     */
    @Optional
    private final Integer openAt;

    /**
     * Additional filters to search businesses. you can use multiple attribute filters at the same time by providing a
     * comma-separated string. For example: {@code "attribute1,attribute2"}.
     * <p>
     * Currently the valid values are: {@code "hot_and_new" & "deals"}.
     */
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

        private final static int MAX_LIMIT = 50;

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

        public static Builder newInstance()
        {
            return new Builder();
        }

        public static Builder from(@Required YelpSearchRequest query) throws IllegalArgumentException
        {
            checkThat(query).is(notNull());

            Builder builder = Builder.newInstance();
            builder.searchTerm = query.searchTerm;
            builder.locale = query.locale;
            builder.latitude = query.latitude;
            builder.longitude = query.longitude;
            builder.radiusInMeters = query.radius;
            builder.categories = query.categories;
            builder.location = query.location;
            builder.limit = query.limit;
            builder.offset = query.offset;
            builder.sortBy = query.sortBy;
            builder.prices = query.prices;
            builder.isOpenNow = query.openNow;
            builder.openAt = query.openAt;
            builder.attributes = query.attributes;

            return builder;
        }

        public Builder withSearchTerm(@NonEmpty String searchTerm) throws IllegalArgumentException
        {
            checkThat(searchTerm).is(nonEmptyString());

            this.searchTerm = searchTerm;
            return this;
        }

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

        public Builder withRadiusInMeters(@Positive int radius) throws IllegalArgumentException
        {
            checkThat(radius)
                .is(positiveInteger());

            this.radiusInMeters = radius;
            return this;
        }

        public Builder withCategories(@Required Category first, Category... rest) throws IllegalArgumentException
        {
            checkThat(first)
                .usingMessage("category is null")
                .is(notNull());

            List<Category> categoriesList = Lists.createFrom(first, rest);
            return withCategories(categoriesList);
        }

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

        public Builder withLocale(@NonEmpty String locale) throws IllegalArgumentException
        {
            checkThat(locale)
                .usingMessage("locale cannot be empty")
                .is(nonEmptyString());

            this.locale = locale;
            return this;
        }

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

        public Builder withOffset(@Positive int offset) throws IllegalArgumentException
        {
            checkThat(offset)
                .usingMessage("offset must be > 0")
                .is(positiveInteger());

            this.offset = offset;
            return this;
        }
        
        public Builder withSortBy(@Required SortType sortType) throws IllegalArgumentException
        {
            checkThat(sortType).is(notNull());
            
            this.sortBy = sortType.text;
            return this;
        }
        
        public Builder withPrices(@Required PricingLevel first, PricingLevel...others) throws IllegalArgumentException
        {
            checkThat(first).is(notNull());
            
            List<PricingLevel> pricingLevels = Lists.createFrom(first, others);
            return withPrices(pricingLevels);
        }
        
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
        
        public Builder lookingForOpenNow()
        {
            this.isOpenNow = true;
            return this;
        }
        
        public Builder withBusinessesOpenAt(int hour) throws IllegalArgumentException
        {
            checkThat(hour)
                .is(greaterThanOrEqualTo(0))
                .is(lessThanOrEqualTo(24));
            
            this.openAt = hour;
            return this;
        }
        
        public Builder withAttribute(@Required Attribute attribute) throws IllegalArgumentException
        {
            checkThat(attribute).is(notNull());
            
            this.attributes = attribute.text;
            return this;
        }
        
        public Builder withAttributes(@NonEmpty List<Attribute> attributes)
        {
            checkThat(attributes).is(nonEmptyList());
            
            this.attributes = attributes.stream()
                .map(a -> a.text)
                .distinct()
                .collect(joining(","));
            
            return this;
        }
        

        public YelpSearchRequest build() throws YelpBadArgumentException
        {
            checkThatLocationIsSet();

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

            throw new YelpBadArgumentException("Either location ");
        }
    }

}
