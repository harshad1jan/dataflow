package com.example;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;

public class JdbcToGcs {

public static void main(String[] args) {
// Create a pipeline
Options options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
Pipeline pipeline = Pipeline.create(options);

JdbcIO.DataSourceConfiguration dataSourceConfiguration = JdbcIO.DataSourceConfiguration.create(options.getJdbcDriverName(),options.getUrl())
.withUsername(options.getUserName())
.withPassword(options.getPassword());

// Read data from JDBC
PCollection<String> jdbcData = pipeline
.apply(JdbcIO.<String>read()
.withDataSourceConfiguration(dataSourceConfiguration)
.withQuery(options.getSqlQuery())
.withCoder(StringUtf8Coder.of()));

// Write data to GCS in text format
jdbcData.apply(TextIO.write()
.to(options.getbucketName())
.withNumShards(1));

// Run the pipeline
pipeline.run().waitUntilFinish();
}
}