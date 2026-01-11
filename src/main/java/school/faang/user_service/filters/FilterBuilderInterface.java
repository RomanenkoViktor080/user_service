package school.faang.user_service.filters;

import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public interface FilterBuilderInterface<T, U> {
    Specification<T> buildSpecification(
            U params,
            Function<Specification<T>, Specification<T>> customizer
    );
}
