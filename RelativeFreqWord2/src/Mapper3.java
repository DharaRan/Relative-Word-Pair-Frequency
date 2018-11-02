
//Developed: Dhara Rana
//10/30/18

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

import java.util.*;
import java.io.IOException;

public class Mapper3 extends Mapper<Object, Text,WordPair, DoubleWritable> {

    private DoubleWritable FrequencyCount = new DoubleWritable();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

    	String line=value.toString();
    	String[] words = line.split("\\s+");
    	
    	if(words.length ==3) {
    		WordPair wordpair = new WordPair(words[0], words[1]);
			FrequencyCount.set(Double.parseDouble(words[2]));
			//System.out.println("First Word "+ wordpair.getWord());
		    //System.out.println("Adjacent Word "+ wordpair.getNeighbor());
		    //System.out.println("Count "+ FrequencyCount.get());

	        context.write(wordpair,FrequencyCount);
		
		}
    	
    	
       
       
       
    }

}
