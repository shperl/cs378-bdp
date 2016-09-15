package com.refactorlabs.cs378.assign2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * Saves the values used for word count statistics
 * Three longs and two doubles
 * Attributes: 
 	* Paragraphs: a count of paragraphs a word appears in
 	* Occurrences: a count of total instances of the word
 	* Squared: a square of the occurrences
 	* Mean: the average number of occurrences per paragraph
 	* Variance: the variance of the occurrnces per paragraph
 */
public class WordStatisticsWritable implements Writable {
	
	// paragraph count
	private long paragraphs;
	// word count
	private long occurrences;
	// squared occurrences
	private long squared;
	
	// the average number of occurrences per paragraph
	private double mean;
	// the variance of occurrences per paragraph
	private double variance;
	
	
	// set paragraph count
	public void setParagraphs(long paragraphs){
		this.paragraphs = paragraphs;
	}
	// get paragraph count
	public long getParagraphs(){
		return this.paragraphs;
	}
	
	// set occurrences count
	public void setOccurrences(long occurrences){
		this.occurrences = occurrences;
	}
	// get occurrences count
	public long getOccurrences(){
		return this.occurrences;
	}
	
	// set squared occurrences
	public void setSquared(long squared){
		this.squared = squared;
	}
	// get squared occurrences
	public long getSquared(){
		return this.squared;
	}
	
	// set mean
	public void setMean(double mean){
		this.mean = mean;
	}
	// get mean
	public double getMean(){
		return this.mean;
	}
	
	// set variance
	public void setVariance(double variance){
		this.variance = variance;
	}
	// get variance
	public double getVariance(){
		return this.variance;
	}
	
	// Constructor
	public WordStatisticsWritable() {
	}


	@Override
	public void readFields(DataInput arg0) throws IOException {
		// Read in fields from input and save to class instance
		
		// instantiate argument variables
		long arg1;
		double arg2;
		
		
		// read in the first value (paragraphs) and save to variable
		arg1 = arg0.readLong();
		// set the paragraphs
		setParagraphs(arg1);
		
		// read in the second value (occurrences)
		arg1 = arg0.readLong();
		// set the occurrences
		setOccurrences(arg1);
		
		// read in third value (squared)
		arg1 = arg0.readLong();
		// set the squared
		setSquared(arg1);
		
		// read in the fourth value (mean)
		arg2 = arg0.readDouble();
		// set the mean
		setMean(arg2);
		
		// read in fifth value (variance)
		arg2 = arg0.readDouble();
		// set the variance
		setVariance(arg2);
		
	}
	
	public boolean equals(WordStatisticsWritable other) {
		if (this.occurrences != other.getOccurrences() || this.paragraphs != other.getParagraphs() || this.squared != other.getSquared()){
			return false;
		} else if (this.mean - other.getMean() > .0001 || this.variance - other.getVariance() > .0001) {
			return false;
		}
		
		return true;
	}
	
	public int hashCode() {
		return toString().hashCode();
	}
	
	
	@Override
	public String toString() {
		return occurrences + ", " + mean + ", " + variance;
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// Write class attributes to data output object
		// Paragraphs, Occurrences, Squared, Mean, Variance
		
		arg0.writeLong(getParagraphs());
		arg0.writeLong(getOccurrences());
		arg0.writeLong(getSquared());
		
		arg0.writeDouble(getMean());
		arg0.writeDouble(getVariance());
		
		
	}
	

	
	
	
	
}