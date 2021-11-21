package sapi.com.learningrxjava.introduction;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import sapi.com.learningrxjava.R;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ObservableObserverSample extends AppCompatActivity {

    private String dataStream = "Hello RxJava";
    private Observable<String> myObservable;
    private Observer<String> myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.txv);

        myObservable = Observable.just(dataStream);
        myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("onSubscribe: ", "Called");

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
}