package tusspringbootserver.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tusspringbootserver.service.FileStorageService;
import tusspringbootserver.model.File;
import tusspringbootserver.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	FileService fileService;
	
	 @Autowired
	 private FileStorageService fileStorageService;

	@GetMapping("/{id}")
	private ResponseEntity<File> getFile(@PathVariable("id") long id) {
		logger.info("inside the get file method in controller" + id);

		Optional<File> fileData = fileService.getFileByFileId(id);
		

		if (fileData.isPresent()) {
			return new ResponseEntity<>(fileData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	 @GetMapping("/downloadFile/{fileId}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId, HttpServletRequest request) {
		 
		 Optional<File> fileData = fileService.getFileByFileId(fileId);
		 
		 if (fileData.isPresent()) {
			 
			 File fileDetails = fileData.get();
			 
			 // Load file as Resource
		        Resource resource = fileStorageService.loadFileAsResource(fileDetails.getFileName());

		        // Try to determine file's content type
		        String contentType = null;
		        try {
		            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		        } catch (IOException ex) {
		            logger.info("Could not determine file type.");
		        }

		        // Fallback to the default content type if type could not be determined
		        if(contentType == null) {
		            contentType = "application/octet-stream";
		        }

		        return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(contentType))
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
		                .body(resource);
			 
				
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		 
	       
	    }

}
