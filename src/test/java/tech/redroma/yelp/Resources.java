
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sirwellington.alchemy.annotations.access.Internal;

import static com.google.common.base.Charsets.UTF_8;

/**
 *
 * @author SirWellington
 */
@Internal
public class Resources
{

    private final static Logger LOG = LoggerFactory.getLogger(Resources.class);

    static final Gson GSON = new GsonBuilder()
        .create();

    public static String loadResource(String name) throws IOException
    {
        URL url;
        try
        {
            url = com.google.common.io.Resources.getResource(Resources.class, name);
        }
        catch (IllegalArgumentException ex)
        {
            LOG.error("Failed to {} load resource called [{]]", name, ex);
            throw ex;
        }

        return com.google.common.io.Resources.toString(url, UTF_8);

    }
}
