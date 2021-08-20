package com.xzq.module_base.mvp;

import com.xzq.module_base.utils.EntitySerializerUtils;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BaseJsonParam {
    public RequestBody create() {
        return create(EntitySerializerUtils.serializerEntity(this));
    }

    public static RequestBody create(String json) {
        return RequestBody.create(MediaType.parse("application/json"), json);
    }
}
