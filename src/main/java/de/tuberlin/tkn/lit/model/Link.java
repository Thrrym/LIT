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
	
	private Link(LinkBuilder builder) {
		this.href = builder.href;
		this.id = builder.id;
		this.name = builder.name;
		this.hreflang = builder.hreflang;
		this.mediaType = builder.mediaType;
		this.rel = builder.rel;
		this.height = builder.height;
		this.width = builder.width;
	}
	
	public String getHref() {
		return href;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getHreflang() {
		return hreflang;
	}

	public String getMediaType() {
		return mediaType;
	}

	public String[] getRel() {
		return rel;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	
	public static class LinkBuilder {
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
		
		public LinkBuilder(String href) {
			this.href = href;
		}

		public LinkBuilder setId(String id) {
			this.id = id;
			return this;
		}

		public LinkBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public LinkBuilder setHreflang(String hreflang) {
			this.hreflang = hreflang;
			return this;
		}

		public LinkBuilder setMediaType(String mediaType) {
			this.mediaType = mediaType;
			return this;
		}

		public LinkBuilder setRel(String[] rel) {
			this.rel = rel;
			return this;
		}

		public LinkBuilder setHeight(int height) {
			this.height = height;
			return this;
		}

		public LinkBuilder setWidth(int width) {
			this.width = width;
			return this;
		}
		
		public Link build() {
			return new Link(this);
		}
	}
}
