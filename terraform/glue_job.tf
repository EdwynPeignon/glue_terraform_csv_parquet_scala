resource "aws_glue_job" "example" {
  name     = "${var.name_job}"
  role_arn = "${aws_iam_role.glue_role.arn}"
  max_capacity = "${var.max_capacity_DPU}"
  timeout = "${var.timeout_in_minute}"


  command {
    script_location = "s3://${var.scala_bucket_path}"
  }

  # You can find all the default arguments here :
  # https://docs.aws.amazon.com/glue/latest/dg/aws-glue-programming-etl-glue-arguments.html
  default_arguments = {
    "--job-language" = "scala",
    "--class" = "${var.class_name_scala}",
    "--TempDir" = "s3://${var.my_temporary_directory}"
  }
}
