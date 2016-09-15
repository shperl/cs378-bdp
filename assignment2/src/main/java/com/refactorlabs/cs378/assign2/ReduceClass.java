package com.refactorlabs.cs378.assign2;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


/**
 * The Reduce class for word count.
 * Will sum all of the combined:
 	* paragraph counts,
 	* word counts,
 	* and squared word counts (used for variance)
 	* also calculates variance & mean
 */
public class ReduceClass extends Reducer<Text, WordStatisticsWritable, Text, WordStatisticsWritable> {

	/**
	 * Counter group for the reducer.  Individual counters are grouped for the reducer.
	 */
	private static final String REDUCER_COUNTER_GROUP = "Reducer Counts";
	
	
	@Override
	public void reduce(Text key, Iterable<WordStatisticsWritable> values, Context context)
			throws IOException, InterruptedException {

		// used for output of word
		WordStatisticsWritable wordStats = new WordStatisticsWritable();
		
		
		long paragraphs = 0L;
		long occurrences = 0L;
		long squared = 0L;
		
		for (WordStatisticsWritable value : values) {
			// parse out values and aggregate
			
			paragraphs += value.getParagraphs(); // total number of paragraphs
			occurrences += value.getOccurrences(); // total number of word instances
			squared += value.getSquared(); // total sum of squared occurrences per paragraph
			
		}
		
		// calculate the mean and store it
		double mean = avg(paragraphs, occurrences);
		// calculate the variance and store it
		double variance = variance(paragraphs, squared, mean);
		
		// set values into the output writable
		wordStats.setMean(mean);
		wordStats.setVariance(variance);
		wordStats.setParagraphs(paragraphs);
		wordStats.setOccurrences(occurrences);
		wordStats.setSquared(squared);
		
		// write the output writable to the context, using input key as output key
		context.write(key, wordStats);
		
		context.getCounter(REDUCER_COUNTER_GROUP, "Words Out").increment(1L);

	}
	
	public double variance(long paragraphs, long squared, double mean){
		// calculates the variance from the number of paragraphs, squared counts, and mean
		double squaredMean = mean * mean;
		double expectedSquared = (1.0 * squared) / (1.0 * paragraphs);
		
		return expectedSquared - squaredMean;
		
	}
	
	public double avg(long paragraphs, long occurrences){
		// finds the average number of word instances per paragraph
		return (1.0 * occurrences) / (1.0 * paragraphs);
	}
}