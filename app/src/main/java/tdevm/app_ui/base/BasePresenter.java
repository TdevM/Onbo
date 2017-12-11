package tdevm.app_ui.base;

import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tridev on 12-10-2017.
 */

public class BasePresenter {

//    protected CompositeDisposable compositeDisposable;
//
//    protected CompositeDisposable initializeDisposable() {
//        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
//            compositeDisposable = new CompositeDisposable();
//        }
//        return compositeDisposable;
//    }
//
//    protected void addToDisposable(Disposable d){
//        initializeDisposable().add(d);
//    }
//
//    protected void clearDisposable(){
//        if (compositeDisposable != null) {
//            compositeDisposable.dispose();
//            compositeDisposable.clear();
//        }
//    }
    protected<T> void subscribe(Observable<T> observable, Observer<T> observer){
         observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
