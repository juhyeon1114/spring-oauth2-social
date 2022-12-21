package security.springoauth2social.common.converters;

public interface ProviderUserConverter<T, R> {

    R converter(T t);

}
