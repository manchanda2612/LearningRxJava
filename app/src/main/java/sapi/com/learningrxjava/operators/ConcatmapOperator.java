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
 * RxJava Doc - emits the emissions from two or more observables without interleaving them.
 *
 * Concatmap operator cares about the order of elements to emits. Here the elements will not overlap.
 *
 * It is work exact in same manner as flap map except that it preserves the order of items.
 *
 * Disadvantage - It wait for each observable to finish all the work until next one is proceed.
 * So it is not the most efficient solution.
 *
 * So the bottom line here is if you want to maintain the order use concatmap. If speed is important
 * then use flatmap.
 *
 */
public class ConcatmapOperator extends AppCompatActivity {

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

        mCompositeDisposable.add(
                mObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .concatMap(new Function<Student, ObservableSource<Student>>() {
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