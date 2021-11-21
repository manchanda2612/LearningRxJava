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
 * Docs - FromArray operator creates an observable from a set of items of the array using an iterable.
 * It means, in this case each string will be emitted one at a time.
 * We can use From Array to any type of data.
 */
public class FromOperator extends AppCompatActivity {

    private static final String TAG = JustOperator.class.getSimpleName();
    private String[] mDataStream = {"Neeraj Manchanda", "Bharti Manchanda", "Neelam"};
    private Observable<String> mObservable;
    private DisposableObserver<String> mObserver;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mObservable = Observable.fromArray(mDataStream);

        mCompositeDisposable.add(
                mObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserver())
        );
    }


    private DisposableObserver<String> getObserver() {

        mObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
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