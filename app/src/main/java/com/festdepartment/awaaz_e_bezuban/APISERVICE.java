package com.festdepartment.awaaz_e_bezuban;

import com.festdepartment.awaaz_e_bezuban.Notification.MyResponse;
import com.festdepartment.awaaz_e_bezuban.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APISERVICE {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAFPyylcE:APA91bGnhgxo9hC2gY8mdirPRO_B33mOrXiGFgzaXPlu2C-xUtGJEFJxBmadML3ljG7QQV6G9L629hmW7038BV3jObtu0w6mqJVozXhtKybjV7LrDcg7wwwzx1X28YrP4DjVsEvmOk59"
    })

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
