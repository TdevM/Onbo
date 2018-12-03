package onbo.app.base;

/**
 * Created by Tridev on 12-12-2017.
 */

public interface BasePresenterMVP<T> {
    void attachView(T view);
    void detachView();
}
