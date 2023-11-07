package com.example;

import java.sql.ResultSetMetaData;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.io.gcp.bigquery.TableRowJsonCoder;
import com.google.api.services.bigquery.model.TableRow;

public class JdbcToGcs {

public static void main(String[] args) {
// Create a pipeline
Options options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
Pipeline pipeline = Pipeline.create(options);

JdbcIO.DataSourceConfiguration dataSourceConfiguration = JdbcIO.DataSourceConfiguration.create(options.getJdbcDriverName(),options.getUrl())
.withUsername(options.getUserName())
.withPassword(options.getPassword());

// Read data from JDBC
PCollection<TableRow> jdbcData = pipeline
.apply(JdbcIO.<TableRow>read()
.withDataSourceConfiguration(dataSourceConfiguration)
.withQuery(options.getSqlQuery())
.withCoder(TableRowJsonCoder.of())
.withRowMapper(
                    (JdbcIO.RowMapper<TableRow>)
                        resultSet -> {
                          ResultSetMetaData metaData = resultSet.getMetaData();
                          TableRow outputTableRow = new TableRow();
                          for (int i = 1; i <= metaData.getColumnCount(); i++) {
                            
                                outputTableRow.set(
                                    metaData.getColumnName(i), resultSet.getString(i).trim());
                              
                          }
                          return outputTableRow;
                        }));


// Run the pipeline
pipeline.run();
}
}