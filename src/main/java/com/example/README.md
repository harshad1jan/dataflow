  Command to create a flex template
  gcloud dataflow flex-template build gs://<bucket-name>/dataflow/templates/streaming-beam-sql.json --image-gcr-path "image-gcr-path" --sdk-language "JAVA"  --flex-template-base-image JAVA11  --jar "target/df-template-1.0.jar" --env FLEX_TEMPLATE_JAVA_MAIN_CLASS="com.example.JdbcToGcs"



  gcloud dataflow flex-template run "streaming-beam-sql-`date +%Y%m%d-%H%M%S`" --template-file-gcs-location "gs://<bucket-name>/dataflow/templates/streaming-beam-sql.json" --parameters userName="userName" --parameters password="password" --parameters jdbcDriverName="jdbcDriverName" --parameters bucketName="bucketName" --parameters sqlQuery="sqlQuery" --parameters url="url"  --region "us-east1" --subnetwork "projects/<project-id>/regions/us-east1/subnetworks/<subnet>"This is the repo associated with the Medium article.