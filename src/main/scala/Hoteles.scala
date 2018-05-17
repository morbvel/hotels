import org.apache.spark.sql.{DataFrame, SparkSession}


object Hoteles  {

  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "C:\\winutils")

    val spark = SparkSession.builder()
      .appName("DrinksProvider")
      .master("local")
      .getOrCreate()

    val europe: DataFrame = readCsvFile(spark,"src/main/resources/europe.csv")
    val revenue: DataFrame = readCsvFile(spark,"src/main/resources/revenue.csv")

    /*
    * Filtrar por españoles solo
    *  europe.as("e").filter(col("e.cc1").equalTo("es")).show(false)
    */

    /*
    *  Filtrar por españoles y mostrar su revenue
    *    europe.as("e").filter(col("e.cc1").equalTo("es")).join(revenue.as("r")
    *      ,col("e.id").equalTo(col("r.id"))
    *      ,"left_outer").select("name","revenue").show(false)
    */

    /*
    * 100 hoteles que más revenue han generado
    *   europe.as("e").filter(col("e.cc1").equalTo("es")).join(revenue.as("r")
    *     ,col("e.id").equalTo(col("r.id"))
    *     ,"left_outer").select("name","revenue").sort(col("revenue").desc).show(100, false)
    */

    europe.as("e").filter(col("e.cc1").equalTo("es")).join(revenue.as("r")
      ,col("e.id").equalTo(col("r.id"))
      ,"left_outer").select("name","city_hotel","revenue").sort(col("revenue").desc).show(200, false)

  }

  private def readCsvFile(spark: SparkSession, src: String) = {
    spark.read
      .format("com.databricks.spark.csv")
      .option("header", "true") // Use first line of all files as header
      .option("inferSchema", "true") // Automatically infer data types
      .option("delimiter", ";")
      .load(src)
  }
}
