package com.example.vovch.listogram_20.data_layer.async_tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.vovch.listogram_20.ActiveActivityProvider;
import com.example.vovch.listogram_20.data_types.UserGroup;

/**
 * Created by vovch on 06.01.2018.
 */

public class GroupsGetterTask extends AsyncTask <String, Void, UserGroup[]> {
    private Context applicationContext;
    private ActiveActivityProvider activeActivityProvider;
    private String userId;
    private UserGroup[] groups;

    @Override
    public UserGroup[] doInBackground(String... loginPair) {
        UserGroup[] result = null;
        activeActivityProvider = (ActiveActivityProvider) applicationContext;
        result = activeActivityProvider.dataExchanger.getGroupsFromWeb();
        return result;
    }

    @Override
    public void onPostExecute(UserGroup[] result) {
        if(result != null) {
            activeActivityProvider.showGroupsGottenGood(result);
        }
        else{
            activeActivityProvider.showGroupsGottenBad(result);
        }
    }
    public void setApplicationContext(Context ctf){
        applicationContext = ctf;
    }
}
