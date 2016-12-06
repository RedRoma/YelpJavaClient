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

import tech.sirwellington.alchemy.annotations.arguments.NonEmpty;

import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.nonEmptyString;

/**
 *
 * @author SirWellington
 */
public enum Price
{
    $(1), $$(2), $$$(3), $$$$(4);
    final int number;

    private Price(int number)
    {
        this.number = number;
    }
    
    public String asString()
    {
        return this.toString();
    }
    
    /**
     * Creates a {@link Price} from the given number, which must be in the range (1...4).
     * <pre>
     *  1 = $
     *  2 = $$
     *  3 = $$$
     *  4 = $$$$
     * </pre>
     * @param number Must be in the range 
     * @return
     * @throws IllegalArgumentException If the number could not be matched to a {@link Price}.
     */
    public static Price fromNumber(int number) throws IllegalArgumentException
    {
        
        for (Price price : Price.values())
        {
            if (price.number == number)
            {
                return price;
            }
        }
        
        throw new IllegalArgumentException("Cannot determine price from number: " + number);
    }
    
    /**
     * Creates a price from the give string representation.
     * <p>
     * Valid values include:
     * <pre>
     * + "$"
     * + "$$"
     * + "$$$"
     * + "$$$$"
     * </pre>
     * 
     * @param price
     * @return
     * @throws IllegalArgumentException 
     */
    public static Price fromString(@NonEmpty String price) throws IllegalArgumentException
    {
        checkThat(price).is(nonEmptyString());
        
        for (Price p : Price.values())
        {
            if (price.equals(p.asString()))
            {
                return p;
            }
        }
        
        throw new IllegalArgumentException("Cannot determine price from: " + price);
    }
}
