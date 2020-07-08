package com.example.exam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentAdvertisement extends Fragment {
    public static String NOTIFICATION = "ad_notification";
    public static String NOTIFICATION_DATA = "ad_data";
    private FragmentAdvertisementMessageCatcherBroadcast fragmentAdvertisementMessageCatcherBroadcast;
    private Callback mCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_advertisement, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.onAdvertisementClick();
                }
            }
        });
        initBroadcast();
        return view;
    }

    public interface Callback {

        void onAdvertisementClick();
    }

    public void setCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    void initBroadcast() {
        fragmentAdvertisementMessageCatcherBroadcast = new FragmentAdvertisementMessageCatcherBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(NOTIFICATION);
        getActivity().registerReceiver(fragmentAdvertisementMessageCatcherBroadcast, filter);
    }

    public static class FragmentAdvertisementMessageCatcherBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(NOTIFICATION_DATA)) {
                String messageText = intent.getStringExtra(NOTIFICATION_DATA);
                Toast.makeText(context, messageText, Toast.LENGTH_LONG).show();
            }
        }
    }
}
