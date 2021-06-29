package tusspringbootserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import org.springframework.stereotype.Service;
import tusspringbootserver.model.File;
import tusspringbootserver.repository.FileRepository;

@Service
public class FileService {

	@Autowired
	FileRepository fileRepository;

	public Optional<File> getFileByFileId(long fileId) {

		return fileRepository.findById(fileId);


	}

	public void saveOrUpdate(File file) {
		fileRepository.save(file);
	}
	
}
