package de.tuberlin.tkn.lit.model.objects;

import de.tuberlin.tkn.lit.model.ActivityPubObject;

public class Link extends ActivityPubObject {
	private static final String type = "Link";

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

	public String getType() {
		return type;
	}
}
