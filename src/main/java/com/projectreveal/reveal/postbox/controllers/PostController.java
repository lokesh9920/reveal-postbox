package com.projectreveal.reveal.postbox.controllers;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projectreveal.reveal.postbox.constants.LoggingConstants;
import com.projectreveal.reveal.postbox.models.NewPost;
import com.projectreveal.reveal.postbox.models.Post;
import com.projectreveal.reveal.postbox.service.PostService;


@RestController
@RequestMapping("/posts")
public class PostController {
	
	private final Logger logger = LogManager.getLogger(PostController.class);

	@Autowired
	PostService postService;
	
	@GetMapping
	@CrossOrigin
	public ResponseEntity<Object> getPosts(	@RequestParam("groupId") long groupId, 
											@RequestParam("timeStamp") Long timeStamp,
											@RequestParam("count") int numberOfPosts){
		
		
		//skipping authentication, trusting that the APIs will be internal and only used by some internal MicroService
		
		logger.info(LoggingConstants.REQUEST_LOGGER, "GET", "/posts");
		ArrayList<Post> postsList = postService.fetchPosts(groupId,timeStamp,numberOfPosts);
		
		logger.info(LoggingConstants.RESPONSE_BODY, "GET","/posts",HttpStatus.OK,postsList);
		
		return new ResponseEntity<Object>(postsList, HttpStatus.OK);
		
	}
	
	@PostMapping
	@CrossOrigin
	public ResponseEntity<Post> addPost(@RequestBody(required = true) NewPost newPost){
		logger.info(LoggingConstants.REQUEST_LOGGER, "POST", "/posts");

		Post createdPost = postService.addNewPost(newPost);
		
		logger.info(LoggingConstants.RESPONSE_BODY, "POST","/posts",HttpStatus.OK,createdPost.toString());

		return new ResponseEntity<Post>(createdPost,HttpStatus.CREATED);	
	}
	
}
