/*
 * Copyright 2012 Hadoop Vietnam <admin@hadoopvietnam.com>.
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
package vn.mangtuyendung.common;

/*
 * JobCacheKeys.java
 * 
 * Version 1.0
 *
 * Date 01/01/2013
 * 
 * Cache keys common
 * 
 * Modification Logs:
 *  DATE            AUTHOR      DESCRIPTION
 *  --------------------------------------------------------
 *  01-Jan-2013     tuanta      Create first time
 */
public final class JobCacheKeys {

    /**
     * Hidden constructor.
     */
    private JobCacheKeys() {
        // no-op
    }
    /**
     * All locations cache key.
     */
    public static final String LOCATION_ALL = "Location:findAll";
    /**
     * Location cache key.
     */
    public static final String LOCATION_ID = "Location:";
    /**
     * All categories cache key.
     */
    public static final String CATEGORY_ALL = "Category:findAll";
    /**
     * Category cache key.
     */
    public static final String CATEGORY_ID = "Category:";
}
