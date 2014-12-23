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

package tr.com.jug.ankara.bigdata.demo.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import tr.com.jug.ankara.bigdata.demo.mapreduce.logdata.LogData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Serkan OZAL
 */
public class LogDataGenerator {

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String DIRECTORY_PATH = "map-reduce" + File.separator + "log-data" + File.separator;
    private static final int BLOCK_COUNT = 9;
    private static final int LOG_DATA_COUNT_IN_A_BLOCK = 1750000; // To be close to 128 MB chunk
    private static final Random random = new Random();
    private static final String[] USERNAMES = new String[100];
    
    static {
        for (int i = 0; i < 100; i++) {
            USERNAMES[i] = "user_" + (i + 1);
        }
        // TODO Maybe use more meaningful (real) usernames
    }
    
    public static void main(String[] args) throws IOException {
        Gson gson = new GsonBuilder().create();
        File outputDirectory = new File(DIRECTORY_PATH);
        outputDirectory.mkdirs();
        for (int i = 0; i < BLOCK_COUNT; i++) {
            File outputPath = new File(outputDirectory.getAbsolutePath() + File.separator + "input-" + (i + 1) + ".txt");
            outputPath.createNewFile();
            FileWriter fw = new FileWriter(outputPath);
            for (int j = 0; j < LOG_DATA_COUNT_IN_A_BLOCK; j++) {
                fw.write(gson.toJson(createRandomLogData()) + NEW_LINE);
            }
            fw.flush();
            fw.close();
        }   
        System.gc();
    }
    
    @SuppressWarnings("deprecation")
    private static LogData createRandomLogData() {
        String username = USERNAMES[random.nextInt(USERNAMES.length)];
        String ip = random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256);
        Date date = 
            new Date(
                100 + random.nextInt(15), // Year is taken as "<actual_year> - 1900" by "java.util.Date" object, so all years from 2000 (last 15 years)
                random.nextInt(12),       // Month is taken as number between [0-11]
                1 + random.nextInt(28),   // Date is taken as number between [1-31]. To handle all months, date can be 28 at max
                random.nextInt(24),       // Hour
                random.nextInt(60),       // minute
                random.nextInt(1000));    // millisecond
        return new LogData(username, ip, date);
    }

}
