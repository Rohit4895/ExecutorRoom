package com.example.roomdatabase2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.List;

public class Dummy {

    public interface ICallback {

        void success(List<Task> tasks);

        void fail();

    }

    public void patch(final Context context, final ICallback iCallback) {


        AppExecutor.getINSTANCE().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {

                final List<Task> taskList = DatabaseClient
                        .getInstance(context)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();



                AppExecutor.getINSTANCE().getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (taskList != null && taskList.size() != 0)
                            iCallback.success(taskList);
                        else
                            iCallback.fail();
                    }
                });

            }
        });

    }
}
