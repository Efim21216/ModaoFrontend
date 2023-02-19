package ru.nsu.fit.modao.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
/*
* public class MyParcelable implements Parcelable {
     private int mData;

     public int describeContents() {
         return 0;
     }

     public void writeToParcel(Parcel out, int flags) {
         out.writeInt(mData);
     }

     public static final Parcelable.Creator<MyParcelable> CREATOR
             = new Parcelable.Creator<MyParcelable>() {
         public MyParcelable createFromParcel(Parcel in) {
             return new MyParcelable(in);
         }

         public MyParcelable[] newArray(int size) {
             return new MyParcelable[size];
         }
     };

     private MyParcelable(Parcel in) {
         mData = in.readInt();
     }
 }
* */
public class Group implements Parcelable {
    String groupName;
    Long id;
    String description;
    Integer typeGroup;

    public void setId(Long id) {
        this.id = id;
    }

    public Group(String groupName, Long id, String description, Integer typeGroup) {
        this.groupName = groupName;
        this.id = id;
        this.description = description;
        this.typeGroup = typeGroup;
    }
    public Group(Parcel in){
        groupName = in.readString();
        id = in.readLong();
        description = in.readString();
        typeGroup = in.readInt();
    }
    public Long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(groupName);
        dest.writeLong(id);
        dest.writeString(description);
        dest.writeInt(typeGroup);
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
