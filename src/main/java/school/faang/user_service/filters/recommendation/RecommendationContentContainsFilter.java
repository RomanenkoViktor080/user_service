package school.faang.user_service.filters.recommendation;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import school.faang.user_service.dto.recommendation.RecommendationFilterDto;
import school.faang.user_service.entity.recommendation.Recommendation;

@Component
public class RecommendationContentContainsFilter implements RecommendationFilter {
    @Override
    public boolean isApplicable(RecommendationFilterDto recommendationFilterDto) {
        return recommendationFilterDto.content() != null;
    }

    @Override
    public Specification<Recommendation> apply(
            Specification<Recommendation> specification,
            RecommendationFilterDto dto
    ) {
        return specification.and((root, query, cb) ->
                cb.like(cb.lower(root.get("content")), "%" + dto.content() + "%")
        );
    }
}
