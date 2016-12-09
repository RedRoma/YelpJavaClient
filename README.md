YelpJavaClient
===================

[<img src="http://brand.redroma.tech/Logos/RedRoma-Logo%402x.png" width="300">](http://RedRoma.tech)

[![Jenkins](http://jenkins.redroma.tech/job/YelpAPI/badge/icon)](http://jenkins.redroma.tech/job/YelpAPI/) [![Travis](https://travis-ci.org/RedRoma/YelpJavaClient.svg?branch=develop)](https://travis-ci.org/RedRoma/YelpJavaClient)
![Maven Central Version](http://img.shields.io/maven-central/v/tech.redroma.yelp/yelp-api.svg)

---

The **YelpJavaClient** provides a simple Java interface for interacting with Yelp's API.

## Download

### Maven

```xml
<dependency>
	<groupId>tech.redroma.yelp</groupId>
	<artifactId>yelp-api</artifactId>
    <version>1.0</version>
</dependency>
```

## Creating a Client

Create a client using the App ID and App Secret that you obtained from the [Yelp Developer console](https://www.yelp.com/developers/v3/manage_app).

```java
String clientId = "...";
String clientSecret = "...";
YelpAPI yelp = YelpAPI.newInstance(clientId, clientSecret);
```

## Searching Businesses

#### [Yelp API Documentation](https://www.yelp.com/developers/documentation/v3/business_search)


Searching businesses is as easy as making a request object and using the `searchForBusinesses()` method.

```java
//Create a request object
YelpSearchRequest request =  YelpSearchRequest.newBuilder()
    .withSearchTerm("Deli")
    .withCoordinate(Coordinate.of(34.018363, -118.492343))
    .withLimit(10)
    .withSortBy(YelpSearchRequest.SortType.DISTANCE)
    .build();

//Make the request
List<YelpBusiness> results = yelp.searchForBusinesses(request);

LOG.info("Found {} results for request {}", results.size(), request);
```


## Business Details

#### [Yelp API Documentation](https://www.yelp.com/developers/documentation/v3/business)


Sometimes you want more detailed information about a business, such as the business hours, additional photos, and price information.   

Simply call the `getBusinessDetails()` method.

```java
//Using any business
YelpBusiness business = Lists.oneOf(results);

//Make the request to get business details.
YelpBusinessDetails businessDetails = yelp.getBusinessDetails(business);

LOG.info("Received detailed info for business named {}: [{}]", business.name, businessDetails);

if (businessDetails.isOpenNow())
{
    LOG.info("{} is open now.", businessDetails.name)
}
```

## Reviews

#### [Yelp API Documentation](https://www.yelp.com/developers/documentation/v3/business_reviews)


```java
YelpBusiness business = Lists.oneof(business);
List<YelpReview> reviews = yelp.getReviewsForBusiness(business);

LOG.info("Business named")
```

## [Javadocs](http://www.javadoc.io/doc/tech.redroma.yelp/yelp-api/)

## Currently Unsupported

We do not yet support the following API calls:

+ [Phone Search](https://www.yelp.com/developers/documentation/v3/business_search_phone)
+ [Transaction Search](https://www.yelp.com/developers/documentation/v3/transactions_search)
+ [Autocomplete](https://www.yelp.com/developers/documentation/v3/autocomplete)

## Guiding Philosophy

We used [**Alchemy Design Principles**](https://github.com/SirWellington/alchemy) when designing this library.

[<img src="https://raw.githubusercontent.com/SirWellington/alchemy/develop/Graphics/Logo/Alchemy-Logo-v7-name.png" width="200">](https://github.com/SirWellington/alchemy)

### Swift
We wanted our code to feel like it was barely there. This meant keeping things minimal and light.

### Intuitive
Yelp already designed a great intuitive API. We didn't want to add a pool of unnecessary soda.

### Solid
Nearly everything is unit tested, and it is already being used in production by [BlackNectar](http://docs.blacknectarapi.apiary.io/), and others.

### Invigorating
We wanted you to have fun, and to feel powerful.   
We ditched the no-fun java `get() set()` pojo style in favor of open `public`
 variables. We trust you.

# License

This Software is licensed under the Apache 2.0 License

http://www.apache.org/licenses/LICENSE-2.0
