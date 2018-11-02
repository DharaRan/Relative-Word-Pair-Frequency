
//Developed: Dhara Rana
//10/30/18


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Mapper1 extends Mapper<LongWritable, Text, AdjacentWord, IntWritable> {
   
	private AdjacentWord wordPair = new AdjacentWord();
    private IntWritable ONE = new IntWritable(1);
    private IntWritable wordFrequency = new IntWritable();
    public static String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or","org", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero","s","t","w","www","shouldn","wouldn","wasn","hasn","didn","doesn"};
	public static Set<String> stopWordSet = new HashSet<String>(Arrays.asList(stopwords));
	
	public static boolean isStopword(String word) {
		if(word.length() <= 2) { 
			return true;
		}
		if(word.charAt(0) >= '0' && word.charAt(0) <= '9') { 
			return true;} //remove numbers, "25th", etc
		if(stopWordSet.contains(word)) { 
			return true;
		}else {
			return false;
		}
	}
	public static String cleanWordup(String word) {
		// Return a word that is lowercase; not punctions and ' removed
		String cleanWord;
		cleanWord=word.toLowerCase().replace("'", "").replace(",", "").replaceAll("\\p{Punct}", " "); //replaceAll("\\p{Punct}", "")
		return(cleanWord);
	}
	

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
    	String line =cleanWordup(value.toString());//String[] words = value.toString().split("\\s+");
		String[] words = line.split("\\s+");
        
        Set<String> uniqueWords= new HashSet<>();
        
        if (words.length > 1) {
        	
        	// Find unique words and return the user frequency
			for(String w:words) {
				if(w.matches("^[A-Za-z]+$")) {
					//String cleanW=cleanWordup(w);					
					if(w.matches("^[A-Za-z]+$")) {
						if(!isStopword(w)) {
							uniqueWords.add(w);
						}
					 }
				}
			}
			
			
			// Count the frequency of each individual word
            int countW =0;
            for(String word:uniqueWords) {
            	wordPair.setWord(word);
	            for(int k=0;k<words.length;k++) {
	            	if( word.toLowerCase().equals(words[k]) ) { //cleanWordup(words[k])
	            		countW=countW+1;
	            	}
	            }
	            //System.out.println("Individual word Context: "+word+" "+countW);
	            wordPair.setNeighbor("*");
                wordFrequency.set(countW);
                context.write(wordPair, wordFrequency);        
	            countW=0;
            }
            
            
            for (int i = 0; i < words.length-1; i++) {
            	
            	String first_word=cleanWordup(words[i]);
				String neighbor=cleanWordup(words[i+1]);
				

	            // Find Word Pairs
                if(first_word.matches("^[A-Za-z]+$") && neighbor.matches("^[A-Za-z]+$")) {
                    if(isStopword(first_word) ||isStopword(neighbor)) {
                    	continue;
                    }else {
	                    //System.out.println("Word: "+words[i]);
	                    if(words[i].equals("")){
	                        continue;
	                    }
                    }
                
	                wordPair.setWord(first_word);
	
	                //Create context for word pair => only word next it it        
                    wordPair.setNeighbor(neighbor);
                    context.write(wordPair, ONE);
	                //System.out.println("Word: " +first_word);
	                //System.out.println("Hash Code: "+wordPair.getWord().hashCode());
	                //System.out.println("Neighbor is: " +neighbor);
	                //System.out.println("Context: "+wordPair.getWord()+" " +wordPair.getNeighbor()+" 1");
	                //System.out.println("");

           
                }// end of if regEx
            } //end of for loop to count word pair freq
        }//End of words length checker
        
        
        
    }
}

