/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.test.sql.parser.internal.asserts.statement.ral.impl.migration;

import org.apache.shardingsphere.test.sql.parser.internal.asserts.SQLCaseAssertContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Job ID assert.
 */
public final class JobIdAssert {
    
    /**
     * Assert job ID.
     * 
     * @param assertContext assert context
     * @param actual actual job ID
     * @param expected expected job ID
     */
    public static void assertJobId(final SQLCaseAssertContext assertContext, final String actual, final String expected) {
        if (null == expected) {
            assertNull(assertContext.getText("Actual job ID should not exist."), actual);
        } else {
            assertNotNull(assertContext.getText("Actual job ID should exist."), actual);
            assertThat(assertContext.getText("Job ID assertion error."), actual, is(expected));
        }
    }
}
