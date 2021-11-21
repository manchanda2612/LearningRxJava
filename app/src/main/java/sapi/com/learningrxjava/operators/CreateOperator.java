package sapi.com.learningrxjava.operators;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import sapi.com.learningrxjava.R;
import sapi.com.learningrxjava.model.Student;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * RxJava Docs - Create an observable from scratch by mean of a function.
 *
 * Create operator is another way of creating and subscribing to observables.
 *
 * With create method we can have a function body. So we can have some control over our data
 * before the emition.
 *
 * We can create a custom observable.
 *
 * Using create operator we have more control over the data emition. We can decide what object to emit and we can change the data
 * we are going to emit.
 *
 */
public class CreateOperator extends AppCompatActivity {

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
                    if(student.age > 30) {
                        emitter.onNext(student);
                    }
                }

            }
        });

        mCompositeDisposable.add(
                mObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
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