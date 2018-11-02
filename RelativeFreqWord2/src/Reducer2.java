
//Developed: Dhara Rana
//10/30/18

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.*;
import java.io.IOException;

public class Reducer2 extends Reducer<DoubleWritable,AdjacentWord,AdjacentWord,DoubleWritable> {
    private int i = 0;
    @Override
    protected void reduce(DoubleWritable key, Iterable<AdjacentWord> values, Context context) throws IOException, InterruptedException {
       
        for (AdjacentWord value : values) {
        	
        	if(value.getNeighbor().toString().equals("*")) {
                if(i >= 100)
                    continue;
                context.write(value,key);
                i++;
        	}else {

        		context.write(value,key);        		
        	}

        }
    }
}
