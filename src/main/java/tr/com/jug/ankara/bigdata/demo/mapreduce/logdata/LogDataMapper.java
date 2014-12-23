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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tr.com.jug.ankara.bigdata.demo.mapreduce.MapReduceJob;

/**
 * @author Serkan OZAL
 */
public class LogDataMapper extends Mapper<LongWritable, Text, NullWritable, IntWritable> {

    private static final Logger logger = Logger.getLogger(LogDataMapper.class); 

    private static final DateFormat LOG_DATA_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private static final Gson GSON = new GsonBuilder().create();
    
    private int count;
    private Date startDate;
    private Date endDate;
    
    @Override
    public void run(Context context) throws IOException, InterruptedException {
        init(context);
        super.run(context);
    }

    protected void init(Context context) {
        try {
            // Get start date from parameters
            String arg0 = context.getConfiguration().get(MapReduceJob.JOB_ARGUMENT_NAME_PREFIX + "0");
            if (StringUtils.isNotEmpty(arg0)) {
                startDate = LOG_DATA_DATE_FORMAT.parse(arg0);
            }
            
            // Get end date from parameters
            String arg1 = context.getConfiguration().get(MapReduceJob.JOB_ARGUMENT_NAME_PREFIX + "1");
            if (StringUtils.isNotEmpty(arg1)) {
                endDate = LOG_DATA_DATE_FORMAT.parse(arg1);
            }
            
            logger.info("LogDataMapper has been initialized with startDate: " + 
                        startDate + " and endDate: " + endDate + " ...");
        }
        catch (Throwable t) {
            logger.error("Error occured while initializing LogDataMapper", t);
        }
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        try {
            String line = value.toString();
            LogData logData = GSON.fromJson(line, LogData.class);
            boolean startDateOk = true;
            boolean endDateOk = true;
            // Check that is it OK as start date if start date is available 
            if (startDate != null && logData.getDate().before(startDate)) {
                startDateOk = false;
            }
            // Check that is it OK as end date if end date is available 
            if (endDate != null && logData.getDate().after(endDate)) {
                endDateOk = false;
            }
            if (startDateOk && endDateOk) {
                count++;
            }
        }
        catch (Throwable t) {
            logger.error("Error occured while executing map function of LogDataMapper", t);
        }
    }
    
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        // Emit partial log count on clean up to only one reducer by using constant key (NullWritable)
        context.write(NullWritable.get(), new IntWritable(count));
    }

}
