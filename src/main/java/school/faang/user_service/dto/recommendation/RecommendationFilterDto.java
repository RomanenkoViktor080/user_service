package school.faang.user_service.dto.recommendation;

public record RecommendationFilterDto(
        String content,
        Long authorId,
        Long receiverId
) {
}