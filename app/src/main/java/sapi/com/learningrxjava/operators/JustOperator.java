package sapi.com.learningrxjava.operators;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import sapi.com.learningrxjava.R;

import android.os.Bundle;
import android.util.Log;

/**
 * RxJava Doc - create an Observable that emits a particular item
 *
 * As a name suggest, just operator just emits the same values provided in the arguments.
 * Just operator converts an item into observable that emits that item.
 * Just operator only emits the object once. It doesn't iterate through each item of the array
 * Layman language - It simple pass the object to observable and then observable simply emits the same object to observer.
 */
public class JustOperator extends AppCompatActivity {

    private static final String TAG = JustOperator.class.getSimpleName();
    private String[] mDataStream = {"Neeraj Manchanda", "Bharti Manchanda", "Neelam"};
    private Observable<String[]> mObservable;
    private DisposableObserver<String[]> mObserver;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mObservable = Observable.just(mDataStream);

        mCompositeDisposable.add(
          mObservable.subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeWith(getObserver())
        );
    }


    private DisposableObserver<String[]> getObserver() {

        mObserver = new DisposableObserver<String[]>() {
            @Override
            public void onNext(String[] s) {
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