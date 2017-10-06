package net.telintel.inventory.test.file.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.telintel.inventory.test.file.util.FileUtil;

/**
 * THe entity Process encapsulate the information about the a file process
 * 
 * @author Freddy Lemus
 *
 */
public class ProcessFile {

	public enum Status {
		INIT_PROCESSING, DATA_BASE_OK, DATA_BASE_ERROR, READ_FILE_ERROR, READ_FILE_OK,READ_FILE_PROCESSING, DATA_BASE_PROCESSING, NOFITY_SENT_OK, NOFITY_SENT_ERROR, NOFITY_SENT_PROCESSING
	};

	private int id;
	private File file = new File();
	private Thread threal;
	private Date start;
	private Date end;
	private Status status;
	private String message;
	private String nameThreal;
	private int totalStock;
	private int linesFile;
	private String startTime;
	private String endTime;

	public ProcessFile(File file_, Thread currentThread) {
		this.file = file_;
		this.threal = currentThread;
		this.start = new Date();
		this.status = Status.INIT_PROCESSING;

	}

	public ProcessFile() {

	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Thread getThreal() {
		return threal;
	}

	public void setThreal(Thread threal) {
		this.threal = threal;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() { 
		return this.end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameThreal() {
		return nameThreal;
	}

	public void setNameThreal(String nameThreal) {
		this.nameThreal = nameThreal;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private int getTotalStock() {
		// TODO Auto-generated method stub
		return this.totalStock;
	}

	public void setTotalStock(int totalStock) {
		this.totalStock = totalStock;
	}

	public int getLinesFile() {
		return linesFile;
	}

	public void setLinesFile(int linesFile) {
		this.linesFile = linesFile;
	}
	
	  @JsonIgnore
	    @JsonProperty(value = "isOKReadProduct")
	public boolean isOKReadProduct() {
		this.setEnd(new Date());
		if (this.getFile().getStatus().equals(File.Status.OK)) {
			this.setStatus(ProcessFile.Status.READ_FILE_OK);
			this.message = "OK";
			return true;
		} else {
			this.setStatus(ProcessFile.Status.READ_FILE_ERROR);
			this.message = "Error reading file.";
			return false;
		}

	}

	public String getMessage() {
		return this.message;
	}

	  @JsonIgnore
	    @JsonProperty(value = "isOKInsertDBProduct")
	public boolean isOKInsertDBProduct() {
		this.setEnd(new Date());
		if (this.getStatus().equals(ProcessFile.Status.DATA_BASE_OK)) {
			this.message = "OK";
			return true;
		} else {
			this.setStatus(ProcessFile.Status.DATA_BASE_ERROR);
			this.message = "Error register information in BD.";
			return false;
		}
	}

	  
	  
	  
	public String getStartTime() {
		String endTime = "";
		if(this.getStart() != null) {
			 endTime = FileUtil.simpleDateFormat.format(this.getStart()) ;
		}
		return endTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		String endTime = "";
		if(this.getEnd() != null) {
			 endTime = FileUtil.simpleDateFormat.format(this.getEnd()) ;
		}
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * Casting Json message
	 * 
	 * @param processFile
	 * @return
	 */
	  
	  @JsonIgnore
	    @JsonProperty(value = "getMessageFormatJson")	  
	public String getMessageFormatJson() {
		return "{\"message\":\" [batch]" + this.getFile().getLines() + "," + this.getFile().getNameNew() + ","
				+ this.getTotalStock() + "\",\"id\":" + System.currentTimeMillis() + ",\"count\": "
				+ this.getTotalStock() + "  ,\"category\":null,\"product\":null,\"time\":" + System.currentTimeMillis()
				+ "}";
	}

}
