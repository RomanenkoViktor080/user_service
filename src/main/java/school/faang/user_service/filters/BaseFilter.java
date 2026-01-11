package school.faang.user_service.filters;

import org.springframework.data.jpa.domain.Specification;

public interface BaseFilter<T, U> {
    boolean isApplicable(U dto);

    Specification<T> apply(Specification<T> specification, U dto);
}
