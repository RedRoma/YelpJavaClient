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
import tech.sirwellington.alchemy.annotations.arguments.NonEmpty;

import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.nonEmptyString;

/**
 * A Local consists of a code, country, and language.
 * <p>
 * See {@link Locales} for locales supported by Yelp.
 * 
 * @author SirWellington
 * @see Locales
 */
public interface Locale
{
    /**
     * For example, {@code en_CA}, for English/Canada.
     * @return The 5 digit code consisting of {@code language-code_country-code }. The underscore in the middle is included.
     */
    public String code();
    
    /**
     * For example, Italy.
     * @return The display name of the country.
     */
    public String country();
    
    /**
     * For example, Italian.
     * @return The display name of the language.
     */
    public String language();
    
    /**
     * All of the Locales supported by Yelp.
     */
    public enum Locales implements Locale
    {
        CZECH_REPUBLIC("cs_CZ", "Czech Republic", "Czech"),
        DENMARK("da_DK", "Denmark", "Danish"),
        
        //German
        AUSTRIA("de_AT", "Austria", "German"),
        SWITZERLAND_GERMAN("de_CH", "Switzerland", "German"),
        GERMANY("de_DE", "Germany", "German"),
        
        //English
        AUSTRALIA("en_AU", "Australia", "English"),
        BELGIUM_ENGLISH("en_BE", "Belgium", "English"),
        CANADA_ENGLISH("en_CA", "Canada", "English"),
        SWITZERLAND_ENGLISH("en_CH", "Switzerland", "English"),
        UNITED_KINGDOM("en_GB", "United Kingdom", "English"),
        HONG_KONG_ENGLISH("en_HK", "Hong Kong", "English"),
        IRELAND("en_IR", "Republic of Ireland", "English"),
        MALAYSIA_ENGLISH("en_MY", "Malaysia", "English"),
        NEW_ZEALAND("en_NZ", "New Zealand", "English"),
        PHILIPPINES_ENGLISH("en_PH", "Philippines", "English"),
        SINGAPORE("en_SG", "Singapore", "English"),
        UNITED_STATES("en_US", "United St ates", "English"),
        
        //Spanish
        ARGENTINA("es_AR", "Argentina", "Spanish"),
        CHILE("es_CL", "Chile", "Spanish"),
        SPAIN("es_ES", "Spain", "Spanish"),
        MEXICO("es_MX", "Mexico", "Spanish"),
        FINLAND_FINNISH("fi_FI", "Finland", "Finnish"),
        PHILIPPINES_FILIPINO("fil_PH", "Phillippines", "Filipino"),
        
        //French
        BELGIUM_FRENCH("fr_BE", "Belgium", "French"),
        CANADA_FRENCH("fr_CA", "Canada", "French"),
        SWITZERLAND_FRENCH("fr_CH", "Switzerland", "French"),
        FRANCE("fr_FR", "France", "French"),
        
        //Italian
        SWITZERLAND_ITALIAN("it_CH", "Switzerland", "Italian"),
        ITALY("it_IT", "Italy", "Italian"),
        
        JAPAN("ja_JP", "Japan", "Japanese"),
        
        MALAYSIA_MALAY("ms_MY", "Malaysia", "Malay"),
        
        NORWAY("nb_NO", "Norway", "Norwegian"),
        
        //Dutch
        BELGIUM_DUTCH("nl_BE", "Belgium", "Dutch"),
        NETHERLANDS("nl_NL", "The Netherlands", "Dutch"),
        
        POLAND("pl_PL", "Poland", "Polish"),
        
        //Portugese
        BRAZIL("pt_BR", "Brazil", "Portugese"),
        PORTUGAL("pt_PT", "Portugal", "Portguese"),
        
        //Swedish
        FINLAND_SWEDISH("sv_FI", "Finland", "Swedish"),
        SWEDEN_SWEDISH("sv_SE", "Sweden", "Swedish"),
        
        TURKEY("tr_TR", "Turkey", "Turkish"),
        
        //Chinese
        HONG_KONG_CHINESE("zh_HK", "Hong Kong", "Chinese"),
        TAIWAIN("zh_TW", "Taiwan", "Chinese"),
        ;
        
        private final String code;

        private final String country;
        private final String language;

        private Locales(String code, String country, String language)
        {
            checkThat(code, country, language)
                .are(nonEmptyString());

            this.code = code;
            this.country = country;
            this.language = language;
        }

        @Override
        public String code()
        {
            return code;
        }

        @Override
        public String country()
        {
            return country;
        }

        @Override
        public String language()
        {
            return language;
        }

    }

    /**
     * Creates a Custom Locale from the specified information.
     * 
     * @param code The country code follows ISO 3166-1 alpha-2 code, for example "en_CA".
     * @param country The display name of the country, for example "Canada".
     * @param language The Language, for example "English".
     * @return 
     */
    static Locale of(@NonEmpty String code, @NonEmpty String country, @NonEmpty String language) throws IllegalArgumentException
    {
        checkThat(code, country, language)
            .are(nonEmptyString());

        /**
         * Hidden internal class used for custom Locales.
         */
        class CustomLocale implements Locale
        {

            private final String code;
            private final String country;
            private final String language;

            CustomLocale(String code, String country, String language)
            {
                this.code = code;
                this.country = country;
                this.language = language;
            }

            @Override
            public String code()
            {
                return code;
            }

            @Override
            public String country()
            {
                return country;
            }

            @Override
            public String language()
            {
                return language;
            }

            @Override
            public int hashCode()
            {
                int hash = 5;
                hash = 53 * hash + Objects.hashCode(this.code);
                hash = 53 * hash + Objects.hashCode(this.country);
                hash = 53 * hash + Objects.hashCode(this.language);
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
                final CustomLocale other = (CustomLocale) obj;
                if (!Objects.equals(this.code, other.code))
                {
                    return false;
                }
                if (!Objects.equals(this.country, other.country))
                {
                    return false;
                }
                if (!Objects.equals(this.language, other.language))
                {
                    return false;
                }
                return true;
            }

        }

        return new CustomLocale(code, country, language);
    }


}
