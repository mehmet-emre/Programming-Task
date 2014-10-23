package programmingTask;

public class Track {
	
	private String title;
	private String id;
	private boolean isDownloadable;
	private String downloadUrl;
	private String location;
	
	public Track(){
		super();
	}
	
	public Track(String title, String id, boolean isDownloadable, String downloadUrl,
			String location) {
		super();
		this.title = title;
		this.id = id;
		this.isDownloadable = isDownloadable;
		this.downloadUrl = downloadUrl;
		this.location = location;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isDownloadable() {
		return isDownloadable;
	}
	public void setDownloadable(boolean isDownloadable) {
		this.isDownloadable = isDownloadable;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
