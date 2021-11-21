package sapi.com.learningrxjava.operators;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import sapi.com.learningrxjava.R;
import sapi.com.learningrxjava.model.Student;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * RxJava Doc - transform the item emitted by the observable into observables, then flatten the emission from those
 * into a single observable.
 *
 * Flap map is very much similar like Map operator. The difference is that the function is applies
 * return an observable.
 *
 * If you send an item to map function it will return an item which will be emitted only once in the downstream.
 *
 * When you want to convert item emitted to another type I prefer map operator.
 *
 * Both map and flat map can generate the same result. But map is much easier to handle.
 *
 * If you want a non-observable object then you should just use map().
 *
 * If you want an observable object, then you should use flatMap(). And flatMap() unwraps the Observable,
 * picks the returned object wraps it with its own Observable and emit it.
 *
 * If you send an item to flat map function it will return an Observable, each item of the new "Observable"
 * will be emitted separately in the downstream.
 * Remember map takes in item and return an item. FlatMap takes an item and returns an observable.
 *
 * Disadvantage - Flat map doesn't preserve the order of the elements. If there is large amount of data to emit
 * data stream may overlap.
 *
 */
public class FlatMapOperator extends AppCompatActivity {

    private static final String TAG = JustOperator.class.getSimpleName();
    private Observable<Student> mObservable;
    private DisposableObserver<Student> mObserver;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mObservable = Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> emitter) throws Exception {

                ArrayList<Student> studentsList = getStudentList();

                for(Student student : studentsList) {

                    emitter.onNext(student);
                }
            }
        });


        /**
         * Our flatmap operator return 5 observables each emits 3 student objects
         * Consider a scenario where you have a network call to fetch Movie details. Then you have another network  call, that give you lists filming
         * locations of those movies.
         * For Example - There is a situation where you have to hit one API that will give you some result and that result will have URL and now you have
         * to again hit an API to fetch the final result. Then in that case Flat map is useful.
         */
        mCompositeDisposable.add(
                mObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<Student, ObservableSource<Student>>() {
                            @Override
                            public ObservableSource<Student> apply(Student student) throws Exception {

                                Student student1 = new Student();
                                student1.name = student.name;

                                Student student2 = new Student();
                                student2.name = "Hello " + student.name;

                                student.name = student.name.toUpperCase();
                                return Observable.just(student, student1, student2);
                            }
                        })
                        .subscribeWith(getObserver())
        );
    }

    private DisposableObserver<Student> getObserver() {

        mObserver = new DisposableObserver<Student>() {
            @Override
            public void onNext(Student s) {
                Log.d(TAG, "onNext: "  + s.name);
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


    private ArrayList<Student> getStudentList() {

        ArrayList<Student> studentArrayList = new ArrayList<>();

        Student student1 = new Student("Neeraj", "manchanda2612@gmail.com", 30, "12/01/2012");
        studentArrayList.add(student1);
        Student student2 = new Student("Bharati", "rediffmail1603@gmail.com", 35, "12/01/2012");
        studentArrayList.add(student2);
        Student student3 = new Student("Vivek", "vivekShehrawat@gmail.com", 32, "12/01/2012");
        studentArrayList.add(student3);
        Student student4 = new Student("Jogi", "jogi@gmail.com", 31, "12/01/2012");
        studentArrayList.add(student4);

        return studentArrayList;
    }
}