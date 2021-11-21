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

/**
 * Using skip last operator, you can ignore the last n items emitted by an observable and attend only to those
 * items that comes before.
 */
public class SkipLastOperator extends AppCompatActivity {

    private Observable<Integer> observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        observable = Observable.just(1,2,2,4,5,6,6,8,20,20);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .skipLast(4)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integers) {
                        Log.d("Distinct Integer is : ", integers + "");

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