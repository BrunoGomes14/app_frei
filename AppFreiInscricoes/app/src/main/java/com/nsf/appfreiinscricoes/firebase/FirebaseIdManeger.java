package com.nsf.appfreiinscricoes.firebase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.nsf.appfreiinscricoes.model.Requests.LoginRequestModel;
import com.nsf.appfreiinscricoes.requestsAPI.LoginRequests;
import com.nsf.appfreiinscricoes.interfaces.Callbacks.RetornaStringCallback;
import com.nsf.appfreiinscricoes.model.ErrorModel;
import com.nsf.appfreiinscricoes.model.Requests.LogoutRequest;
import com.nsf.appfreiinscricoes.model.TbFirebaseTokens;
import com.nsf.appfreiinscricoes.ultil.Ultil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FirebaseIdManeger extends FirebaseMessagingService
{
    FirebaseInstanceId instance;
    TbFirebaseTokens tokenModel;
    LoginRequests loginRequests = new LoginRequests();

    @Override
    public void onNewToken(@NonNull String token)
    {
        super.onNewToken(token);

        tokenModel = new TbFirebaseTokens();
        tokenModel.oldToken = Ultil.retornaTokenFirebase(this);
        tokenModel.newToken = token;

        loginRequests.AlteraToken(tokenModel, Ultil.retornaTokenAuth(this),this);
    }

    public void retornaToken(RetornaStringCallback callback)
    {
        instance = FirebaseInstanceId.getInstance();

        instance.getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                if (task.isSuccessful())
                {
                    callback.onSucess(task.getResult().getToken());

                }
                else
                {
                    ErrorModel error = new ErrorModel();
                    error.setCodigoErro(500);
                    error.setMensagemErro(task.getException().getMessage());
                    callback.OnError(error);
                }
            }
        });
    }
}
