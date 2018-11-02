
//Developed: Dhara Rana
//10/30/18

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

import java.util.*;
import java.io.IOException;

public class Mapper4 extends Mapper<Object, Text, DoubleWritable, WordPair> {
	private String[] str;
    private String[] tokens;
    private DoubleWritable RelativeFrequencyCount = new DoubleWritable();
    
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
    
    	
	   StringTokenizer itr = new StringTokenizer(value.toString(), "\n");
	   
	   while (itr.hasMoreTokens()) {
		      tokens = itr.nextToken().toString().split("\t");
		      str = tokens[0].toString().split(" ");
		      WordPair wordpair = new WordPair(str[0], str[1]);
		      RelativeFrequencyCount.set(Double.parseDouble(tokens[1].trim()));
		      
		      if(RelativeFrequencyCount == null)
		          continue;
		      context.write(RelativeFrequencyCount, wordpair);
	   }
	   //System.out.println("Finished Running MAPPER 4");
    }    

}
