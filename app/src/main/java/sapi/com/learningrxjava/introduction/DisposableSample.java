package sapi.com.learningrxjava.introduction;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import sapi.com.learningrxjava.R;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * For example in your app, you have to make REST API call and update the view accordingly.
 * If a user initiate a view and decide to go back before the completion of the network call.
 * The activity or fragment will be destroyed. But the observer subscription will be there. when the
 * observer trying to update the User Interface, in this scenario as the view already destroyed, it can
 * cause a memory leak. And your app will freeze or crash as a result.
 * To avoid such situations, we can use Disposable to dispose the subscription when an observer no longer wants to listen
 * to the observable.
 */
public class DisposableSample extends AppCompatActivity {

    private String dataStream = "Hello RxJava";
    private Observable<String> myObservable;
    private Observer<String> myObserver;
    private Disposable myDisposable;

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


        myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("onSubscribe: ", "Called");
                // when an observer subscribed to an observable it return disposable
                // so I am storing disposable so that I can dispose it when activity
                // or fragment destroyed.
                myDisposable = d;
            }

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

        myObservable.subscribe(myObserver);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // when an activity or fragment destroyed I am disposing the subscription
        myDisposable.dispose();
    }
}