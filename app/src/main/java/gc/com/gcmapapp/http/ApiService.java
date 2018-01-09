package gc.com.gcmapapp.http;

import java.util.List;

import gc.com.gcmapapp.bean.CoordinationInfo;
import gc.com.gcmapapp.bean.Login;
import gc.com.gcmapapp.bean.MapResult;
import gc.com.gcmapapp.bean.Menu;
import gc.com.gcmapapp.bean.ResultMsg;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {


    @POST("app/login")
    Observable<ResultMsg<Login>> login(@Query("userName") String userName, @Query("userPwd") String userPwd);

    @POST("app/getMenu")
    Observable<ResultMsg<List<Menu>>> getMenu();

    @FormUrlEncoded
    @POST("app/getMapInfo")
    Observable<ResultMsg<List<MapResult>>> getMapInfo(@Field("jsonIds") String jsonIds);

    @FormUrlEncoded
    @POST("app/getNextMapInfoByKey")
    Observable<ResultMsg<List<MapResult>>> getNextMapInfoByKey(@Field("key") String key,@Field("cacheKey") String cacheKey,@Field("currentLevel") String currentLevel);


    @FormUrlEncoded
    @POST("app/getPreMapInfoByKey")
    Observable<ResultMsg<List<MapResult>>> getPreMapInfoByKey(@Field("key") String key,@Field("cacheKey") String cacheKey,@Field("currentLevel") String currentLevel);

    @POST("app/getCoordinateInfo")
    Observable<ResultMsg<List<CoordinationInfo>>> getCoordinateInfo(@Query("coordinateId") String coordinateId);

}
