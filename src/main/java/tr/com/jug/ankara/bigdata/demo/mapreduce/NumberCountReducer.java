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
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

/**
 * @author Serkan OZAL
 *  
 * Reduce implementation of Map/Reduce job.
 * Gets partial intermediate results as grouped and generates its result.
 */
public class NumberCountReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {

	private static final Logger logger = Logger.getLogger(NumberCountReducer.class); 
    
    @Override
    public void run(Context context) throws IOException, InterruptedException {
    	init(context);
    	super.run(context);
    }
    
	protected void init(Context context) {
    	try {
    		logger.info("Reducer has been initialized ...");
    	}
    	catch (Throwable t) {
    		logger.error("Error occured while initializing Reducer", t);
    	}
    }

    protected void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		try {
			int sum = 0;
			Iterator<IntWritable> i = values.iterator();
			// Explore all occurences of this number and calculate sum of them as frequency of number
			while (i.hasNext()) {
				sum += i.next().get();
			}
			// Write occurence of this number 
			context.write(key, new IntWritable(sum));
		}
        catch (Throwable t) {
        	logger.error("Error occured while executing reduce function of Reducer", t);
        }    
    }

}
