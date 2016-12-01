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

import java.util.Objects;
import tech.redroma.yelp.exceptions.YelpAreaTooLargeException;
import tech.sirwellington.alchemy.annotations.arguments.Optional;
import tech.sirwellington.alchemy.annotations.concurrency.Immutable;
import tech.sirwellington.alchemy.annotations.concurrency.ThreadSafe;
import tech.sirwellington.alchemy.annotations.designs.patterns.BuilderPattern;
import tech.sirwellington.alchemy.annotations.objects.Pojo;

import static tech.sirwellington.alchemy.annotations.designs.patterns.BuilderPattern.Role.PRODUCT;

/**
 *
 * @author SirWellington
 */
@Pojo
@Immutable
@ThreadSafe
@BuilderPattern(role = PRODUCT)
public final class YelpSearchQuery
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
    private final int radius;

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
    private final int limit;

    /**
     * Offset the list of returned businesses by the amount. For, example, if you have seen results 1-10, specify '10' to see the
     * next 11-20.
     */
    @Optional
    private final int offset;

    /**
     * Sort the results by one of these modes:
     *
     */
    @Optional
    private final SortType sortBy;

    /**
     * Pricing levels to filter the search result with.
     */
    @Optional
    private final String price;

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
    private final Boolean openAt;

    YelpSearchQuery(String searchTerm,
                    String location,
                    Double latitude,
                    Double longitude,
                    int radius,
                    String categories,
                    String locale,
                    int limit,
                    int offset,
                    SortType sortBy,
                    String price,
                    Boolean openNow,
                    Boolean openAt)
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
        this.price = price;
        this.openNow = openNow;
        this.openAt = openAt;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.searchTerm);
        hash = 79 * hash + Objects.hashCode(this.location);
        hash = 79 * hash + Objects.hashCode(this.latitude);
        hash = 79 * hash + Objects.hashCode(this.longitude);
        hash = 79 * hash + this.radius;
        hash = 79 * hash + Objects.hashCode(this.categories);
        hash = 79 * hash + Objects.hashCode(this.locale);
        hash = 79 * hash + this.limit;
        hash = 79 * hash + this.offset;
        hash = 79 * hash + Objects.hashCode(this.sortBy);
        hash = 79 * hash + Objects.hashCode(this.price);
        hash = 79 * hash + Objects.hashCode(this.openNow);
        hash = 79 * hash + Objects.hashCode(this.openAt);
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
        final YelpSearchQuery other = (YelpSearchQuery) obj;
        if (this.radius != other.radius)
        {
            return false;
        }
        if (this.limit != other.limit)
        {
            return false;
        }
        if (this.offset != other.offset)
        {
            return false;
        }
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
        if (!Objects.equals(this.price, other.price))
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
        if (this.sortBy != other.sortBy)
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
        return "YelpSearchQuery{" + "searchTerm=" + searchTerm + ", location=" + location + ", latitude=" + latitude + ", longitude=" + longitude + ", radius=" + radius + ", categories=" + categories + ", locale=" + locale + ", limit=" + limit + ", offset=" + offset + ", sortBy=" + sortBy + ", price=" + price + ", openNow=" + openNow + ", openAt=" + openAt + '}';
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

    public static class Builder
    {

        private String term;
        private String location;
        private Coordinate coordinate;
        private double radiusInMeters;
        private String categories;
        private String locale;
        private int offset;
        private String sortBy;
        private String price;
        private boolean isOpenNow;
        private int openAt;
        private String attributes;

    }

}
