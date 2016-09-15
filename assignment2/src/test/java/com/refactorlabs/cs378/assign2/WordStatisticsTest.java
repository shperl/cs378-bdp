//package com.refactorlabs.cs378.assign2;
//
//
//import java.io.IOException;
//import java.util.Arrays;
//import org.apache.hadoop.io.LongWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mrunit.mapreduce.MapDriver;
//import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
//import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
//import org.junit.Before;
//import org.junit.Test;
//
//public class WordStatisticsTest {
//    MapDriver<LongWritable, Text, Text, WordStatisticsWritable> mapDriver;
//    ReduceDriver<Text,WordStatisticsWritable,Text, WordStatisticsWritable> reduceDriver;
//    MapReduceDriver<LongWritable, Text, Text, WordStatisticsWritable,Text, WordStatisticsWritable> mapReduceDriver;
//
//    @Before
//    public void setUp() {
//        MapClass mapper = new MapClass();
//        ReduceClass reducer = new ReduceClass();
//        mapDriver = MapDriver.newMapDriver(mapper);
//        reduceDriver = ReduceDriver.newReduceDriver(reducer);
//        mapReduceDriver = mapReduceDriver.newMapReduceDriver(mapper,reducer);
//    }

//    @Test
//    public void testMapper() throws IOException {
//        mapDriver.withInput(new LongWritable(), new Text(
//                "Hello World Hello \nWorld Hello World Hello\n"));
//        WordStatisticsWritable x = new WordStatisticsWritable();
//
//
//        x.setOccurrences(2L);
//        x.setParagraphs(1L);
//        x.setSquared(4L);
//        mapDriver.withOutput(new Text("hello"), x);
//
//        x.setOccurrences(1L);
//        x.setParagraphs(1L);
//        x.setSquared(1L);
//        mapDriver.withOutput(new Text("world"), x);
//
//        x.setOccurrences(2L);
//        x.setParagraphs(1L);
//        x.setSquared(4L);
//        mapDriver.withOutput(new Text("world"), x);
//        
//        x.setOccurrences(2L);
//        x.setParagraphs(1L);
//        x.setSquared(4L);
//        mapDriver.withOutput(new Text("hello"), x);
//
//
//
//        mapDriver.runTest();
//    }

//    @Test
//    public void testReducer() throws IOException {
//
//    	WordStatisticsWritable[] values = new WordStatisticsWritable[2];
//
//        WordStatisticsWritable x1 = new WordStatisticsWritable();
//        x1.setOccurrences(1L);
//        x1.setParagraphs(1L);
//        x1.setSquared(1L);
//        x1.setMean(1.0);
//        x1.setVariance(0.0);
//
//        WordStatisticsWritable x2 = new WordStatisticsWritable();
//        x2.setOccurrences(1L);
//        x2.setParagraphs(1L);
//        x2.setSquared(1L);
//        x2.setMean(1.0);
//        x2.setVariance(0.0);
//        
//        values[0] = x1;
//        values[1] = x2;
//
//        WordStatisticsWritable y = new WordStatisticsWritable();
//        y.setOccurrences(2L);
//        y.setParagraphs(2L);
//        y.setSquared(4L);
//        y.setMean(1.0);
//        y.setVariance(0.0);
//
//        reduceDriver.withInput(new Text("hello"), Arrays.asList(values));
//        reduceDriver.withOutput(new Text("hello"), y);
//        reduceDriver.runTest();
//    }

//    @Test
//    public void testMapReduce() throws IOException {
//
//        mapReduceDriver.withInput(new LongWritable(), new Text(
//                "Hello World Hello \nWorld Hello World Hello\n"));
//
//        WordStatisticsWritable y1 = new WordStatisticsWritable();
//        double[] dArr = new double[3];
//        dArr[0]=2;
//        dArr[1]=2.0;
//        dArr[2]=0.0;
//        y1.setValues(dArr);
//
//        WordStatisticsWritable y2 = new WordStatisticsWritable();
//        dArr[0]=2;
//        dArr[1]=1.5;
//        dArr[2]=0.5;
//        y2.setValues(dArr);
//
//        mapReduceDriver.withOutput(new Text("hello"), y1);
//        mapReduceDriver.withOutput(new Text("world"), y2);
//        mapReduceDriver.runTest();
//    }
    
//}