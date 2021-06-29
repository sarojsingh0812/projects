package tusspringbootserver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;
import tusspringbootserver.model.File;
import tusspringbootserver.repository.FileRepository;

@Controller
@CrossOrigin(exposedHeaders = { "Location", "Upload-Offset" })

public class UploadController {

	private final TusFileUploadService tusFileUploadService;

	private final Path uploadDirectory;

	private final Path tusUploadDirectory;
	
	@Autowired  
	FileRepository fileRepository;
	
	

	public UploadController(TusFileUploadService tusFileUploadService, AppProperties appProperties) {
		this.tusFileUploadService = tusFileUploadService;

		this.uploadDirectory = Paths.get(appProperties.getAppUploadDirectory());
		try {
			Files.createDirectories(this.uploadDirectory);
		} catch (IOException e) {
			TusSpringBootServerDemoApplication.logger.error("create upload directory", e);
		}

		this.tusUploadDirectory = Paths.get(appProperties.getTusUploadDirectory());
	}

	@RequestMapping(value = { "/upload", "/upload/**" }, method = { RequestMethod.POST, RequestMethod.PATCH,
			RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.GET })
	public void upload(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException {
		this.tusFileUploadService.process(servletRequest, servletResponse);
		
		
		
		TusSpringBootServerDemoApplication.logger.info(servletRequest.getMethod());
	

		String uploadURI = servletRequest.getRequestURI();
		
		TusSpringBootServerDemoApplication.logger.info("test ::" + uploadURI);

		
		UploadInfo uploadInfo = null;
		try {
			uploadInfo = this.tusFileUploadService.getUploadInfo(uploadURI);
			
			
		} catch (IOException | TusException e) {
			TusSpringBootServerDemoApplication.logger.error("get upload info", e);
		}

		if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
			try (InputStream is = this.tusFileUploadService.getUploadedBytes(uploadURI)) {
				Path output = this.uploadDirectory.resolve(uploadInfo.getFileName());
				Files.copy(is, output, StandardCopyOption.REPLACE_EXISTING);
				
				Map<String,String> metaData = uploadInfo.getMetadata();
				
				File file = new File();
				
				file.setFileType(uploadInfo.getFileMimeType());
				file.setFileLocationURL(this.uploadDirectory.toString());
				file.setFileName(uploadInfo.getFileName());
				
				if(metaData.containsKey("submitterId")) {
					TusSpringBootServerDemoApplication.logger.info("submitterId ::" + metaData.get("submitterId"));
					file.setSubmitterId( new Integer(metaData.get("submitterId")));
				}
				
				fileRepository.save(file);
				
				
				
			} catch (IOException | TusException e) {
				TusSpringBootServerDemoApplication.logger.error("get uploaded bytes", e);
			}

			/*
			try {
				this.tusFileUploadService.deleteUpload(uploadURI);
			} catch (IOException | TusException e) {
				TusSpringBootServerDemoApplication.logger.error("delete upload", e);
			}
			*/
		}
		
		
	}


	
	@Scheduled(fixedDelayString = "PT24H")
	private void cleanup() {
		Path locksDir = this.tusUploadDirectory.resolve("locks");
		if (Files.exists(locksDir)) {
			try {
				this.tusFileUploadService.cleanup();
			} catch (IOException e) {
				TusSpringBootServerDemoApplication.logger.error("error during cleanup", e);
			}
		}
	}

}