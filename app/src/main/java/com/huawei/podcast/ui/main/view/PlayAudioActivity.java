package com.huawei.podcast.ui.main.view;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.huawei.hms.api.bean.HwAudioPlayItem;
import com.huawei.hms.api.config.NotificationConfig;
import com.huawei.hms.audiokit.player.callback.HwAudioConfigCallBack;
import com.huawei.hms.audiokit.player.manager.HwAudioConfigManager;
import com.huawei.hms.audiokit.player.manager.HwAudioManager;
import com.huawei.hms.audiokit.player.manager.HwAudioManagerFactory;
import com.huawei.hms.audiokit.player.manager.HwAudioPlayerConfig;
import com.huawei.hms.audiokit.player.manager.HwAudioPlayerManager;
import com.huawei.hms.audiokit.player.manager.HwAudioQueueManager;
import com.huawei.hms.audiokit.player.manager.HwAudioStatusListener;
import com.huawei.hms.audiokit.player.manager.INotificationFactory;
import com.huawei.podcast.R;
import com.huawei.podcast.data.model.EpisodeModel;
import com.huawei.podcast.utils.MyService;
import com.huawei.podcast.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;


public class PlayAudioActivity extends AppCompatActivity implements View.OnClickListener {

    private static HwAudioManager mHwAudioManager;
    private HwAudioPlayerManager mHwAudioPlayerManager;
    private HwAudioConfigManager mHwAudioConfigManager;
    private HwAudioQueueManager mHwAudioQueueManager;
    NotificationCompat.Builder builder;
    private List<HwAudioStatusListener> mTempListeners = new CopyOnWriteArrayList<>();
    boolean isReallyPlaying = true;
    private List<HwAudioPlayItem> playList;
    List<HwAudioPlayItem> playItemList = new ArrayList<>();
    TextView txt_title, progressTextView, totalDurationTextView;
    ImageView playButtonImageView, nextSongImageView, previousSongImageView, img_back_arrow;
    SeekBar musicSeekBar;
    int position;
    private EpisodeModel eList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        txt_title = findViewById(R.id.txt_title);
        playButtonImageView = findViewById(R.id.playButtonImageView);
        nextSongImageView = findViewById(R.id.nextSongImageView);
        previousSongImageView = findViewById(R.id.previousSongImageView);
        musicSeekBar = findViewById(R.id.musicSeekBar);
        progressTextView = findViewById(R.id.progressTextView);
        totalDurationTextView = findViewById(R.id.totalDurationTextView);
        img_back_arrow = findViewById(R.id.img_back_arrow);
        playButtonImageView.setOnClickListener(this);
        nextSongImageView.setOnClickListener(this);
        previousSongImageView.setOnClickListener(this);
        playButtonImageView.setOnClickListener(this);
        img_back_arrow.setOnClickListener(this);

        eList = getIntent().getExtras().getParcelable("episode_list");
        position = getIntent().getExtras().getInt("position");
        initializeManagerAndGetPlayList(this);

        if (mHwAudioQueueManager != null) {
            setSongDetails(mHwAudioQueueManager.getCurrentPlayItem());
        }

