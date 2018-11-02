
//Developed: Dhara Rana
//10/30/18

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class Reducer1 extends Reducer<AdjacentWord, IntWritable, AdjacentWord, DoubleWritable> {
    private DoubleWritable totalCount = new DoubleWritable();
    private DoubleWritable wordpairCount = new DoubleWritable();
    private Text currentWord = new Text("NOT_SET");
    private Text flag = new Text("*");
    private AdjacentWord wordPair = new AdjacentWord();
    
    
    @Override
    protected void reduce(AdjacentWord key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
    	
    	
    	if (key.getNeighbor().equals(flag)) {
            if (key.getWord().equals(currentWord)) {
                totalCount.set(totalCount.get() + getTotalCount(values));
                //System.out.println("Key, word: "+key.getWord());
            	//System.out.println("Key, wordNeighbor: "+key.getNeighbor());
            	//System.out.println("Total individual word Count : "+ totalCount.get());
            	//System.out.println("NotStored");

            } else {
            	
            	if( totalCount.get()!=0 && !currentWord.equals("NOT_SET")) {
            		 wordPair.setWord(currentWord.toString());
            		 wordPair.setNeighbor("*");   
            		 context.write(wordPair, totalCount);
            		 //System.out.println("Key, word: "+wordPair.getWord());
            	     //System.out.println("Key, wordNeighbor: "+wordPair.getNeighbor());
            	     //System.out.println("Total individual word Count : "+ totalCount.get());
            		 //System.out.println("Stored");
            		 //System.out.println("");
            	}
            	
                currentWord.set(key.getWord());
                totalCount.set(0);
                totalCount.set(getTotalCount(values));
                //System.out.println("Key, word: "+key.getWord());
            	//System.out.println("Key, wordNeighbor: "+key.getNeighbor());
            	//System.out.println("Total individual word Count : "+ totalCount.get());
            	//System.out.println("NotStored");

            }
        } else {        	
        	
        	if(totalCount.get()!=0) {
        	 wordPair.setWord(currentWord.toString());
       		 wordPair.setNeighbor("*");   
       		 context.write(wordPair, totalCount);
       		 //System.out.println("Key, word: "+wordPair.getWord());
       	     //System.out.println("Key, wordNeighbor: "+wordPair.getNeighbor());
       	     //System.out.println("Total individual word Count : "+ totalCount.get());
       		 //System.out.println("Stored");
       		 //System.out.println("");
       		totalCount.set(0);
        	}
        	
        	wordpairCount.set(getTotalCount(values));
            context.write(key, wordpairCount);
            //System.out.println("Key, word: "+key.getWord());
        	//System.out.println("Key, wordNeighbor: "+key.getNeighbor());
        	//System.out.println("Total individual word Count : "+ wordpairCount.get());
            //System.out.println("Stored");
        }
    	

    	//System.out.println("");

    }

    private int getTotalCount(Iterable<IntWritable> values) {
        int count = 0;
        for (IntWritable value : values) {
            count += value.get();
        }
        return count;
    }
}
