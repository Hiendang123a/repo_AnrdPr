package com.example.society.Entity;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post_comments")
public class PostComment {
    @Id
    private ObjectId commentID;
    private ObjectId userID;
    private String content;
    private List<String> imageUrls;
    private Date createdAt;
    private List<ObjectId> repliesComment = new ArrayList<>();
    private List<ObjectId> repliesEmotion = new ArrayList<>();
}
