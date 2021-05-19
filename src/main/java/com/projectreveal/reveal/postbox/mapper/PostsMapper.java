package com.projectreveal.reveal.postbox.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.projectreveal.reveal.postbox.models.NewPost;
import com.projectreveal.reveal.postbox.models.Post;

@Mapper
public interface PostsMapper {
	
	
	@Select("select post_content,likes_count,dislikes_count,time_stamp from posts where time_stamp < #{timeStamp} and group_id=#{groupId} order by time_stamp DESC fetch first #{numberOfPosts} rows only")
	@Results({
		@Result(property = "postContent",column = "post_content"),
		@Result(property = "likesCount",column = "likes_count"),
		@Result(property = "dislikesCount",column = "dislikes_count"),
		@Result(property = "timeStamp",column = "time_stamp")
	})
	public ArrayList<Post> fetchPosts(long groupId, Long timeStamp, int numberOfPosts);

	
	/*
	 * @options is used to retrieve the id created by the insert statement, that id is stored into post object, id parameter
	 */
	@Options(useGeneratedKeys = true, keyProperty = "post.id", keyColumn = "ID")
	@Insert("insert into Posts (post_content,time_stamp,created_by,group_id) values(#{postContent},#{timeStamp},#{postCreator},#{groupId})")
	public void addNewPost(String postContent,long groupId, String postCreator, Long timeStamp,Post post);

	
	@Select("select * from posts where id = #{id}")
	@Results({
		@Result(property = "postContent",column = "post_content"),
		@Result(property = "likesCount",column = "likes_count"),
		@Result(property = "dislikesCount",column = "dislikes_count"),
		@Result(property = "timeStamp",column = "time_stamp")
	})
	public Post fetchPostById(long id);


}
