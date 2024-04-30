package dev.innomo.restapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Schema(
        description = "PostDto information"
)
@Data
public class PostDto {
    private long id;
    @Schema(
            description = "Post title"
    )
    @NotEmpty
   // @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;
    @Schema(
            description = "Post description"
    )
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;
    @Schema(
            description = "Post content"
    )
    @Size(min = 15, message = "Post description should have at least 20 characters")
    private String content;
    @Schema(
            description = "Post comments"
    )
    private Set<CommentDto> comments;
}
