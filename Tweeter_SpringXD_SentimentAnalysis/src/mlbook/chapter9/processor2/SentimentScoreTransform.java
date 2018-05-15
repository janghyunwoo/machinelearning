package mlbook.chapter9.processor2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.integration.transformer.MessageTransformationException;


public class SentimentScoreTransform {
	private Set<String> poswords = new HashSet<String>();
	private Set<String> negwords = new HashSet<String>();

	public SentimentScoreTransform() {
		// TODO Auto-generated constructor stub
		// 아래 두 파일은 긍정적인 단어와 부정적인 단어(영어)를 가지는 text 파일
		loadWords("/home/jang/spring-xd-1.3.1.RELEASE/positive-words.txt",poswords);
		loadWords("/home/jang/spring-xd-1.3.1.RELEASE/negative-words.txt",negwords);
	}
	
	private ObjectMapper mapper = new ObjectMapper();

	// Spring XD에서 호출하는 메소드 
	public String transform(String payload) {
		try {
			StringBuffer sb = new StringBuffer();
			
			Map<String, Object> tweet = mapper.readValue(payload, new TypeReference<Map<String, Object>>(){});
			sb.append(tweet.get("created_at").toString());
			sb.append("|");
			sb.append(tweet.get("text").toString());
			sb.append("|");
			sb.append(scoreTweet(tweet.get("text").toString()));
			return sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
			throw new MessageTransformationException("[MLBook] - Cannot work on this tweet:"+e.getMessage(),e); 
		}
	}
	
	//점수 메기는 메소드
	private int scoreTweet(String tweet) {
		// TODO Auto-generated method stub
		int score = 0;
		StringTokenizer st = new StringTokenizer(cleanTweet(tweet));
		String thisToken;
		while(st.hasMoreTokens()) {
			thisToken = st.nextToken();
			if(poswords.contains(thisToken)) {
				score+=1;
			}else if (negwords.contains(thisToken)) {
				score-=1;
			}
		}
		return score;
	}

	//파일을 받는 메소드
	private void loadWords(String filepath, Set<String> set) {
		// TODO Auto-generated method stub
		try {
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			String str;
			while((str = in.readLine())!=null) {
				set.add(str);
			}
			in.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//테그#,링크@ 제거/ 대문자를 소문자로 바꾸는 메소드 
	private String cleanTweet(String tweet) {
		tweet = tweet.replaceAll("#", "");
		tweet = tweet.replaceAll("@", "");
		return tweet.toLowerCase();
	}
	
	
}
