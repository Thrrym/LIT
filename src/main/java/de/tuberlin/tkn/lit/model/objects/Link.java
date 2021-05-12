package de.tuberlin.tkn.lit.model;

public class Link {
	// Required
	private String href;
	
	// Optional
	private String id;
	private String name;
	private String hreflang;
	private String mediaType;
	private String[] rel;
	private int height;
	private int width;
	
	public Link() {
	}
	
	public String getHref() {
		return href;
	}
	
        public void setHref(String href) {
		this.href = href;
	}
	
        public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHreflang() {
		return hreflang;
	}
	public void setHreflang(String hreflang) {
		this.hreflang = hreflang;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String[] getRel() {
		return rel;
	}
	public void setRel(String[] rel) {
		this.rel = rel;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
}
