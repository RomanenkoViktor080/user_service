package school.faang.user_service.service.recommendation_request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.recommendation.CreateRecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestDto;
import school.faang.user_service.dto.recommendation.RecommendationRequestFilterDto;
import school.faang.user_service.dto.recommendation.RejectionDto;
import school.faang.user_service.entity.RequestStatus;
import school.faang.user_service.entity.recommendation.RecommendationRequest;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.filters.FilterBuilderInterface;
import school.faang.user_service.mapper.RecommendationRequestMapper;
import school.faang.user_service.repository.recommendation.RecommendationRequestRepository;
import school.faang.user_service.repository.user.UserRepository;
import school.faang.user_service.validator.recommendation.ValidatorRecommendation;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationRequestServiceImpl implements RecommendationRequestService {
    private final RecommendationRequestRepository recommendationRequestRepository;
    private final UserRepository userRepository;
    private final RecommendationRequestMapper recommendationRequestMapper;
    private final UserContext userContext;
    private final FilterBuilderInterface<RecommendationRequest, RecommendationRequestFilterDto> filters;
    private final ValidatorRecommendation validatorRecommendation;

    @Override
    public RecommendationRequestDto create(CreateRecommendationRequestDto recommendationDto) {
        User requester = userRepository.getByIdOrThrow(userContext.getUserId());
        User receiver = userRepository.getByIdOrThrow(recommendationDto.receiverId());

        validatorRecommendation.validateRecommendationIsRequest(receiver.getId(),
                requester.getId(), "receiverId");
        validatorRecommendation.validateTimeOutSixMount(receiver.getRecommendationsReceived()
                .get(recommendationDto.receiverId().intValue()).getCreatedAt(), "time out");

        RecommendationRequest recommendationRequest = recommendationRequestMapper
                .toRecommendationRequest(recommendationDto);
        recommendationRequest.setRequester(requester);
        recommendationRequest.setReceiver(receiver);
        recommendationRequest = recommendationRequestRepository.save(recommendationRequest);

        return recommendationRequestMapper.toRecommendationRequestDto(recommendationRequest);
    }

    @Override
    public Page<RecommendationRequestDto> getByFilters(RecommendationRequestFilterDto filtersDto, Pageable pageable) {
        Specification<RecommendationRequest> specification = filters.buildSpecification(filtersDto, null);
        Page<RecommendationRequest> recommendationRequests = recommendationRequestRepository
                .findAll(specification, pageable);
        return recommendationRequests.map(recommendationRequestMapper::toRecommendationRequestDto);

    }

    @Override
    public RecommendationRequestDto getById(long id) {
        return recommendationRequestMapper
                .toRecommendationRequestDto(recommendationRequestRepository.getByIdOrThrow(id));
    }

    @Override
    public void accept(long id) {
        RecommendationRequest recommendationRequest = recommendationRequestRepository.getByIdOrThrow(id);
        validatorRecommendation.validateRecommendationToRequest(userContext.getUserId(), id, "id");
        validatorRecommendation.validateStatus(recommendationRequest.getStatus());
        recommendationRequest.setStatus(RequestStatus.ACCEPTED);
        recommendationRequest.setUpdatedAt(LocalDateTime.now());
        recommendationRequestRepository.save(recommendationRequest);
    }

    @Override
    public void reject(long id, RejectionDto rejection) {
        RecommendationRequest recommendationRequest = recommendationRequestRepository.getByIdOrThrow(id);
        validatorRecommendation.validateRecommendationToRequest(userContext.getUserId(), id, "id");
        validatorRecommendation.validateStatus(recommendationRequest.getStatus());
        recommendationRequest.setRejectionReason(rejection.reason());
        recommendationRequest.setStatus(RequestStatus.REJECTED);
        recommendationRequest.setUpdatedAt(LocalDateTime.now());
        recommendationRequestRepository.save(recommendationRequest);
    }
}
