package school.faang.user_service.filters.goal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.faang.user_service.dto.goal.FilterGoalDto;
import school.faang.user_service.entity.goal.Goal;
import school.faang.user_service.filters.FilterBuilder;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GoalFilterBuilder
        extends FilterBuilder<Goal, FilterGoalDto> {
    private final List<GoalFilterInterface> filters;

    @Override
    public List<GoalFilterInterface> getFilters() {
        return filters;
    }
}
