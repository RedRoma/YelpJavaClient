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

package tech.redroma.yelp.exceptions;

/**
 * Thrown when an Authentication error occurs communicating with the Yelp API. 
 * <p>
 * This can happen because:
 * <pre>
 * + The client_id or client_secret were invalid
 * + An OAuth token could not be obtained from Yelp
 * + The OAuth token being used has expired
 * </pre>
 *
 * @author SirWellington
 */
public class YelpAuthenticationException extends YelpException
{

    public YelpAuthenticationException()
    {
    }

    public YelpAuthenticationException(String message)
    {
        super(message);
    }

    public YelpAuthenticationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public YelpAuthenticationException(Throwable cause)
    {
        super(cause);
    }

}
