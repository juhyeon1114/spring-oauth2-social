package security.springoauth2social.converters;

public interface ProviderUserConverter<T, R> {

    R converter(T t);

}
