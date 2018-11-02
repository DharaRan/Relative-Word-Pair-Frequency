
//Developed: Dhara Rana
//10/30/18

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class FlagWordPartitioner extends Partitioner<DoubleWritable, AdjacentWord> {

    @Override
    public int getPartition(DoubleWritable doublewritable, AdjacentWord wordPair1, int numPartitions) {
    	
    	if(wordPair1.getNeighbor().toString().equals("*")) {
    		return 0;
    		
    	}else {
    		return 1;
    	}
    }
}
