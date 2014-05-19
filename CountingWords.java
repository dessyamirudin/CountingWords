import java.io.*;
import java.util.*;

public class CountingWords {
	
	public static Map<String, Integer> sortByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

            public int compare(Map.Entry<String, Integer> m1, Map.Entry<String, Integer> m2) {
                return (m2.getValue()).compareTo(m1.getValue());
            }
        });

        Map<String, Integer> result = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

	public static String[] all_words(String filename) throws IOException{
		ArrayList<String> word=new ArrayList<String>();
		FileReader file=new FileReader(filename);
		BufferedReader in=new BufferedReader(file);
		while(in.ready()){
			String line=in.readLine();
			//System.out.println(line);
			StringTokenizer token=new StringTokenizer(line);

			while(token.hasMoreTokens()){
				String next_token=token.nextToken();
				next_token=next_token.replaceAll("[^a-zA-Z]","");
				//int last_char=next_token.length();
				//if (!next_token.matches("^.*[^a-zA-Z0-9 ].*$")){
				word.add(next_token.toLowerCase());	
				//}else if(next_token.charAt(last_char).matches()){

			}
		}
		//System.out.print(word);
		in.close();

		String[] words=new String[word.size()];
		words=word.toArray(words);
		//for (String s:words){
		//System.out.print(s);
		//}
		return words;

	}

	public static String[] non_stop_word(String filename) throws IOException{
		FileReader stop_file = new FileReader("stop_words.txt");
		BufferedReader in=new BufferedReader(stop_file);
		String line=in.readLine();
		String delimiter="\\,";
		String[] stop_words;
		stop_words=line.split(delimiter);
		String all_word[];
		all_word=all_words(filename);
		ArrayList<String> not_stop_word=new ArrayList<String>();
		boolean exist;
		for(String s:all_word){
			exist=false;
			for (int i=0;i<stop_words.length;i++){
				if(s.equals(stop_words[i])){
					exist=true;
				}
			}
			if (exist==false){
				not_stop_word.add(s);
			}

		}

		String[] all_non_stop_words=new String[not_stop_word.size()];
		all_non_stop_words=not_stop_word.toArray(all_non_stop_words);
		
		in.close();

		return all_non_stop_words;
	}

	public static void count_and_sort(String filename) throws IOException{
		Map<String,Integer> freqs=new HashMap<String,Integer>();
		int ind=1;
		String[] not_stop_words;
		not_stop_words=non_stop_word(filename);

		for (String ns:not_stop_words){
			if (freqs.get(ns)!=null){
				int val=freqs.get(ns)+1;
				freqs.put(ns,val);
			}else{
				freqs.put(ns,1);
			}
			
			Map<String,Integer> sorted_freqs=new HashMap<String,Integer>();
			

			if(ind%5000==0){
				sorted_freqs=sortByValue(freqs);
				System.out.println("------------------------");
				ArrayList<String> keys = new ArrayList<String>(sorted_freqs.keySet());
				for (int i = 0; i <25; i++) {
					Object obj = keys.get(i);
					System.out.println(obj+" - "+sorted_freqs.get(obj));
				}

			}
			ind++;
		}

	}

	public static void main(String args[]) throws Exception{
		//String[] out;
		String filename;
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the filename (PrideAndPrejudice)= ");
		filename=scan.next();
		//out=all_words(filename);
		//System.out.println(out[0]);
		//for (String s:out){
		//System.out.println(s);
		//}
		count_and_sort(filename);
		scan.close();

	}

}
