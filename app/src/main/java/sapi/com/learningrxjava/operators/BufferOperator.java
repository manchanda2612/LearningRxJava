package sapi.com.learningrxjava.operators;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import sapi.com.learningrxjava.R;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * RxJava Doc - Periodically gather item emitted by an observable into bundles and emits these bundles rather than emitting the items
 * one at a time.
 *
 *
 *
 */
public class BufferOperator extends AppCompatActivity {

    private Observable<Integer> observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        observable = Observable.range(1,20);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .buffer(4)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.d("onNext: " , "Called");
                        for (Integer integer : integers) {
                            Log.d("Integer is : ", integer + "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("onComplete: ", "Called");

                    }
                });

    }
}