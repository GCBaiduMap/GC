package gc.com.gcmapapp.utils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class GsonUtils {

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    public static <T> List<T> parseJsonArrayWithGson(String jsonData,
                                                     Class<T[]> clazz) {
        Gson gson = new Gson();
        T[] array = gson.fromJson(jsonData, clazz);
        return Arrays.asList(array);
    }

}
