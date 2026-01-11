package school.faang.user_service.filters.recommendation_request;

import school.faang.user_service.dto.recommendation.RecommendationRequestFilterDto;
import school.faang.user_service.entity.recommendation.RecommendationRequest;
import school.faang.user_service.filters.BaseFilter;

public interface RecommendationRequestFilterInterface extends
        BaseFilter<RecommendationRequest, RecommendationRequestFilterDto> {
}
