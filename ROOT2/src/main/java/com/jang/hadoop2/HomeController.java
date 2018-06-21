package com.jang.hadoop2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
import java.util.Locale;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.util.ToolRunner;
//import org.apache.mahout.cf.taste.hadoop.item.RecommenderJob;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ConnectionPoolDataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.mapreduce.JobRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UriTemplate;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.discovery.Discovery;
import com.google.api.services.discovery.model.JsonSchema;
import com.google.api.services.discovery.model.RestDescription;
import com.google.api.services.discovery.model.RestMethod;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import java.io.File;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	// 하둡 HDFS 객체
	@Autowired
	private Configuration hdConf;

	// 맵리듀스 처리할 JobRunner 객체
	@Autowired
	private JobRunner wordCountJobRunner;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("접속 성공!!!!!!!!!!!!!! {}.", hdConf);
		String formattedDate = "<br>";
		try {
			// HDFS상 경로 설정
			Path inputfilePath = new Path("/input/subway.csv");
			// 연결 (여기서 inhdfs는 inputfilePath 경로를 기준하는게 아닌 HDFS 자체 파일 시스템을 기준하는 것같음 )
			FileSystem inhdfs = FileSystem.get(inputfilePath.toUri(), hdConf);

			// Job-Runner 호출
			wordCountJobRunner.call();

			// 출력 파일 경로
			Path outputFilePath = new Path("/output/part-r-00000");

			// outputFilePath 경로 상에 파일(part-r-00000)이 존재하면 실행
			if (inhdfs.exists(outputFilePath)) {

				BufferedReader reader = new BufferedReader(new InputStreamReader(inhdfs.open(outputFilePath), "utf-8"));
				String line = null;
				do {
					line = reader.readLine();
					formattedDate += line + "<br>";
					// 로그 출력
					logger.info(line);
				} while (line != null);
			} else {
				logger.info("파일 없음!");
			}

			// 웹 페이지 출력
			model.addAttribute("serverTime", formattedDate);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("끝", hdConf);

		return "home";
	}

	// 유저 기반 추천 시스템
	// http://ip주소:포트번호/mahout 으로 호출
	/*@RequestMapping(value = "/mahout", method = RequestMethod.GET)
	public String muhout(Locale locale, Model model) {

		// 기존 디랙토리가 존재하면 애러가 떠서, 있다면 제거해준다.
		Path deleteFilePath = new Path("temp/");
		Path deleteOutputFilePath = new Path("/mahoutresult");

		FileSystem outhdfs;
		try {
			outhdfs = FileSystem.get(deleteFilePath.toUri(), hdConf);

			if (outhdfs.exists(deleteFilePath)) {
				outhdfs.delete(deleteFilePath, true);
			}
			if (outhdfs.exists(deleteOutputFilePath)) {
				outhdfs.delete(deleteOutputFilePath, true);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 하둡 서버의 정보 set
		Configuration conf = hdConf;
		conf.set("fs.default.name", "hdfs://192.168.0.11:9000");

		// 아래 주석은 yarn, mapreduce 할 때 필요한듯??
		// conf.set("hadoop.job.user", "hadoop");
		// conf.set("mapreduce.framework.name", "yarn");
		// conf.set("mapreduce.jobtracker.address", "192.168.1.101:9001");
		
		 * conf.set("yarn.resourcemanager.hostname", "192.168.1.101");
		 * conf.set("yarn.resourcemanager.admin.address", "192.168.1.101:8033");
		 * conf.set("yarn.resourcemanager.address", "192.168.1.101:8032");
		 * conf.set("yarn.resourcemanager.resource-tracker.address",
		 * "192.168.1.101:8031"); conf.set("yarn.resourcemanager.scheduler.address",
		 * "192.168.1.101:8030");
		 

		
		 * -i : input path (미리 파일을 hdfs에 업로드 해둔다.) -o : output path -s : 추천 알고리즘
		 
		String[] str = { "-i", "hdfs://192.168.0.11:9000/userrating.csv", "-o", "hdfs://192.168.0.11:9000/mahoutresult",
				// "-n","3",
				// "-b","false",

				// mahout自带的相似类列表
				// SIMILARITY_COOCCURRENCE(CooccurrenceCountSimilarity.class),
				// SIMILARITY_LOGLIKELIHOOD(LoglikelihoodSimilarity.class),
				// SIMILARITY_TANIMOTO_COEFFICIENT(TanimotoCoefficientSimilarity.class),
				// SIMILARITY_CITY_BLOCK(CityBlockSimilarity.class),
				// SIMILARITY_COSINE(CityBlockSimilarity.class),
				// SIMILARITY_PEARSON_CORRELATION(CosineSimilarity.class),
				// SIMILARITY_EUCLIDEAN_DISTANCE(EuclideanDistanceSimilarity.class);
				"-s", "SIMILARITY_COOCCURRENCE",

				// "--maxPrefsPerUser","70",
				// "--minPrefsPerUser","2",
				// "--maxPrefsInItemSimilarity","70",
				// "--outputPathForSimilarityMatrix","hdfs://192.168.1.100:9000/data/test_out/mahout_out_CityBlockSimilarity/matrix/rec001",
				// "--tempDir","hdfs://192.168.1.100:9000/data/test_out/mahout_out_CityBlockSimilarity/temp/rec001"
		};

		try {
			// 추천 알고리즘 실행
			ToolRunner.run(conf, new RecommenderJob(), str);

			// 결과 Path
			Path outputFilePath = new Path("/mahoutresult/part-r-00000");
			FileSystem inhdfs = FileSystem.get(outputFilePath.toUri(), hdConf);

			String formattedDate = "<br>";

			// outputFilePath 경로 상에 파일(part-r-00000)이 존재하면 실행
			if (inhdfs.exists(outputFilePath)) {

				BufferedReader reader = new BufferedReader(new InputStreamReader(inhdfs.open(outputFilePath), "utf-8"));
				String line = null;
				do {
					line = reader.readLine();
					formattedDate += line + "<br>";
					// 로그 출력
					logger.info(line);
				} while (line != null);
			} else {
				logger.info("파일 없음!");
			}

			// 웹 페이지 출력
			model.addAttribute("serverTime", formattedDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "home";
	}*/

	// ML Test
	@RequestMapping(value = "/mltest", method = RequestMethod.GET)
	public String home2(Locale locale, Model model) throws Exception {
		System.out.println("sysout 시작");
		logger.info("logger시작");
		logger.info("환경변수:" + System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));

		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		Discovery discovery = new Discovery.Builder(httpTransport, jsonFactory, null).build();

		RestDescription api = discovery.apis().getRest("ml", "v1").execute();
		RestMethod method = api.getResources().get("projects").getMethods().get("predict");

		JsonSchema param = new JsonSchema();
		String projectId = "hypnotic-maker-206205";
		// You should have already deployed a model and a version.
		// For reference, see https://cloud.google.com/ml-engine/docs/deploying-models.
		String modelId = "census";
		String versionId = "YOUR_VERSION_ID";
		param.set(
				// "name", String.format("projects/%s/models/%s/versions/%s", projectId,
				// modelId, versionId));
				"name", String.format("projects/%s/models/%s", projectId, modelId));

		GenericUrl url = new GenericUrl(UriTemplate.expand(api.getBaseUrl() + method.getPath(), param, true));
		logger.info(url + "");

		String contentType = "application/json";
		File requestBodyFile = new File("/home/jang/Downloads/input.txt");
		HttpContent content = new FileContent(contentType, requestBodyFile);
		logger.info(content.getLength() + "");

		GoogleCredential credential = GoogleCredential.getApplicationDefault();
		logger.info(credential + "");
		// GoogleCredential credential =
		// GoogleCredential.getApplicationDefault(httpTransport, jsonFactory);
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
		logger.info(requestFactory + "");
		HttpRequest request = requestFactory.buildRequest(method.getHttpMethod(), url, content);
		logger.info(request + "");

		String response = request.execute().parseAsString();
		logger.info(response + "");
		// 웹 페이지 출력
		model.addAttribute("serverTime", response);
		return "home";
	}

	// mahout User Based
	@RequestMapping(value = "/mahoutUser", method = RequestMethod.GET)
	public String home3(Locale locale, Model model) throws Exception {
//		try {
			logger.info("User Based 들어옴!");
			
			File CSVFile = new File("/var/lib/mysql-files/output1.csv");
			if(!CSVFile.exists()) {
			 DBase db = new DBase();
		     Connection conn = db.connect(
		                "jdbc:mysql://jhwoo.hopto.org:3306/db","jang","Wkdgusdn#01");
		     
		    	 db.exportData(conn,"/var/lib/mysql-files/output1.csv");
		    	 logger.info("CSV 생성 완료");
		     }
		     
			
			// Creating data model
//			DataModel datamodel = new FileDataModel(ResourceUtils.getFile(this.getClass().getResource("/u1.csv"))); // data
			DataModel datamodel = new FileDataModel(CSVFile); // data
			logger.info("1."+datamodel);
			// Creating UserSimilarity object.
			// ItemSimilarity usersimilarity = new LogLikelihoodSimilarity(datamodel);
			// UserSimilarity usersimilarity = new LogLikelihoodSimilarity(datamodel);
			UserSimilarity usersimilarity = new PearsonCorrelationSimilarity(datamodel);
			logger.info("2."+usersimilarity);
			// Creating UserNeighbourHHood object.
			UserNeighborhood userneighborhood = new ThresholdUserNeighborhood(0.1, usersimilarity, datamodel);
			logger.info("3."+userneighborhood);
			// Create UserRecomender
			// Recommender recommender = new
			// GenericBooleanPrefItemBasedRecommender(datamodel, usersimilarity);
			UserBasedRecommender recommender = new GenericUserBasedRecommender(datamodel, userneighborhood,
					usersimilarity);
			// GenericItemBasedRecommender recommender = new
			// GenericItemBasedRecommender(datamodel, usersimilarity);
			logger.info("4."+recommender);
			int x = 1;
			for (LongPrimitiveIterator users = datamodel.getUserIDs(); users.hasNext();) {
				 long userID = users.nextLong();

				List<RecommendedItem> recommendations = recommender.recommend(userID, 10);
				logger.info("5."+recommendations);
				for (RecommendedItem recommendation : recommendations) {
					logger.info("id:" + userID + " 영화:" + recommendation.getItemID() + " 추천 점수: "
							+ recommendation.getValue());
				}

				if (++x > 5)
					break;
			}

//		} catch (Exception e) {
//			logger.info(e+"");
//		}

		return "home";
	}

	// mahout Item Based
	@RequestMapping(value = "/mahoutItem", method = RequestMethod.GET)
	public String home4(Locale locale, Model model) throws Exception {
		logger.info("Item Based 들어옴!");
		
		MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
//		dataSource.setServerName("jhwoo.hopto.org");
		dataSource.setServerName("192.168.0.11");
		dataSource.setDatabaseName("db");
		dataSource.setUser("jang");
		dataSource.setPassword("Wkdgusdn#01");
		dataSource.setCachePreparedStatements(true);
		dataSource.setCachePrepStmts(true);
		dataSource.setCacheResultSetMetadata(true);
		dataSource.setAlwaysSendSetIsolation(false);
		dataSource.setElideSetAutoCommits(true);
		/*
		 * MysqlDataSource dataSource = new MysqlDataSource();
		 * dataSource.setServerName("jhwoo.hopto.org"); dataSource.setUser("jang");
		 * dataSource.setPassword("Wkdgusdn#01"); dataSource.setDatabaseName("db");
		 */
		// ConnectionPoolDataSource aa = new ConnectionPoolDataSource(dataSource);
		JDBCDataModel model1 = new MySQLJDBCDataModel(new ConnectionPoolDataSource(dataSource), "rating", "userid",
				"movieid", "rating", null);
		
		
//		try {
		// BasicConfigurator.configure();
//		DataModel model1 = new FileDataModel(new File("/home/jang/hadoop/hadoop-3.1.0/u1.csv"));
//		DataModel model1 = new FileDataModel(ResourceUtils.getFile(this.getClass().getResource("/u1.csv")));
		logger.info("1."+model1);
		// CooccurrenceCountSimilarity aa =new CooccurrenceCountSimilarity();

		// UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		ItemSimilarity similarity = new PearsonCorrelationSimilarity(model1);
		logger.info("2."+similarity);
		ItemBasedRecommender recommender = new GenericItemBasedRecommender(model1, similarity);
		logger.info("3."+recommender);
		// getItemIDs 나 getUserIDs는 단지 유저나 아이템의 id 값을 가져오는 것에 불과
		// 연산하는데 영향을 미치지 않는다.
		int x = 1;
		String response="";
		for (LongPrimitiveIterator items = model1.getItemIDs(); items.hasNext();) {
			long itemID = items.nextLong();

			// recommend() 메서드는 사용자에게 재품을 추천하는 메소드
			// List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemID,
			// 10);
			List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemID, 10);
			logger.info("4."+recommendations);
			for (RecommendedItem recommendation : recommendations) {
				response+=itemID + " 비슷영화id:" + recommendation.getItemID() + " 연관점수 " + recommendation.getValue() + "<br>";
				logger.info(
						itemID + " 비슷영화id:" + recommendation.getItemID() + " 연관점수 " + recommendation.getValue() + "");
			}

			if (++x > 1)
				break;
		}
		model.addAttribute("serverTime", response);
//		} catch (Exception e) {
//			logger.info(e+"");
//		}
		
		return "home";
	}
	
	

}

class DBase {
    public DBase() {
    }
     
    public Connection connect(String db_connect_str, 
            String db_userid, String db_password) {
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = (Connection) DriverManager.getConnection(db_connect_str,
                    db_userid, db_password);
             
        } catch(Exception e) {
            e.printStackTrace();
            conn = null;
        }
        return conn;
    }
     
    public void exportData(Connection conn,String filename) {
        Statement stmt;
        String query;
        try {
            stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
             
            //For comma separated file
            query = "SELECT userid,movieid,rating into OUTFILE '"+filename+
                    "' FIELDS TERMINATED BY ',' FROM rating";
            stmt.executeQuery(query);
             
        } catch(Exception e) {
            e.printStackTrace();
            stmt = null;
        }
    }
}
