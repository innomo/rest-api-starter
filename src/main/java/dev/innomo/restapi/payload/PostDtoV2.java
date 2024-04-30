package dev.innomo.restapi.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class PostDtoV2 {
    private long id;
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;
    @Size(min = 15, message = "Post description should have at least 20 characters")
    private String content;
    private Set<CommentDto> comments;
    private List<String> tags = new ArrayList<>();
}
