/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tr.com.jug.ankara.bigdata.demo.mapreduce;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;

/**
 * @author Serkan OZAL
 * 
 * Starts Map/Reduce job. Runs on Master node and submits sub-job to slave nodes (mapper and reducer nodes)
 */
/*
 * AWS EMR URL          : https://console.aws.amazon.com/elasticmapreduce/home?region=us-east-1
 * Spot Instance Prices : http://aws.amazon.com/ec2/purchasing-options/spot-instances/#pricing
 * Spot Instance Price  : 0.01$
 * Jar Location         : s3://ankarajug-bigdata-demo-482514484979/ankarajug-bigdata-demo-mapreduce-job.jar
 */
public class MapReduceDriver {

	private static final Logger logger = Logger.getLogger(MapReduceDriver.class);
	
	public static final int INPUT_PATH_ARGUMENT_ORDER = 0;
	public static final int OUTPUT_PATH_ARGUMENT_ORDER = 1;
	public static final int JOB_TYPE_ARGUMENT_ORDER = 2;
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		long start, finish;
		long executionTimeInSeconds, executionTimeInMinutes;
		
		JobConf conf = new JobConf();
		
		// Use SPACE instead of TAB as separator between key and value at output
		conf.set("mapred.textoutputformat.separator", " "); // Prior to Hadoop 2 (YARN)
		conf.set("mapreduce.textoutputformat.separator", " ");  // Hadoop v2+ (YARN)
		conf.set("mapreduce.output.textoutputformat.separator", " ");
		conf.set("mapreduce.output.key.field.separator", " ");
		conf.set("mapred.textoutputformat.separatorText", " "); 
		
		// Store all job arguments in configuration object
		for (int i = JOB_TYPE_ARGUMENT_ORDER + 1, j = 0; i < args.length; i++, j++) {
			conf.set(MapReduceJob.JOB_ARGUMENT_NAME_PREFIX + j, args[i]);
		}
		
		MapReduceJob mapReduceJob = MapReduceJobFactory.getMapReduceJob(Integer.parseInt(args[JOB_TYPE_ARGUMENT_ORDER]));
		
		// *** NOTE ***: Update configuration before passing configuration to job as parameter
		
		Job job = new Job(conf, "Ankara JUG Map-Reduce Job " + "[" + mapReduceJob.getMapReduceJobType() + "]");
		
		job.setJarByClass(MapReduceDriver.class);
		 
		Path inputPath = new Path(args[INPUT_PATH_ARGUMENT_ORDER]);
		Path outputPath = new Path(args[OUTPUT_PATH_ARGUMENT_ORDER]);
		
		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		
		FileSystem outputFS = outputPath.getFileSystem(conf);
		
		mapReduceJob.doConfig(args, job, conf, inputPath, outputPath);
	
		////////////////////////////////////////////////////////////////////////////////////

		logger.info("MapReduce job started ...");
		start = System.currentTimeMillis();
		
		// Start job and wait it for completion
		job.waitForCompletion(true);
		
		finish = System.currentTimeMillis();
		executionTimeInSeconds = (finish - start) / 1000;
		executionTimeInMinutes = executionTimeInSeconds / 60;
		logger.info("MapReduce job finished in " + 
						executionTimeInSeconds + " seconds " + 
						"(" + executionTimeInMinutes + " minutes" + ")");
		
		////////////////////////////////////////////////////////////////////////////////////
		
		Properties props = System.getProperties();
		String outputFileName = props.getProperty("outputFileName");
		if (StringUtils.isEmpty(outputFileName)) {
			outputFileName = "output.txt";
		}
		Path resultPath = new Path(args[OUTPUT_PATH_ARGUMENT_ORDER] + "/" + outputFileName);
		
		logger.info("MapReduce output merging started ...");
		start = System.currentTimeMillis();
		
		// Merge all partitioned output files to single result file such as "output.txt" in output directory.
		FileUtil.copyMerge(outputFS, outputPath, outputFS, resultPath, false, conf, null);

		finish = System.currentTimeMillis();
		executionTimeInSeconds = (finish - start) / 1000;
		executionTimeInMinutes = executionTimeInSeconds / 60;
		logger.info("MapReduce output merging finished in " + 
						executionTimeInSeconds + " seconds " + 
						"(" + executionTimeInMinutes + " minutes" + ")");
	}

}
