package gc.com.gcmapapp.http;

public class ApiException extends RuntimeException {


    private  String message;


    public ApiException(String message) {
           this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
