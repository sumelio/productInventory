package net.telintel.inventory.test.file.model;

public class File {

	public enum Status {
		OK, ERROR, PROCESS
	};

	private String url;
	private String thumbnailUrl;
	private String name ;
	private String nameNew;
	private String type;
	private String contentType;
	private long size;
	private String deleteUrl;
	private String deleteType;
	private Status status; // error Filetype not allowed
	private String detail;
	private String path;
	private int lines;

	public File(String url, String name, String nameNew, String type, long size) {
		super();
		this.url = url;
		this.path = url;
		this.name = name;
		this.nameNew = nameNew;
		this.type = type;
		this.contentType = type;
		this.size = size;
		this.detail = "New";
	}

	public File(String detail, Status error) {
		this.detail = detail;
		this.status = error;
	}

	public File() {

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameNew() {
		return nameNew;
	}

	public void setNameNew(String nameNew) {
		this.nameNew = nameNew;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getDeleteUrl() {
		return deleteUrl;
	}

	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}

	public String getDeleteType() {
		return deleteType;
	}

	public void setDeleteType(String deleteType) {
		this.deleteType = deleteType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

 

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

 
 
}
