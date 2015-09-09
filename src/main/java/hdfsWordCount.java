import java.util.Arrays;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.SparkConf;
import scala.Tuple2;

public class hdfsWordCount {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SparkConf conf = new SparkConf().setMaster("local").setAppName("hdfsWordCount"); 
		JavaSparkContext sc = new JavaSparkContext(conf);
		//JavaSparkContext spark = new JavaSparkContext(new SparkConf().setAppName("Word Count"));
		
		JavaRDD<String> textFile = sc.textFile("hdfs://localhost:8020/user/jerry/new.txt");
		JavaRDD<String> words = textFile.flatMap(new FlatMapFunction<String, String>() {
			public Iterable<String> call(String s) { return Arrays.asList(s.split(" ")); }
		});
		JavaPairRDD<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
			public Tuple2<String, Integer> call(String s) { return new Tuple2<String, Integer>(s, 1); }
		});
		JavaPairRDD<String, Integer> counts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
			public Integer call(Integer a, Integer b) { return a + b; }
		});
		counts.coalesce(1).saveAsTextFile("hdfs://localhost:8020/user/jerry/output");
	}
}
