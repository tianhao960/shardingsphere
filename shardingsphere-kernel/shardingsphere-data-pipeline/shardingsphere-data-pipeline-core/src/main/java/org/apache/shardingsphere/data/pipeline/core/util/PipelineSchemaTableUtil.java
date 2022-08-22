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

package org.apache.shardingsphere.data.pipeline.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.data.pipeline.api.datasource.PipelineDataSourceWrapper;
import org.apache.shardingsphere.data.pipeline.api.datasource.config.PipelineDataSourceConfiguration;
import org.apache.shardingsphere.data.pipeline.core.datasource.PipelineDataSourceFactory;
import org.apache.shardingsphere.data.pipeline.core.exception.AddMigrationSourceResourceException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pipeline schema table util.
 */
@Slf4j
public final class PipelineSchemaTableUtil {
    
    private PipelineSchemaTableUtil() {
    }
    
    /**
     * Get schema tables map from actual data source.
     *
     * @param pipelineDataSourceConfig pipeline data source config
     * @param tableName table name
     * @return schema tables map
     */
    public static Map<String, List<String>> getSchemaTablesMapFromActual(final PipelineDataSourceConfiguration pipelineDataSourceConfig, final String tableName) {
        Map<String, List<String>> result = new HashMap<>();
        try (PipelineDataSourceWrapper dataSource = PipelineDataSourceFactory.newInstance(pipelineDataSourceConfig)) {
            try (Connection connection = dataSource.getConnection()) {
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet resultSet = metaData.getTables(null, null, tableName, new String[]{"TABLE"});
                while (resultSet.next()) {
                    String schemaName = resultSet.getString("TABLE_SCHEM");
                    result.computeIfAbsent(schemaName, k -> new ArrayList<>()).add(resultSet.getString("TABLE_NAME"));
                }
            }
        } catch (final SQLException ex) {
            log.error("Get schema name map error", ex);
            throw new AddMigrationSourceResourceException(ex.getMessage());
        }
        return result;
    }
}
