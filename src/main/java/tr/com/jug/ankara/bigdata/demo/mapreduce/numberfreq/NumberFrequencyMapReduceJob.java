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

package tr.com.jug.ankara.bigdata.demo.mapreduce.numberfreq;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
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
 * Cluster Name         : Number_Frequency_Job_Cluster
 * Log Folder           : s3://ankarajug-bigdata-demo/map-reduce/number-frequency/log
 * Input Path           : s3://ankarajug-bigdata-demo/map-reduce/number-frequency/input
 * Output Path          : s3://ankarajug-bigdata-demo/map-reduce/number-frequency/output
 * Job Type             : 1 (NUMBER_FREQUENCY_JOB)
 * All arguments        : s3://ankarajug-bigdata-demo/map-reduce/number-frequency/input s3://ankarajug-bigdata-demo/map-reduce/number-frequency/output 1
 */
public class NumberFrequencyMapReduceJob extends BaseMapReduceJob {

    public NumberFrequencyMapReduceJob() {
        super(MapReduceJobType.NUMBER_FREQUENCY_JOB);
    }

    @Override
    public void doConfig(String[] args, Job job, JobConf conf, Path inputPath, Path outputPath) {
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);
         
        job.setMapperClass(NumberFrequencyMapper.class);
        job.setReducerClass(NumberFrequencyReducer.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
    }

}
