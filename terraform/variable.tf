variable "region" {}

# Job variables
variable "name_job" {}
variable "max_capacity_DPU" {}
variable "timeout_in_minute" {}
variable "scala_bucket_path" {
  description =  <<EOF
    We have to know the path of the scala file to have the right in your role to get the code from the bucket.
    The value should be the bucket with scala key. Example : my_bucket/to/my/code.scala
  EOF
}
variable "my_temporary_directory" {
  description = "The bucket and directory for output of datasource. Example : my_temp_bucket/to/the/output"
}
variable "class_name_scala" {
  description = "The scala name class. Example : MyGlueApp"
}

# Crawler for S3 variables
variable "crawler_name" {}
variable "csv_key_crawler" {
  description = "The key path to csv file to crawl. Example bucket_name/to/my_file.csv"
}

# Catalogue database
variable "catalogue_name" {}

