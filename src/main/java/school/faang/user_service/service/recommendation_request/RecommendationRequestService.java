package school.faang.user_service.service.recommendation_request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import school.faang.user_service.dto.recommendation.CreateRecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestFilterDto;
import school.faang.user_service.dto.recommendation.RejectionDto;

public interface RecommendationRequestService {

    RecommendationRequestDto create(CreateRecommendationRequestDto recommendationDto);

    Page<RecommendationRequestDto> getByFilters(RecommendationRequestFilterDto filtersDto, Pageable pageable);

    RecommendationRequestDto getById(long id);

    void accept(long id);

    void reject(long id, RejectionDto rejection);
}
