package school.faang.user_service.filters.recommendation_request;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import school.faang.user_service.dto.recommendation.RecommendationRequestFilterDto;
import school.faang.user_service.entity.recommendation.RecommendationRequest;

@Component
public class RecommendationRequestFilterRequesterId implements RecommendationRequestFilterInterface {
    @Override
    public boolean isApplicable(RecommendationRequestFilterDto recommendationRequestFilterDto) {
        return recommendationRequestFilterDto.requesterId() != null;
    }

    @Override
    public Specification<RecommendationRequest> apply(
            Specification<RecommendationRequest> specification,
            RecommendationRequestFilterDto dto
    ) {
        return specification.and((root, query, cb) ->
                cb.equal(root.get("requester").get("id"), dto.requesterId())
        );
    }
}
