package ru.nsu.fit.modao.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CreateExpenseParticipant implements Parcelable {
    private String username;
    private Float coefficient;
    private Long id;
    private String assumedCoefficient;
    public CreateExpenseParticipant(Parcel in){
        username = in.readString();
        coefficient = in.readFloat();
        id = in.readLong();
        assumedCoefficient = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeFloat(coefficient);
        dest.writeLong(id);
        dest.writeString(assumedCoefficient);
    }
    public static final Parcelable.Creator<CreateExpenseParticipant> CREATOR =
            new Creator<CreateExpenseParticipant>() {
                @Override
                public CreateExpenseParticipant createFromParcel(Parcel source) {
                    return new CreateExpenseParticipant(source);
                }

                @Override
                public CreateExpenseParticipant[] newArray(int size) {
                    return new CreateExpenseParticipant[0];
                }
            };
    public String getUsername() {
        return username;
    }

    public Float getCoefficient() {
        return coefficient;
    }

    public Long getId() {
        return id;
    }
    public void setAssumedCoefficient(String assumedCoefficient) {
        this.assumedCoefficient = assumedCoefficient;
    }

    public String getAssumedCoefficient() {
        return assumedCoefficient;
    }

    public CreateExpenseParticipant(String username, Float coefficient, Long id, String assumedCoefficient) {
        this.username = username;
        this.coefficient = coefficient;
        this.id = id;
        this.assumedCoefficient = assumedCoefficient;
    }

    public CreateExpenseParticipant(String username, Long id) {
        this.username = username;
        this.id = id;
    }

    public void setCoefficient(Float coefficient) {
        this.coefficient = coefficient;
    }

    public CreateExpenseParticipant(String username, Float coefficient, Long id) {
        this.username = username;
        this.coefficient = coefficient;
        this.id = id;
    }
}
