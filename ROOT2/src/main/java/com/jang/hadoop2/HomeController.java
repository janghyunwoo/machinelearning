package com.jang.hadoop2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.cf.taste.hadoop.item.RecommenderJob;
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
		@RequestMapping(value = "/mahout", method = RequestMethod.GET)
		public String muhout(Locale locale, Model model) {
			
			// 기존 디랙토리가 존재하면 애러가 떠서, 있다면 제거해준다.
			Path deleteFilePath = new Path("temp/");
			Path deleteOutputFilePath = new Path("/mahoutresult");

			FileSystem outhdfs;
			try {
				outhdfs = FileSystem.get(deleteFilePath.toUri(), hdConf);

				if(outhdfs.exists(deleteFilePath)) {
					outhdfs.delete(deleteFilePath, true);
				}
				if(outhdfs.exists(deleteOutputFilePath)) {
					outhdfs.delete(deleteOutputFilePath, true);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// 하둡 서버의 정보 set
			Configuration conf = hdConf;
			conf.set("fs.default.name", "hdfs://192.168.0.10:9000");
			
			// 아래 주석은 yarn, mapreduce 할 때 필요한듯??
			// conf.set("hadoop.job.user", "hadoop");
			// conf.set("mapreduce.framework.name", "yarn");
			// conf.set("mapreduce.jobtracker.address", "192.168.1.101:9001");
			/*
			 * conf.set("yarn.resourcemanager.hostname", "192.168.1.101");
			 * conf.set("yarn.resourcemanager.admin.address", "192.168.1.101:8033");
			 * conf.set("yarn.resourcemanager.address", "192.168.1.101:8032");
			 * conf.set("yarn.resourcemanager.resource-tracker.address",
			 * "192.168.1.101:8031"); conf.set("yarn.resourcemanager.scheduler.address",
			 * "192.168.1.101:8030");
			 */

			/*
			 * -i : input path (미리 파일을 hdfs에 업로드 해둔다.)
			 * -o : output path 
			 * -s : 추천 알고리즘
			 * */
			String[] str = { 
					"-i", "hdfs://192.168.0.10:9000/userrating.csv",
					"-o", "hdfs://192.168.0.10:9000/mahoutresult",
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
		}
		
		//ML Test
		@RequestMapping(value = "/mltest", method = RequestMethod.GET)
		public String home2(Locale locale, Model model) {
			try {
				logger.info("환경변수:"+System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
				
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
//		        "name", String.format("projects/%s/models/%s/versions/%s", projectId, modelId, versionId));
						"name", String.format("projects/%s/models/%s", projectId, modelId));
				
				GenericUrl url =
						new GenericUrl(UriTemplate.expand(api.getBaseUrl() + method.getPath(), param, true));
				logger.info(url+"");
				
				String contentType = "application/json";
				
				File requestBodyFile = ResourceUtils.getFile(this.getClass().getResource("/input.txt"));
				HttpContent content = new FileContent(contentType, requestBodyFile);
				logger.info(content.getLength()+"");
				logger.info(requestBodyFile.getAbsolutePath());
				
				GoogleCredential credential = GoogleCredential.getApplicationDefault();
				logger.info("환경:"+credential);
//		    GoogleCredential credential = GoogleCredential.getApplicationDefault(httpTransport, jsonFactory);
				HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
				HttpRequest request = requestFactory.buildRequest(method.getHttpMethod(), url, content);
				
				String response = request.execute().parseAsString();
				logger.info(response);
				
				
				// 웹 페이지 출력
				model.addAttribute("serverTime", response);
				
				
				logger.info("끝", response);
				
			}catch (Exception e) {
				logger.info("애러", e);
				// TODO: handle exception
			}
			return "home";
		}
		
}
