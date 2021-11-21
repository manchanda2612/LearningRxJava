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
 * what are schedulers in RxJava?
 * To handler multithreading in RxJava we use Schedulers.
 * A Scheduler can be identified as a thread pool managing one or more thread.
 * Whenever a scheduler needs to execute the task, it will take the thread from its pool and run the
 * task in that thread.
 *
 * We have different types of schedulers available in RxJava
 * 1) Schedulers.io() - This can have a limitless thread pool. User for non CPU intensive tasks. such as database interaction,
 * network communications and file system interactions.
 * 2) AndroidSchedulers.mainThread() - This is the  main thread or UI thread. This is where user interaction happen.
 * This scheduler does not come with RxJava, but provided to RxJava from RxAndroid.
 * 3) Schedulers.newThread() - This schedulers creates a new thread for each unit of work.
 * 4) Schedulers.Single() - This scheduler has a single thread executing tasks one after another following the given order.
 * 5) Schedulers.trampoline() - This scheduler executes tasks following first in first out basics. We use when implementing recurring tasks.
 * 6) Schedulers.from(Executor executor) - This create and return a custom scheduler backed by a specific executor.
 *
 * In Android development, 90% of the time we use only 2 schedulers. Schedulers.io() and AndroidSchedulers.mainThread().
 *
 * RxJava has provided us a simple way to schedule a work on a desired thread using two methods. 1) observeOn 2) subscribeOn
 *
 * We include schedulers and operators between the observable and observer.
 */


public class SchedulerSample extends AppCompatActivity {

    private String dataStream = "Hello RxJava";
    private Observable<String> myObservable;
    private Observer<String> myObserver;

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