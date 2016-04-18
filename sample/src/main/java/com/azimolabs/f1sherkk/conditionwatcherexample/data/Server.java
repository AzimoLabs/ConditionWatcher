package com.azimolabs.f1sherkk.conditionwatcherexample.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by F1sherKK on 13/04/16.
 */
public class Server implements Parcelable {

    String name;
    String address;
    String port;

    public Server(String name, String address, String port) {
        this.name = name;
        this.address = address;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Server{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", port='" + port + '\'' +
                '}';
    }

    protected Server(Parcel in) {
        name = in.readString();
        address = in.readString();
        port = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(port);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Server> CREATOR = new Parcelable.Creator<Server>() {
        @Override
        public Server createFromParcel(Parcel in) {
            return new Server(in);
        }

        @Override
        public Server[] newArray(int size) {
            return new Server[size];
        }
    };
}
