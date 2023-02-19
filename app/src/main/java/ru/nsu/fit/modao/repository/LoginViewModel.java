package ru.nsu.fit.modao.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.nsu.fit.modao.model.NewUser;
import ru.nsu.fit.modao.model.UserIn;

public class LoginViewModel extends AndroidViewModel {
    MutableLiveData<Long> userId = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public MutableLiveData<Long> getUserIn() {
        return userId;
    }

    public void requestLogin(ApiService questApi, UserIn user){
        compositeDisposable.add(questApi.login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((id) -> userId.setValue(id),
                        (e) -> {
                            if (e.getMessage().equals("HTTP 404 ")) {
                                userId.setValue(-1L);
                            } else {
                                userId.setValue(-2L);
                                e.printStackTrace();
                            }
                        })
        );
    }
    public void createUser(ApiService questApi, NewUser user){
        compositeDisposable.add(questApi.createUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((id) -> userId.setValue(id),
                        (e) -> {
                    if (e.getMessage().equals("HTTP 400 ")){
                        userId.setValue(-1L);
                    }
                    else {
                        userId.setValue(-2L);
                        e.printStackTrace();
                    }
                        })
        );
    }
}