        MyReceiver receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.menes.audiokittryoutapp.ON_NEW_TOKEN");
        PlayAudioActivity.this.registerReceiver(receiver, filter);
    }

    @Override
    public void onClick(View v) {
        final Drawable drawablePlay = getDrawable(R.drawable.ic_play_arrow);
        final Drawable drawablePause = getDrawable(R.drawable.ic_pause);
        switch (v.getId()) {
            case R.id.playButtonImageView:
                if (playButtonImageView.getDrawable().getConstantState().equals(drawablePlay.getConstantState())) {
                    if (mHwAudioPlayerManager != null) {
                        mHwAudioPlayerManager.play();
                        playButtonImageView.setImageDrawable(getDrawable(R.drawable.ic_pause));
                        isReallyPlaying = true;
                    }
                } else if (playButtonImageView.getDrawable().getConstantState().equals(drawablePause.getConstantState())) {
                    if (mHwAudioPlayerManager != null) {
                        mHwAudioPlayerManager.pause();
                        playButtonImageView.setImageDrawable(getDrawable(R.drawable.ic_play_arrow));
                        isReallyPlaying = false;
                    }
                }
                break;
            case R.id.nextSongImageView:
                if (mHwAudioPlayerManager != null) {
                    mHwAudioPlayerManager.playNext();
                    isReallyPlaying = true;
                }
                break;
            case R.id.previousSongImageView:
                if (mHwAudioPlayerManager != null) {
                    mHwAudioPlayerManager.playPre();
                    isReallyPlaying = true;
                }
                break;
            case R.id.img_back_arrow:
                onBackPressed();
                break;
        }
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("ccom.menes.audiokittryoutapp.ON_NEW_TOKEN".equals(intent.getAction())) {
                String token = intent.getStringExtra("token");
            }
        }
    }


    HwAudioStatusListener mPlayListener = new HwAudioStatusListener() {
        @Override
        public void onSongChange(HwAudioPlayItem hwAudioPlayItem) {
            setSongDetails(hwAudioPlayItem);
            if (mHwAudioPlayerManager.getOffsetTime() != -1 && mHwAudioPlayerManager.getDuration() != -1)
                updateSeekBar(mHwAudioPlayerManager.getOffsetTime(), mHwAudioPlayerManager.getDuration());
        }

        @Override
        public void onQueueChanged(List<HwAudioPlayItem> list) {
            if (mHwAudioPlayerManager != null && list.size() != 0 && !isReallyPlaying) {
                mHwAudioPlayerManager.play();
                isReallyPlaying = true;
                playButtonImageView.setImageDrawable(getDrawable(R.drawable.ic_pause));
            }
        }

        @Override
        public void onBufferProgress(int percent) {
        }

        @Override
        public void onPlayProgress(final long currentPosition, long duration) {
            updateSeekBar(currentPosition, duration);
        }

        @Override
        public void onPlayCompleted(boolean isStopped) {
            if (mHwAudioPlayerManager != null && isStopped) {
                mHwAudioPlayerManager.playNext();
            }
            isReallyPlaying = !isStopped;
        }

        @Override
        public void onPlayError(int errorCode, boolean isUserForcePlay) {
            Toast.makeText(PlayAudioActivity.this, "We cannot play this!!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPlayStateChange(boolean isPlaying, boolean isBuffering) {
            if (isPlaying || isBuffering) {
                playButtonImageView.setImageDrawable(getDrawable(R.drawable.ic_pause));
                isReallyPlaying = true;
            } else {
                playButtonImageView.setImageDrawable(getDrawable(R.drawable.ic_play_arrow));
                isReallyPlaying = false;
                if (builder != null)
                    builder.setOngoing(false); //probably not working as intended
            }

        }
    };

    public void updateSeekBar(final long currentPosition, long duration) {
        musicSeekBar.setMax((int) (duration / 1000));
        if (mHwAudioPlayerManager != null) {
            int mCurrentPosition = (int) (currentPosition / 1000);
            musicSeekBar.setProgress(mCurrentPosition);
            setProgressText(mCurrentPosition);
        }

        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mHwAudioPlayerManager != null && fromUser) {
                    mHwAudioPlayerManager.seekTo(progress * 1000);
                }
                if (!isReallyPlaying) {
                    setProgressText(progress);
                }
            }
        });
    }

    public void setProgressText(int progress) {
        String progressText = String.format(Locale.US, "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(progress * 1000),
                TimeUnit.MILLISECONDS.toSeconds(progress * 1000) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(progress * 1000))
        );
        progressTextView.setText(progressText);
    }

    public void setSongDetails(HwAudioPlayItem currentItem) {
        if (currentItem != null) {
            if (currentItem.getAudioTitle() != null) {
                if (!currentItem.getAudioTitle().equals(""))
                    txt_title.setText(currentItem.getAudioTitle());
                else
                    txt_title.setText("You can't play song");
            } else {
                txt_title.setText("You can't play song");
            }
            progressTextView.setText("00:00");
            totalDurationTextView.setText("2:11");
        } else {
            txt_title.setText("You can't play song");

        }
    }


    @SuppressLint("StaticFieldLeak")
    public void initializeManagerAndGetPlayList(final Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                HwAudioPlayerConfig hwAudioPlayerConfig = new HwAudioPlayerConfig(context);
                HwAudioManagerFactory.createHwAudioManager(hwAudioPlayerConfig, new HwAudioConfigCallBack() {
                    @RequiresApi(api = Build.VERSION_CODES.R)
                    @Override
                    public void onSuccess(HwAudioManager hwAudioManager) {
                        try {
                            mHwAudioManager = hwAudioManager;
                            mHwAudioPlayerManager = hwAudioManager.getPlayerManager();
                            mHwAudioConfigManager = hwAudioManager.getConfigManager();
                            mHwAudioQueueManager = hwAudioManager.getQueueManager();

                            playList = getOnlinePlaylist();

                            if (playList.size() > 0) {
                                Collections.sort(playList, new Comparator<HwAudioPlayItem>() {
                                    @Override
                                    public int compare(final HwAudioPlayItem object1, final HwAudioPlayItem object2) {
                                        return object1.getAudioTitle().compareTo(object2.getAudioTitle());
                                    }
                                });
                            }

                            doListenersAndNotifications();


                        } catch (Exception e) {
                            Log.e("TAG", "player init fail", e);
                        }
                    }

                    @Override
                    public void onError(int errorCode) {
                        Log.e("TAG", "init err:" + errorCode);
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                addListener(mPlayListener);
                play();
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    private void play() {
        if (mHwAudioPlayerManager != null && mHwAudioQueueManager != null && mHwAudioQueueManager.getAllPlaylist() != null) {
            if (mHwAudioQueueManager.getAllPlaylist() == playItemList) {
                mHwAudioPlayerManager.play(0);
            } else {
                mHwAudioPlayerManager.playList(playList, position, 0);
                mHwAudioPlayerManager.setPlayMode(0);
                mHwAudioQueueManager.setPlaylist(playList);
                Log.w("Playlist", mHwAudioQueueManager.getAllPlaylist() + "");
            }
        }
    }

    public List<HwAudioPlayItem> getOnlinePlaylist() {
        for (int i = 0; i < eList.getCollection().size(); i++) {
            HwAudioPlayItem audioPlayItem = new HwAudioPlayItem();
            audioPlayItem.setAudioId(String.valueOf(i));
            audioPlayItem.setSinger("Matt Rubin");
            audioPlayItem.setOnlinePath(eList.getCollection().get(i).getEnclosureUrl());
            audioPlayItem.setOnline(1);
            audioPlayItem.setAudioTitle(eList.getCollection().get(i).getTitle());
            playItemList.add(audioPlayItem);
        }
        return playItemList;
    }

    private void doListenersAndNotifications() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (HwAudioStatusListener listener : mTempListeners) {
                    try {
                        mHwAudioManager.addPlayerStatusListener(listener);
                    } catch (RemoteException e) {
                        Log.e("TAG", "TAG", e);
                    }
                }

                mHwAudioConfigManager.setSaveQueue(true);
                mHwAudioConfigManager.setNotificationFactory(new INotificationFactory() {
                    @Override
                    public Notification createNotification(NotificationConfig notificationConfig) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                            builder = new NotificationCompat.Builder(getApplication(), null);
                            RemoteViews remoteViews = new RemoteViews(getApplication().getPackageName(), R.layout.notification_player);
                            builder.setContent(remoteViews);
                            builder.setSmallIcon(R.drawable.ic_share);
                            builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                            builder.setCustomBigContentView(remoteViews);
                            NotificationUtils.addChannel(getApplication(), NotificationUtils.NOTIFY_CHANNEL_ID_PLAY, builder);
                            boolean isQueueEmpty = mHwAudioManager.getQueueManager().isQueueEmpty();
                            boolean isPlaying = mHwAudioManager.getPlayerManager().isPlaying() && !isQueueEmpty;
                            remoteViews.setImageViewResource(R.id.image_toggle, isPlaying ? R.drawable.ic_pause : R.drawable.ic_play_arrow);
                            HwAudioPlayItem playItem = mHwAudioManager.getQueueManager().getCurrentPlayItem();
                            remoteViews.setTextViewText(R.id.text_song, playItem.getAudioTitle());
                            remoteViews.setTextViewText(R.id.text_artist, playItem.getSinger());
                            remoteViews.setImageViewResource(R.id.image_last, R.drawable.ic_skip_previous);
                            remoteViews.setImageViewResource(R.id.image_next, R.drawable.ic_skip_next);
                            remoteViews.setOnClickPendingIntent(R.id.image_last, notificationConfig.getPrePendingIntent());
                            remoteViews.setOnClickPendingIntent(R.id.image_toggle, notificationConfig.getPlayPendingIntent());
                            remoteViews.setOnClickPendingIntent(R.id.image_next, notificationConfig.getNextPendingIntent());
                            remoteViews.setOnClickPendingIntent(R.id.image_close, getCancelPendingIntent());
                            remoteViews.setOnClickPendingIntent(R.id.layout_content, getMainIntent());

                            return builder.build();
                        } else {
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication(), null);
                            return builder.build();
                        }
                    }
                });
            }
        });
    }


    private PendingIntent getCancelPendingIntent() {
        Log.i("TAG", "getCancelPendingIntent");
        Intent closeIntent = new Intent("com.menes.audiokittryoutapp.cancel_notification");
        closeIntent.setPackage(getApplication().getPackageName());
        return PendingIntent.getBroadcast(getApplication(), 2, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getMainIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setClass(getApplication().getBaseContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        return PendingIntent.getActivity(getApplication(), 0, intent, 0);
    }

    public void addListener(HwAudioStatusListener listener) {
        if (mHwAudioManager != null) {
            try {
                mHwAudioManager.addPlayerStatusListener(listener);
            } catch (RemoteException e) {
                Log.e("TAG", "TAG", e);
            }
        } else {
            mTempListeners.add(listener);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}











