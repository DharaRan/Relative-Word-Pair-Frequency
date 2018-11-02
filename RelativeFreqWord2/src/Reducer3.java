

//Developed: Dhara Rana
//10/30/18

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import java.io.IOException;

public class Reducer3 extends Reducer<WordPair, DoubleWritable, WordPair, DoubleWritable> {
    private DoubleWritable totalCount = new DoubleWritable();
    private DoubleWritable relativeCount = new DoubleWritable();
    private Text currentWord = new Text("NOT_SET");
    private Text flag = new Text("*");

    @Override
    protected void reduce(WordPair key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
        
    	if (key.getNeighbor().equals(flag)) {
            if (key.getWord().equals(currentWord)) {
                totalCount.set(totalCount.get() + getTotalCount(values));
            } else {
                currentWord.set(key.getWord());
                totalCount.set(0);
                totalCount.set(getTotalCount(values));
            }
        } else {
            double count = getTotalCount(values);
            relativeCount.set((double) count / totalCount.get());
            if(relativeCount.get() != Double.POSITIVE_INFINITY) {
            	context.write(key, relativeCount);
            }
            
        }
    	
    	//System.out.println("Key, word: "+key.getWord());
    	//System.out.println("Key, wordNeighbor: "+key.getNeighbor());
    	//System.out.println("Total individual word Count : "+ totalCount.get());
    	//System.out.println("RF: "+ relativeCount.get());
    	
    	

    }

    private int getTotalCount(Iterable<DoubleWritable> values) {
        int count = 0;
        for (DoubleWritable value : values) {
            count += value.get();
        }
        return count;
    }

}
