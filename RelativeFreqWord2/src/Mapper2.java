//Developed: Dhara Rana
//10/30/18

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.*;
import java.io.IOException;

 
public class Mapper2
       extends Mapper<Object, Text, DoubleWritable, AdjacentWord>{

        private String[] str;
        private String[] tokens;
        private DoubleWritable FrequencyCount = new DoubleWritable();
 
        public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {

           StringTokenizer itr = new StringTokenizer(value.toString(), "\n");
           while (itr.hasMoreTokens()) {
      	      tokens = itr.nextToken().toString().split("\t");
      	      str = tokens[0].toString().split(" ");
              AdjacentWord wordpair = new AdjacentWord(str[0], str[1]);
              FrequencyCount.set(Double.parseDouble(tokens[1].trim()));
              if(FrequencyCount == null)
                  continue;
              context.write(FrequencyCount, wordpair);
          }
        }
}
