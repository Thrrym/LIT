package de.tuberlin.tkn.lit.model;

import java.util.HashMap;

public class Object {
    // All optional
	private String id;
	private String type;
	
	// TODO: Create missing classes
	private Attachment[] attachment;
	private AttributedTo[] attributedTo;
	private Audience[] audience;
	private String content;
	private HashMap<String, String> contentMap;
	private String context;
	private String name;
	private HashMap<String, String> nameMap;
	private String startTime;
	private String endTime;
	private Generator generator;
	private Icon[] icon;
	private Image[] image;
	private InReplyTo[] inReplyTo;
	private Location[] location;
	private Preview preview;
	private String published;
	private Collection replies;
	private String summary;
	private HashMap<String, String> summaryMap;
    private Tag[] tag;
    private String updated;
    private Url[] url;
    private String[] to;
    private String[] bto;
    private String[] cc;
    private String[] bcc;
    private String mediaType;
    private String duration;
    
}