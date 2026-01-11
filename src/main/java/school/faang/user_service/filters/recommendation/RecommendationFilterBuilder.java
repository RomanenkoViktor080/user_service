package school.faang.user_service.filters.recommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.faang.user_service.dto.recommendation.RecommendationFilterDto;
import school.faang.user_service.entity.recommendation.Recommendation;
import school.faang.user_service.filters.FilterBuilder;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecommendationFilterBuilder
        extends FilterBuilder<Recommendation, RecommendationFilterDto> {
    private final List<RecommendationFilter> filters;

    @Override
    public List<RecommendationFilter> getFilters() {
        return filters;
    }
}
