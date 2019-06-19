resource "aws_glue_crawler" "example" {
  database_name = "${aws_glue_catalog_database.aws_glue_catalog_database.name}"
  name          = "${var.crawler_name}"
  role          = "${aws_iam_role.glue_role.name}"

  s3_target {
    path = "s3://${var.csv_key_crawler}"
  }
}
