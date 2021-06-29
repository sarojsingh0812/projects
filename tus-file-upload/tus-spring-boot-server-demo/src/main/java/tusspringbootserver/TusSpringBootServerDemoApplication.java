package tusspringbootserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import me.desair.tus.server.TusFileUploadService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class TusSpringBootServerDemoApplication {
	
	 public static final Logger logger = LoggerFactory.getLogger("tusspringbootserver");

	public static void main(String[] args) {
		SpringApplication.run(TusSpringBootServerDemoApplication.class, args);
	}
	

	  @Bean
	  public TusFileUploadService tusFileUploadService(AppProperties appProperties) {
	    return new TusFileUploadService().withStoragePath(appProperties.getTusUploadDirectory())
	        .withUploadURI("/upload");
	  }
}
