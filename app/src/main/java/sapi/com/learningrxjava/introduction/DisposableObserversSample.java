package sapi.com.learningrxjava.introduction;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import sapi.com.learningrxjava.R;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


/**
 * For custom observers, we can also use DisposableObserver as a Base class in order to get Disposable behavior
 * provided to you by the abstract base class.
 * <p>
 * DisposableObserver is a much efficient way, it reduces the number of code lines and it makes our work easier,
 * especially if you have more than one observer in a class.
 * <p>
 *  Important - DisposableObserver class implements both Observer and Disposable interfaces.
 * <p>
 * Observer implementation had four overridden methods. onSubscribe() method was mainly there to receive the disposable.
 * But , DisposableObserver implementation has only three overridden methods.
 * DisposableObserver does not need onSubscribe method ,as DisposableObserver can dispose by itself.
 */
public class DisposableObserversSample extends AppCompatActivity {

    private String dataStream = "Hello RxJava";
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;

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

        myObservable.subscribe(myObserver);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // when an activity or fragment destroyed I am disposing the subscription
        myObserver.dispose();

    }
}