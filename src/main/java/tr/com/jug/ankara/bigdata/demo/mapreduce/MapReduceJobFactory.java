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

import tr.com.jug.ankara.bigdata.demo.mapreduce.logdata.LogDataMapReduceJob;
import tr.com.jug.ankara.bigdata.demo.mapreduce.numberfreq.NumberFrequencyMapReduceJob;

/**
 * @author Serkan OZAL
 */
public class MapReduceJobFactory {

	private MapReduceJobFactory() {
		
	}
	
	public static MapReduceJob getMapReduceJob(int jobTypeCode) {
		MapReduceJobType mapReduceJobType = MapReduceJobType.getByCode(jobTypeCode);
		if (mapReduceJobType == null) {
			throw new IllegalArgumentException("Unknown map-reduce job type code: " + jobTypeCode);
		}
		switch (mapReduceJobType) {
			case NUMBER_FREQUENCY_JOB:
				return new NumberFrequencyMapReduceJob();
			case LOG_DATA_JOB:
				return new LogDataMapReduceJob();
			default:
				throw new IllegalArgumentException("Unknown map-reduce job type: " + mapReduceJobType);
		}
	}
	
}
