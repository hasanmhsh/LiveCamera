package com.hms.sra.livecamera;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.media.SyncParams;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.widget.MediaController;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener, MediaPlayer.OnBufferingUpdateListener {




    private Camera mCamera;
    private TextureView mTextureView;
    private static final String DVR_URL = "rtsp://hasan:gigabyte@192.168.1.21/user=admin_password=tlJwpbo6_channel=0_stream=0.sdp";
    private MediaController mediacontroller;
    private MediaPlayer mPlayer;
    private Surface mSurface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            mediacontroller = new MediaController(this);
            mPlayer = new MediaPlayer();

            Uri video = Uri.parse(DVR_URL);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(this,video);
            //mPlayer.setAudioSessionId(6);
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mPlayer.start();

                    mPlayer.setVolume(99.99999f,99.99999f);
                    //mPlayer.seekTo(0);
                }
            });
            mPlayer.setOnBufferingUpdateListener(this);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        mTextureView = (TextureView)findViewById(R.id.texture_view);
        mTextureView.setSurfaceTextureListener(this);

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            mSurface = new Surface(surface);
            SurfaceHolder sh = new SurfaceHolder() {
                @Override
                public void addCallback(Callback callback) {

                }

                @Override
                public void removeCallback(Callback callback) {

                }

                @Override
                public boolean isCreating() {
                    return false;
                }

                @Override
                public void setType(int type) {

                }

                @Override
                public void setFixedSize(int width, int height) {

                }

                @Override
                public void setSizeFromLayout() {

                }

                @Override
                public void setFormat(int format) {

                }

                @Override
                public void setKeepScreenOn(boolean screenOn) {

                }

                @Override
                public Canvas lockCanvas() {
                    return null;
                }

                @Override
                public Canvas lockCanvas(Rect dirty) {
                    return mSurface.lockCanvas(dirty);
                }

                @Override
                public void unlockCanvasAndPost(Canvas canvas) {
                    mSurface.unlockCanvasAndPost(canvas);
                }

                @Override
                public Rect getSurfaceFrame() {
                    return null;
                }

                @Override
                public Surface getSurface() {
                    return mSurface;
                }
            };
            mPlayer.setDisplay(sh);
            mPlayer.prepareAsync();
            //mPlayer.start();

            //mPlayer.prepare();
            //mPlayer.seekTo(0);
            //mPlayer.start();


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Ignored, Camera does all the work for us
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mPlayer.stop();
        mPlayer.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Invoked every time there's a new Camera preview frame
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        //mPlayer.reset();
    }
}
