package com.refactorlabs.cs378.assign2;

import java.io.IOException;
import java.util.TreeMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;

/**
 * The Map class for word count.  Extends class Mapper, provided by Hadoop.
 * This class defines the map() function for the word count example.
 */
public class MapClass extends Mapper<LongWritable, Text, Text, WordStatisticsWritable> {
	
	private static final String MAPPER_COUNTER_GROUP = "Mapper Counts";
	
	// we will use this to emit word strings
	private Text word = new Text();
	
	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		// Get the first line and store as string
		String line = value.toString();
		
		// Tokenize the line -- on whitespace and on punctuation
		StringTokenizer tokenizer = new StringTokenizer(line, "[=\".,?! :;_\t");
		
		// Create a map that can be used to hold word frequency
		Map<String, Long> wordCounts = new TreeMap<String, Long>();
		
		// Iterate through line
		// Update map with incremented count
		while(tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken().toLowerCase();
			
			// account for strings that have ] at -1 char
			if (token.charAt(token.length()-1) == ']') {
				token = '[' + token;
			}
			
			if (token.contains("--")){
				int tokenIndex = token.indexOf("--");
				String word1 = token.substring(0, tokenIndex);
				if (word1.length() > 2) {
					if( word1.charAt(word1.length()-1) == ']' ) {
						word1 = '[' + word1;
					}
				}
				
				if(!(tokenIndex - token.length() <= 1)){
					String word2 = token.substring(token.indexOf("--") + 2, token.length() - 1);
					
					if (word2.charAt(word2.length()-1) == ']' && word2.length() > 1) {
						word2 = '[' + word2;
					}
					if(!wordCounts.containsKey(word2)) {

						wordCounts.put(word2, 1L);
					} else {
						wordCounts.put(word2, wordCounts.get(word2) + 1L);
					}
				}
				
				
				if(!wordCounts.containsKey(word1)) {
					wordCounts.put(word1, 1L);
				} else {
					wordCounts.put(word1, wordCounts.get(word1) + 1L);
				}
				
				
				
			} else if(!wordCounts.containsKey(token)) {
				wordCounts.put(token, 1L);
			} else {
				wordCounts.put(token, wordCounts.get(token) + 1L);
			}
		}
		
		// for each word in the map, emit value to output
		for (String wordKey : wordCounts.keySet()) {
			
			// Set current word text to the word key
			word.set(wordKey);
			long count = wordCounts.get(wordKey);
			
			// Create a WordStatisticsWritable object and update values
			WordStatisticsWritable wordStats = new WordStatisticsWritable();
			
			wordStats.setParagraphs(1L);
			wordStats.setOccurrences(count);
			wordStats.setSquared(count * count);
			wordStats.setMean(0);
			wordStats.setVariance(0);
			
			// Write the object to context with word as key
			context.write(word, wordStats);
			
			// Increment counter for words out
			context.getCounter(MAPPER_COUNTER_GROUP, "Words Out").increment(1L);
			
		}
		
		
		
	}

}