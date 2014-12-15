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

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

/**
 * @author Serkan OZAL
 * 
 * Map implementation of Map/Reduce job.
 * Reads input and generates partial intermediate results.
 */
public class NumberCountMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable> {

	private static final Logger logger = Logger.getLogger(NumberCountMapper.class); 

	private final static IntWritable ONE = new IntWritable(1);
	
    @Override
    public void run(Context context) throws IOException, InterruptedException {
    	init(context);
    	super.run(context);
    }

    protected void init(Context context) {
    	try {
    		logger.info("Mapper has been initialized ...");
    	}
    	catch (Throwable t) {
    		logger.error("Error occured while initializing Mapper", t);
    	}
    }

	@Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        try {
        	String line = value.toString();
        	// Cast it to integer typed number. Because every line in input file consist of only one number.
    		int number = Integer.parseInt(line);
    		// Write context that this number exist 1 time for this line
    		context.write(new IntWritable(number), ONE);
        }
        catch (Throwable t) {
        	logger.error("Error occured while executing map function of Mapper", t);
        }
    }

}
