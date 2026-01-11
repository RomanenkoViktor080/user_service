package school.faang.user_service.filters.recommendation_request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.faang.user_service.dto.recommendation.RecommendationRequestFilterDto;
import school.faang.user_service.entity.recommendation.RecommendationRequest;
import school.faang.user_service.filters.FilterBuilder;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecommendationRequestFilterBuilder
        extends FilterBuilder<RecommendationRequest, RecommendationRequestFilterDto> {
    private final List<RecommendationRequestFilterInterface> filters;

    @Override
    public List<RecommendationRequestFilterInterface> getFilters() {
        return filters;
    }
}
