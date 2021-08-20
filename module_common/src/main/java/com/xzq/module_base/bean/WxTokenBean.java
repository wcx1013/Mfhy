package com.xzq.module_base.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class WxTokenBean  implements Parcelable {
    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     * refresh_token : REFRESH_TOKEN
     * openid : OPENID
     * scope : SCOPE
     * unionid : o6_bmasdasdsad6_2sgVt7hMZOPfL
     */

    public String access_token;
    public int expires_in;
    public String refresh_token;
    public String openid;
    public String scope;
    public String unionid;

    protected WxTokenBean(Parcel in) {
        access_token = in.readString();
        expires_in = in.readInt();
        refresh_token = in.readString();
        openid = in.readString();
        scope = in.readString();
        unionid = in.readString();
    }

    public static final Creator<WxTokenBean> CREATOR = new Creator<WxTokenBean>() {
        @Override
        public WxTokenBean createFromParcel(Parcel in) {
            return new WxTokenBean(in);
        }

        @Override
        public WxTokenBean[] newArray(int size) {
            return new WxTokenBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(access_token);
        dest.writeInt(expires_in);
        dest.writeString(refresh_token);
        dest.writeString(openid);
        dest.writeString(scope);
        dest.writeString(unionid);
    }
}
