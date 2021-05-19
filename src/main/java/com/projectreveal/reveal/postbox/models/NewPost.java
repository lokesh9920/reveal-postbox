package com.projectreveal.reveal.postbox.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class NewPost {

	private String postCreator;
	private long groupId;
	private String postContent;
}
