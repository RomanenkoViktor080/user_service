package school.faang.user_service.filters.recommendation;

import school.faang.user_service.dto.recommendation.RecommendationFilterDto;
import school.faang.user_service.entity.recommendation.Recommendation;
import school.faang.user_service.filters.BaseFilter;

public interface RecommendationFilter extends BaseFilter<Recommendation, RecommendationFilterDto> {
}
