

//Developed: Dhara Rana
//10/30/18

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class WordPairPartitioner extends Partitioner<WordPair,DoubleWritable> {
	
	public int getPartition(WordPair wordPair, DoubleWritable doubleWritable, int numPartitions) {
        return (wordPair.getWord().hashCode() & Integer.MAX_VALUE ) % numPartitions;
    }

}
