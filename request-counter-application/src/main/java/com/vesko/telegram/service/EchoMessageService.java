package com.vesko.telegram.service;

import com.vesko.telegram.dto.EchoMessageRequest;
import com.vesko.telegram.dto.EchoMessageResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Сервис для отпавки REST запросов на контроллер {@link  com.vesko.telegram.controller.MessageController}
 */
public interface EchoMessageService {
    @POST("/receiveEchoMessage")
    Call<EchoMessageResponse> echoMessageRequest(@Body EchoMessageRequest echoMessageRequest);
}
