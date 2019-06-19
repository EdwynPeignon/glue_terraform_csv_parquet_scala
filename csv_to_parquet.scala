import com.amazonaws.services.glue.ChoiceOption
import com.amazonaws.services.glue.GlueContext
import com.amazonaws.services.glue.MappingSpec
import com.amazonaws.services.glue.ResolveSpec
import com.amazonaws.services.glue.errors.CallSite
import com.amazonaws.services.glue.util.GlueArgParser
import com.amazonaws.services.glue.util.Job
import com.amazonaws.services.glue.util.JsonOptions
import org.apache.spark.SparkContext
import scala.collection.JavaConverters._

object GlueApp {
  def main(sysArgs: Array[String]) {
    val output_parquet: String = "s3://my_bucket"
    val table_name: String = "mycsvname_csv"
    val database_name: String = "my_catalogue"


    val spark: SparkContext = new SparkContext()
    val glueContext: GlueContext = new GlueContext(spark)
    // @params: [JOB_NAME]
    val args = GlueArgParser.getResolvedOptions(sysArgs, Seq("JOB_NAME").toArray)
    Job.init(args("JOB_NAME"), glueContext, args.asJava)
    // @type: DataSource
    // @args: [database = database_name, table_name = table_name, transformation_ctx = "datasource0"]
    // @return: datasource0
    // @inputs: []
    val datasource0 = glueContext.getCatalogSource(database = database_name, tableName = table_name, redshiftTmpDir = "", transformationContext = "datasource0").getDynamicFrame()
    // @type: ApplyMapping
    // @args: [mapping = [("seq", "long", "seq", "long"), ("name/first", "string", "name/first", "string"), ("name/last", "string", "name/last", "string"), ("street", "string", "street", "string"), ("city", "string", "city", "string"), ("state", "string", "state", "string"), ("zip", "long", "zip", "long"), ("dollar", "string", "dollar", "string"), ("pick", "string", "pick", "string"), ("date", "string", "date", "string")], transformation_ctx = "applymapping1"]
    // @return: applymapping1
    // @inputs: [frame = datasource0]
    val applymapping1 = datasource0.applyMapping(mappings = Seq(("seq", "long", "seq", "long"), ("name/first", "string", "name/first", "string"), ("name/last", "string", "name/last", "string"), ("street", "string", "street", "string"), ("city", "string", "city", "string"), ("state", "string", "state", "string"), ("zip", "long", "zip", "long"), ("dollar", "string", "dollar", "string"), ("pick", "string", "pick", "string"), ("date", "string", "date", "string")), caseSensitive = false, transformationContext = "applymapping1")
    // @type: ResolveChoice
    // @args: [choice = "make_struct", transformation_ctx = "resolvechoice2"]
    // @return: resolvechoice2
    // @inputs: [frame = applymapping1]
    val resolvechoice2 = applymapping1.resolveChoice(choiceOption = Some(ChoiceOption("make_struct")), transformationContext = "resolvechoice2")
    // @type: DropNullFields
    // @args: [transformation_ctx = "dropnullfields3"]
    // @return: dropnullfields3
    // @inputs: [frame = resolvechoice2]
    val dropnullfields3 = resolvechoice2.dropNulls(transformationContext = "dropnullfields3")
    // @type: DataSink
    // @args: [connection_type = "s3", connection_options = {"path": output_parquet}, format = "parquet", transformation_ctx = "datasink4"]
    // @return: datasink4
    // @inputs: [frame = dropnullfields3]
    val datasink4 = glueContext.getSinkWithFormat(connectionType = "s3", options = JsonOptions(raw"""{"path": "$output_parquet"}"""), transformationContext = "datasink4", format = "parquet").writeDynamicFrame(dropnullfields3)
    Job.commit()
  }
}