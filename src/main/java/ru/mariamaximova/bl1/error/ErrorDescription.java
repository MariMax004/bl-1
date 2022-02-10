package ru.mariamaximova.bl1.error;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.mariamaximova.bl1.error.model.ApplicationErrorDto;

@Getter
@RequiredArgsConstructor
public enum ErrorDescription {

    SAVE_COMMENT_ERROR("Ошибка сохранения коментария"),
    SAVE_COMMENT_ERROR_UNIQ("Комментарий пользователя уже был добавлен"),
    REGISTRATION_ERROR_USER_IS_PRESENT("Пользователь уже зарегестрирован в системе"),
    AUTH_PASSWORD_ERROR("Введён некорректный пароль"),
    AUTH_LOGIN_ERROR("Введён некорректный логин"),
    CUSTOMER_NOT_LOGIN_ERROR("Пользователь уже вышел"),

    UNAUTHORIZED_ACCESS("Неавторизованный доступ"),
    ACCESS_DENIED("Недостаточно прав для доступа к ресурсу"),
    HANDLER_NOT_FOUND("HANDLER_NOT_FOUND"),
    UNKNOWN_ERROR("Неизвестная ошибка сервера"),
    SERVICE_UNAVAILABLE("Сервис недоступен");

    /**
     * Сообщение ошибки.
     */
    private final String message;

    /**
     * Метод выбрасывает исключение приложения.
     *
     * @throws ApplicationException исключение приложения
     */
    public void throwException() throws ApplicationException {
        throw exception();
    }

    /**
     * Метод выбрасывает ислючение если объект == null.
     *
     * @param obj объект для проверки
     */
    public void throwIfNull(Object obj) {
        if (obj == null) {
            throw exception();
        }
    }

    /**
     * Метод выбрасывает ислючение если условие истинно.
     *
     * @param condition условие для проверки
     */
    public void throwIfTrue(Boolean condition) {
        if (condition) {
            throw exception();
        }
    }

    /**
     * Метод выбрасывает ислючение если условие ложно.
     *
     * @param condition условие для проверки
     */
    public void throwIfFalse(Boolean condition) {
        if (!condition) {
            throw exception();
        }
    }

    public ApplicationErrorDto createApplicationError() {
        return ApplicationErrorDto.of(this.message);
    }

    public ApplicationException exception() {
        return new ApplicationException(createApplicationError());
    }

}
