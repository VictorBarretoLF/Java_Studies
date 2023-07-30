package com.springfeignintro.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springfeignintro.config.ClientConfiguration;
import com.springfeignintro.model.Post;

@FeignClient(value = "jplaceholder", url = "https://jsonplaceholder.typicode.com/", configuration = ClientConfiguration.class)
public interface JSONPlaceHolderClient {

    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    List<Post> getPosts();
    
}
