package com.projectreveal.reveal.postbox.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@ToString
public class Post {

	
	public long id;
	public String postContent;
	public long likesCount;
	public long dislikesCount;
	public long timeStamp;
	
	
}
