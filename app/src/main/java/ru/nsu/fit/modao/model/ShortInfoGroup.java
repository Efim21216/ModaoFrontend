package ru.nsu.fit.modao.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ShortInfoGroup implements Parcelable {
    String name;
    Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public ShortInfoGroup(String name, Long id) {
        this.name = name;
        this.id = id;
    }
    public ShortInfoGroup(Parcel in){
        name = in.readString();
        id = in.readLong();
    }
    public Long getId() {
        return id;
    }

    public String getGroupName() {
        return name;
    }

    public void setGroupName(String groupName) {
        this.name = groupName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(id);
    }
    public static final Parcelable.Creator<Group> CREATOR =
            new Creator<Group>() {
                @Override
                public Group createFromParcel(Parcel source) {
                    return new Group(source);
                }

                @Override
                public Group[] newArray(int size) {
                    return new Group[size];
                }
            };
}
