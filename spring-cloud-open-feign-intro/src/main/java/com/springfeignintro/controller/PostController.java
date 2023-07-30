package com.springfeignintro.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springfeignintro.client.JSONPlaceHolderClient;
import com.springfeignintro.model.Post;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostController {

	private final JSONPlaceHolderClient jsonPlaceHolderClient;

	@GetMapping(value = "/posts")
	public List<Post> getPosts() {
		return jsonPlaceHolderClient.getPosts();
	}

}
