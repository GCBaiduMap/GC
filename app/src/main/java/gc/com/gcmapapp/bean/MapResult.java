package gc.com.gcmapapp.bean;

import android.os.Bundle;

/**
 * Created by jiguijun on 2017/12/11.
 */

public class MapResult {
    private String currentLevel;
    private String preLevel;
    private String cacheKey;
    private String totalitem;
    private double latitude;
    private double longitude;
    private String preKey;
    private String ids;
    private String nextLevel;
    private String text;
    private String key;

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getPreLevel() {
        return preLevel;
    }

    public void setPreLevel(String preLevel) {
        this.preLevel = preLevel;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getTotalitem() {
        return totalitem;
    }

    public void setTotalitem(String totalitem) {
        this.totalitem = totalitem;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPreKey() {
        return preKey;
    }

    public void setPreKey(String preKey) {
        this.preKey = preKey;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Bundle toBundle(){
        Bundle bundle = new Bundle();
        bundle.putString("currentLevel", currentLevel);
        bundle.putString("preLevel", preLevel);
        bundle.putString("cacheKey", cacheKey);
        bundle.putString("totalitem", totalitem);
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putString("preKey", preKey);
        bundle.putString("ids", ids);
        bundle.putString("nextLevel", nextLevel);
        bundle.putString("text", text);
        bundle.putString("key", key);
        return bundle;
    }
}

