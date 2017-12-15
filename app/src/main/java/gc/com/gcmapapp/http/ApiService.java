package gc.com.gcmapapp.http;

import java.util.List;

import gc.com.gcmapapp.bean.Login;
import gc.com.gcmapapp.bean.MapResult;
import gc.com.gcmapapp.bean.Menu;
import gc.com.gcmapapp.bean.ResultMsg;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {


    @POST("app/login")
    Observable<ResultMsg<Login>> login(@Query("userName") String userName, @Query("userPwd") String userPwd);

    @POST("app/getMenu")
    Observable<ResultMsg<List<Menu>>> getMenu();

    @POST("app/getMapInfo")
    Observable<ResultMsg<List<MapResult>>> getMapInfo(@Query("jsonIds") String jsonIds);

    @POST("app/getCoordinateInfo")
    Observable<ResultMsg<List<MapResult>>> getCoordinateInfo(@Query("coordinateId") String coordinateId);

}