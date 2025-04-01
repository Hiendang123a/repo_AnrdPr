package com.example.society.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "posts")
public class Post {
    @Id
    private ObjectId postID;
    private ObjectId userID;
    private String content;
    private List<String> imageUrls;
    private List<ObjectId> emotions;
    private List<ObjectId> comments;
}
