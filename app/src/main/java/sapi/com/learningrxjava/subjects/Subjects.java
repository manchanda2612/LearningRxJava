package sapi.com.learningrxjava.subjects;

import androidx.appcompat.app.AppCompatActivity;
import sapi.com.learningrxjava.R;

import android.os.Bundle;

/**
 * RxJava Docs - A subject is a sort of bridge or proxy that is available in some implementations of ReactiveX that
 * acts both as an observer and as an observable. Because it is an observer, it can subscribe to one or more observable, and
 * because it is an observable, it can pass through the item it observe by re-emitting them, and it can also emit new items.
 *
 * RxSubjects, can act as an Observable as well as Observer.
 *
 * If we consider its code, subject class extending the observable class and implementing the Observer interface.
 * That's why it can act like both.
 *
 * Subject can subscribe to multiple observable and emits the items to multiple subscribers.
 *
 * When do we use subjects?
 * Subjects have a lots of "real world" applications. when you transform your code base from imperative to reactive
 * style. It can serve as a bridge between those two coding styles.
 *
 * There are four main Rxjava Subject types
 * 1) Async Subject - Async Subject only emits the last value of the observable.
 * 2) Behavior Subject - it begins by emitting the item most recently emitted by the observable (default value if non has yet been emitted) and then continue
 * to emit any other items emitted later by the observable.
 * 3) Publish Subject -
 * 4) Replay Subject -
 *
 *
 *
 */
public class Subjects extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}