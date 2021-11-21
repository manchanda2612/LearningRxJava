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
 * In RxJava, one observable can have multiple observer. Also, in one class you can have more than
 * one observables. So in that case you have so many observers to dispose.
 *
 * But we have better way of doing that. When we have more than one observers we use composite disposable.
 *
 * CompositeDisposable can maintain a list of subscriptions in a pool and can dispose them all at once.
 *
 * Important - What is the difference between clear() and dispose() ?
 * When you are using CompositeDisposable, If you call to dispose() method, you will no longer be able to add disposables to that composite disposable.
 * But if you call to clear() method you can still add disposable to the composite disposable . Clear() method just clears the disposables
 * that are currently held within the instance.
 *
 */
public class CompositeDisposableSample extends AppCompatActivity {

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

        // data stream will execute on the io thread. Observer will receive the data through
        // the IO thread. If we add different operators to control and change this data stream
        // those changes will happen on io thread.
        myObservable.subscribeOn(Schedulers.io());

        // this means this data stream will move to main thread again.
        myObservable.observeOn(AndroidSchedulers.mainThread());


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

        myCompositeDisposable.add(myObserver);
        myObservable.subscribe(myObserver);


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

        myCompositeDisposable.add(myObserver2);
        myObservable.subscribe(myObserver2);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // when an activity or fragment destroyed I am disposing the subscription
        // as you have observed we have to explicitly dispose multiple observer
        // but if you have CompositeDisposable you will be able to get the same job done in much
        // convenient way
      //  myObserver.dispose();
      //  myObserver2.dispose();


        // it is not important to call this method only in onDestroy(). We can call this method
        // from anywhere
        myCompositeDisposable.clear();
    }
}