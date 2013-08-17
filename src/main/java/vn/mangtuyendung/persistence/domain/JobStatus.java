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
package vn.mangtuyendung.persistence.domain;

/**
 * Trang thai tin dang tuyen.
 *
 * @author Hadoop Vietnam <admin@hadoopvietnam.com>
 */
public class JobStatus {

    /**
     * Tin cho duyet.
     */
    public static final byte PEDING = 0;
    /**
     * Tin can xuat ban.
     */
    public static final byte PUBLICED = 2;
    /**
     * Tin da duyet.
     */
    public static final byte VERRYFIED = 3;
    /**
     * Tin tu choi duyet.
     */
    public static final byte DENIED = 3;
    /**
     * Tin het han dang.
     */
    public static final byte EXPIRED = 4; //expires
}
