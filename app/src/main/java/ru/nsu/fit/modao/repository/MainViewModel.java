package ru.nsu.fit.modao.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.nsu.fit.modao.model.User;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<User> getUserLive(){
        return user;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
    public void getUser(ApiService questApi, Long id){
        compositeDisposable.add(questApi.getUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((responseUser) -> user.setValue(responseUser),
                        Throwable::printStackTrace)
        );
    }
}
