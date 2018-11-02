//Developed: Dhara Rana
//10/30/18

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

public class Relative_Freq_MainDriver {

    public static void main(String[] args) throws IOException,InterruptedException,ClassNotFoundException {
    	//if (args.length <= 2) {
		//	System.out.println("Error: hadoop jar wikiRelativeFreq.jar  [inputFileName] [tempFilePath] [outputFilePath]");
		//	System.exit(-1);
		//}
        //1st stage Mapreduce job
        Job job = Job.getInstance(new Configuration());
        job.setJarByClass(Relative_Freq_MainDriver.class);
        job.setJobName("Relative_Freq_MainDriver");

        // Filepaths being used
        Path inputFilePath = new Path(args[0]);//data path
        Path tempFilePath = new Path(args[1]);// word and word pair freq
        Path tempFilePath2 = new Path(args[2]);// top 100 words
        Path tempFilePath3 = new Path(args[3]);// All RF words
        Path outputFilePath = new Path(args[4]);// Top 100 relative frequencies
        
        FileInputFormat.addInputPath(job, inputFilePath);
        FileOutputFormat.setOutputPath(job, tempFilePath);

        job.setMapperClass(Mapper1.class);
        job.setReducerClass(Reducer1.class);
       // job.setCombinerClass(AdjacentWordReducer.class);
       // job.setPartitionerClass(AdjacentWordPartitioner.class);
        //job.setNumReduceTasks(3);

        job.setOutputKeyClass(AdjacentWord.class); // This is the output of the mapper
        job.setOutputValueClass(IntWritable.class); // this is the output of mapper
		job.waitForCompletion(true);
		//System.exit(job.waitForCompletion(true) ? 0 : 1);
        
//2nd stage Mapreduce job       
        Job job2 = Job.getInstance(new Configuration());
        job2.setJarByClass(Relative_Freq_MainDriver.class);
        job2.setJobName("Relative_Freq_MainDriver");

        job2.setSortComparatorClass(DescendingKeyComparator.class);
        FileInputFormat.addInputPath(job2, tempFilePath);
        FileOutputFormat.setOutputPath(job2, tempFilePath2);

        job2.setMapperClass(Mapper2.class);
        job2.setReducerClass(Reducer2.class);
       // job2.setCombinerClass(AdjacentWordReducer.class);
        job2.setPartitionerClass(FlagWordPartitioner.class);
        job2.setNumReduceTasks(2);
        //job2.setNumReduceTasks(1);

        job2.setOutputKeyClass(DoubleWritable.class);
        job2.setOutputValueClass(AdjacentWord.class);
        job2.waitForCompletion(true);
        
        
//3rd stage Mapreduce job       
        Job job3 = Job.getInstance(new Configuration());
        job3.setJarByClass(Relative_Freq_MainDriver.class);
        job3.setJobName("Relative_Freq_MainDriver");

        //job3.setSortComparatorClass(DescendingKeyComparator.class);
        FileInputFormat.addInputPath(job3, tempFilePath2);
        FileOutputFormat.setOutputPath(job3, tempFilePath3);

        job3.setMapperClass(Mapper3.class);
        job3.setReducerClass(Reducer3.class);
       // job3.setCombinerClass(AdjacentWordReducer.class);
        job3.setPartitionerClass(WordPairPartitioner.class);
        job3.setNumReduceTasks(3);

        job3.setOutputKeyClass(WordPair.class); 
        job3.setOutputValueClass(DoubleWritable.class);
        job3.waitForCompletion(true);

        
//4th stage Mapreduce job       
        Job job4 = Job.getInstance(new Configuration());
        job4.setJarByClass(Relative_Freq_MainDriver.class);
        job4.setJobName("Relative_Freq_MainDriver");

        job4.setSortComparatorClass(DescendingKeyComparator.class);
        FileInputFormat.addInputPath(job4, tempFilePath3);
        FileOutputFormat.setOutputPath(job4, outputFilePath);

        job4.setMapperClass(Mapper4.class);
        job4.setReducerClass(Reducer4.class);
        job4.setNumReduceTasks(1);

        job4.setOutputKeyClass(DoubleWritable.class);
        job4.setOutputValueClass(WordPair.class);
        job4.waitForCompletion(true);
        
       
        System.exit(job4.waitForCompletion(true) ? 0 : 1);
    }
}
