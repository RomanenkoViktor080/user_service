package school.faang.user_service.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import school.faang.user_service.dto.user.UpdateUserDto;
import school.faang.user_service.dto.user.UserDto;

/**
 * Сервис для управления пользователями.
 * Предоставляет методы для создания, обновления и получения информации о пользователях.
 */
public interface UserService extends UserDetailsService {

    /**
     * Обновляет информацию о существующем пользователе.
     * <p>
     * Условия:
     * <ul>
     *     <li>Пользователь с указанным {@code userId} должен существовать —
     *         иначе выбрасывается {@code EntityNotFoundException}.</li>
     *     <li>Обновление данных другого пользователя не допускается —
     *         в этом случае выбрасывается {@code ForbiddenException}.</li>
     *     <li>Если обновляется email, он должен быть уникальным —
     *         иначе выбрасывается {@code DataIntegrityViolationException}.</li>
     * </ul>
     *
     * @param userId  идентификатор пользователя, чьи данные необходимо обновить
     * @param userDto объект {@link UpdateUserDto}, содержащий обновлённые данные пользователя
     * @return объект {@link UserDto}, представляющий обновлённого пользователя
     */
    UserDto update(long userId, UpdateUserDto userDto);

    UserDto updateProfile(UpdateUserDto userDto);

    /**
     * Возвращает информацию о пользователе по его идентификатору.
     * <p>
     * Если пользователь с указанным идентификатором не найден,
     * выбрасывается {@code EntityNotFoundException}.
     *
     * @param userId идентификатор пользователя
     * @return объект {@link UserDto}, содержащий данные пользователя
     */

    UserDto getUser(long userId);

    UserDto deactivateUserById(Long userId);
}


