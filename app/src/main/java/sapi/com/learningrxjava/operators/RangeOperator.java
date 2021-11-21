package sapi.com.learningrxjava.operators;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import sapi.com.learningrxjava.R;

import android.os.Bundle;
import android.util.Log;

/**
 * RxJava Docs - create an observable that emits a particular range of sequential integers.
 * The Range operator emits a range of sequential integers, in order, where you select the start of the range
 * and its length.
 *
 *
 */
public class RangeOperator extends AppCompatActivity {

    private static final String TAG = JustOperator.class.getSimpleName();
    private Observable<Integer> mObservable;
    private DisposableObserver<Integer> mObserver;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mObservable = Observable.range(5, 20);

        mCompositeDisposable.add(
                mObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserver())
        );
    }


    private DisposableObserver<Integer> getObserver() {

        mObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "onNext: "  + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "  + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");

            }
        };

        return mObserver;
    }
}