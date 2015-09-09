### HDFSWordCount
It's very simple program of 'word count' with a HDFS source file. Here are some pre-request before running this program. 
 
>* You must install Hadoop and Spark, and then start the HDFS.
>* Upload a text file to be counted onto the HDFS.
>* Change the HDFS file path to the corresponding file path where your uploaded file in previous step.
>
```java
textFile = sc.textFile("hdfs://localhost:8020/your_path");
```
