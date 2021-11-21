package sapi.com.learningrxjava.introduction;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import sapi.com.learningrxjava.R;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * In this example, we learn shortcut way to use schedulers, observable and observer.
 *
 * Special benefit of using subscribeWith() is simply returns an observer.
 * As our observer is disposable observer. This subscribeWith() returns a disposable observer.
 *
 */
public class EffectiveWay extends AppCompatActivity {

    private String dataStream = "Hello Neeraj Manchanda";
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;
    private DisposableObserver<String> myObserver2;
    private CompositeDisposable myCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.txv);

        myObservable = Observable.just(dataStream);

        myObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                textView.setText(s);
                Log.d("onNext: ", "Called");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("onError: ", "Called");

            }

            @Override
            public void onComplete() {
                Log.d("onComplete: ", "Called");
            }
        };

        myCompositeDisposable.add(
           myObservable
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribeWith(myObserver)
        );


        myObserver2 = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Log.d("onNext: ", "Called");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("onError: ", "Called");

            }

            @Override
            public void onComplete() {
                Log.d("onComplete: ", "Called");
            }
        };

        myCompositeDisposable.add(
                myObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(myObserver2)
        );

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        myCompositeDisposable.clear();
    }
}