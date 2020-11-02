package com.projectreveal.reveal.postbox.service;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectreveal.reveal.postbox.constants.LoggingConstants;
import com.projectreveal.reveal.postbox.exceptionhandler.DatabaseException;
import com.projectreveal.reveal.postbox.exceptionhandler.UnservableRequest;
import com.projectreveal.reveal.postbox.mapper.PostsMapper;
import com.projectreveal.reveal.postbox.models.NewPost;
import com.projectreveal.reveal.postbox.models.Post;

@Service
public class PostService {
	
	@Autowired
	PostsMapper postsMapper;
	
	@Value("${postbox.max.posts.per.request:0}")
	int maxPostsPerRequest;
	
	private final Logger logger = LogManager.getLogger(PostService.class);


	public ArrayList<Post> fetchPosts(int groupId, Long timeStamp, int numberOfPosts){
		
		ArrayList<Post> postsList = null;
		
		if(numberOfPosts < 0 || numberOfPosts > maxPostsPerRequest)
				throw new UnservableRequest("Number of posts requested: " + numberOfPosts + " is either too high or too low");
		
		try{
			postsList = postsMapper.fetchPosts(groupId, timeStamp, numberOfPosts);
			return postsList;
		}catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
		
	}
	
	public Post addNewPost(NewPost newPost) {
		
		Long currentTimeStamp = System.currentTimeMillis();
		
		Post post = new Post();
		//This post is passed into the mapper function just to retrieve the id created by the insert statement.
		postsMapper.addNewPost(newPost.getPostContent(),newPost.getGroupId(),newPost.getPostCreator(), currentTimeStamp,post);	//Timestamp is set to currentTimeMillis
		
		logger.info(LoggingConstants.NEW_POST_UPDATE, currentTimeStamp, newPost.getPostCreator(),newPost.getGroupId());
		post = fetchPostById(post.getId());
		
		return post;
	}
	
	private Post fetchPostById(long id) {
		Post post = null;
		try {
			post =  postsMapper.fetchPostById(id);
			return post;
		}catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}		
	}
}
