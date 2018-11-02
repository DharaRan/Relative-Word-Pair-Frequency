
//Developed: Dhara Rana
//10/30/18

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import java.util.*;
import java.io.IOException;

public class Reducer4 extends Reducer<DoubleWritable,WordPair,WordPair,DoubleWritable>{
	
	private int i = 0; 
	 
    protected void reduce(DoubleWritable key, Iterable<WordPair> values, Context context) throws IOException, InterruptedException {
        
        for (WordPair value : values) {
	          if(i >= 100)
	              break;
	          //if(key.get() == 1.0)
	            //  continue;

	          context.write(value,key);
	          i++;
	        }
        //System.out.println("Finished Running Reducers 4");
    }	 

	

}
