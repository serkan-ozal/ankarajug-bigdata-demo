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
import java.util.Random;

/**
 * @author Serkan OZAL
 */
public class NumberDataGenerator {

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String DIRECTORY_PATH = "map-reduce" + File.separator + "number-frequency" + File.separator;
    private static final int BLOCK_COUNT = 9;
    private static final int NUMBER_COUNT_IN_A_BLOCK = 25000000; // To be close to 128 MB chunk
    
    public static void main(String[] args) throws IOException {
        Random r = new Random();
        File outputDirectory = new File(DIRECTORY_PATH);
        outputDirectory.mkdirs();
        for (int i = 0; i < BLOCK_COUNT; i++) {
            File outputPath = new File(outputDirectory.getAbsolutePath() + File.separator + "input-" + (i + 1) + ".txt");
            outputPath.createNewFile();
            FileWriter fw = new FileWriter(outputPath);
            for (int j = 0; j < NUMBER_COUNT_IN_A_BLOCK; j++) {
                fw.write(String.valueOf(r.nextInt(1000)) + NEW_LINE);
            }
            fw.flush();
            fw.close();
        }   
        System.gc();
    }

}
