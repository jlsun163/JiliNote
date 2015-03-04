package jili.jilinote;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import jili.jilinote.view.WrittingView;


public class WrittingActivity extends BaseActivity {
    private WrittingView writtingView = null;//画板视图
    private RadioGroup colorgroup = null;//颜色组
    private SeekBar pensizebar = null;//滚动条，用于设置笔刷大小
    private Button clearButton = null;//清除按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inite();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_writting;
    }

    /**
     * 初始化
     */
    private void inite(){
        writtingView = (WrittingView)findViewById(R.id.writting);
        //选择颜色
        RadioButton blackbutton = (RadioButton)findViewById(R.id.blackbutton);
        blackbutton.setChecked(true);//开始时选择黑色
        colorgroup = (RadioGroup)findViewById(R.id.colorgroupid);
        //监听颜色选择
        colorgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.blackbutton:
                        writtingView.setcolor(Color.BLACK);
                        break;
                    case R.id.greenbutton:
                        writtingView.setcolor(Color.GREEN);
                        break;
                    case R.id.bluebutton:
                        writtingView.setcolor(Color.BLUE);
                        break;
                    case R.id.redbutton:
                        writtingView.setcolor(Color.RED);
                        break;

                    default:
                        break;
                }
            }
        });
        //选择笔的大小
        pensizebar = (SeekBar)findViewById(R.id.pensize);
        //设置滑动条监听
        pensizebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                writtingView.setPenSize(1.0f+progress);//当滚动条滑动时，调用
            }
        });
        //清除按钮
        clearButton = (Button)findViewById(R.id.clearbutton);
        //设置按钮监听
        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                writtingView.clearall();
            }
        });

    }

    private Bitmap getViewBitmap(View view) {
        view.clearFocus();
        view.setPressed(false);

        boolean willNotCache = view.willNotCacheDrawing();
        view.setWillNotCacheDrawing(false);

        int color = view.getDrawingCacheBackgroundColor();
        view.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            view.destroyDrawingCache();
        }
        view.buildDrawingCache();
        Bitmap cacheBitmap = view.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        view.destroyDrawingCache();
        view.setWillNotCacheDrawing(willNotCache);
        view.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }
}
