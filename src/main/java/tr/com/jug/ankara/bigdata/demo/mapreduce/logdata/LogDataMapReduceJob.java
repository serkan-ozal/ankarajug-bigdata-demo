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

package tr.com.jug.ankara.bigdata.demo.mapreduce.logdata;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import tr.com.jug.ankara.bigdata.demo.mapreduce.BaseMapReduceJob;
import tr.com.jug.ankara.bigdata.demo.mapreduce.MapReduceJobType;

/**
 * @author Serkan OZAL
 */
/*
 * Cluster Name         : Log_Data_Job_Cluster
 * Log Folder           : s3://ankarajug-bigdata-demo/map-reduce/log-data/log
 * Input Path           : s3://ankarajug-bigdata-demo/map-reduce/log-data/input
 * Output Path          : s3://ankarajug-bigdata-demo/map-reduce/log-data/output
 * Job Type             : 2 (LOG_DATA_JOB)
 * Start Date           : 01-01-2013 
 * End Date             : 01-01-2014
 * All arguments        : s3://ankarajug-bigdata-demo/map-reduce/log-data/input s3://ankarajug-bigdata-demo/map-reduce/log-data/output 2 01-01-2013 01-01-2014
 */
public class LogDataMapReduceJob extends BaseMapReduceJob {

	public LogDataMapReduceJob() {
		super(MapReduceJobType.LOG_DATA_JOB);
	}

	@Override
	public void doConfig(String[] args, Job job, JobConf conf, Path inputPath, Path outputPath) {
		job.setMapOutputKeyClass(NullWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(IntWritable.class);
		 
		job.setMapperClass(LogDataMapper.class);
		job.setReducerClass(LogDataReducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setNumReduceTasks(1); // Because we are using "NullWritable". Only 1 reducer will be used
	}

}
