package com.example;

import org.apache.beam.sdk.options.PipelineOptions;

public interface Options extends PipelineOptions {

    String getUserName();
    void setUserName(String userName);

    String getPassword();
    void setPassword(String password);

    String getJdbcDriverName();
    void setJdbcDriverName(String jdbcDriverName);

    String getUrl();
    void setUrl(String url);
    
    String getbucketName();
    void setbucketName(String bucketName);
    
    String getSqlQuery();
    void setSqlQuery(String sqlQuery);
}
