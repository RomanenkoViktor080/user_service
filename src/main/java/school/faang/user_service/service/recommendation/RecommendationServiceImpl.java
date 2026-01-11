package school.faang.user_service.service.recommendation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.recommendation.CreateRecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationDto;
import school.faang.user_service.dto.recommendation.RecommendationFilterDto;
import school.faang.user_service.dto.recommendation.UpdateRecommendationDto;
import school.faang.user_service.entity.recommendation.Recommendation;
import school.faang.user_service.entity.user.User;
import school.faang.user_service.exception.EntityNotFoundException;
import school.faang.user_service.filters.FilterBuilderInterface;
import school.faang.user_service.mapper.RecommendationMapper;
import school.faang.user_service.publisher.RecommendationReceivedEventPublisher;
import school.faang.user_service.repository.recommendation.RecommendationRepository;
import school.faang.user_service.repository.user.UserRepository;
import school.faang.user_service.validator.recommendation.RecommendationValidator;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapper recommendationMapper;
    private final UserContext userContext;
    private final UserRepository userRepository;
    private final FilterBuilderInterface<Recommendation, RecommendationFilterDto> filters;
    private final RecommendationValidator recommendationValidator;
    private final RecommendationReceivedEventPublisher recommendationReceivedEventPublisher;


    @Override
    public RecommendationDto create(CreateRecommendationDto recommendationDto) {
        recommendationValidator.validateCreate(recommendationDto);

        Recommendation recommendation = recommendationMapper.toRecommendation(recommendationDto);
        User receiver = userRepository.getByIdOrThrow(recommendationDto.receiverId());
        recommendation.setReceiver(receiver);
        User author = userRepository.getByIdOrThrow(userContext.getUserId());
        recommendation.setAuthor(author);
        recommendation = recommendationRepository.save(recommendation);
        log.info("Recommendation {} created", recommendation.getId());
        publishRecommendationReceivedEvent(recommendation);
        return recommendationMapper.toRecommendationDto(recommendation);
    }

    private void publishRecommendationReceivedEvent(Recommendation recommendation) {
        recommendationReceivedEventPublisher.publish(
                recommendationMapper.toRecommendationReceivedEventDto(recommendation)
        );
    }

    @Override
    public RecommendationDto update(long recommendationId, UpdateRecommendationDto updateRecommendationDto) {
        Recommendation recommendation = getRecommendationOrFail(recommendationId);
        recommendationValidator.validateUpdate(recommendation);

        recommendationMapper.update(updateRecommendationDto, recommendation);
        recommendation = recommendationRepository.save(recommendation);
        log.info("Recommendation {} updated", recommendation.getId());
        return recommendationMapper.toRecommendationDto(recommendation);
    }

    @Override
    @Transactional
    public void delete(long recommendationId) {
        Recommendation recommendation = getRecommendationOrFail(recommendationId);
        recommendationValidator.validateDelete(recommendation);
        int deletedId = recommendationRepository.deleteByIdAndAuthorId(
                recommendation.getId(),
                recommendation.getAuthor().getId()
        );
        log.info("Recommendation {} deleted", deletedId);
    }

    @Override
    public Page<RecommendationDto> getByFilters(RecommendationFilterDto dto, Pageable pageable) {
        Specification<Recommendation> specification = filters.buildSpecification(dto, null);
        Page<Recommendation> recommendations = recommendationRepository.findAll(specification, pageable);
        return recommendations.map(recommendationMapper::toRecommendationDto);
    }

    private Recommendation getRecommendationOrFail(long recommendationId) {
        return recommendationRepository
                .findById(recommendationId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Recommendation with id: " + recommendationId + "not found.")
                );
    }
}
