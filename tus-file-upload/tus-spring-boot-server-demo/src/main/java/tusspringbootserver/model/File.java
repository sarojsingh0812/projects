package tusspringbootserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "file")

public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long fileId; // fileId is primary key
	
	
	@Column
	private String fileName;

	@Column
	private String fileType;

	@Column
	private String fileLocationURL;

	@Column
	private int submitterId;

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	
	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileLocationURL() {
		return fileLocationURL;
	}

	public void setFileLocationURL(String fileLocationURL) {
		this.fileLocationURL = fileLocationURL;
	}

	public int getSubmitterId() {
		return submitterId;
	}

	public void setSubmitterId(int submitterId) {
		this.submitterId = submitterId;
	}

}
