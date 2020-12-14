package dennis.sung.custombottomdialogdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import dennis.sung.custombottomdialog.CustomBottomDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CustomBottomDialog.OnListItemClickedListener {
    private BottomSheetDialog bottomSheetDialog;
    private CustomBottomDialog customBottomDialog;
    private List<String> data;
    private int dataSize;
    private TextView tvDataSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDataSize = findViewById(R.id.tvDataSize);
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dataSize = progress;
                tvDataSize.setText("Data Size : " + dataSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                iniData();
            }
        });
        dataSize = seekBar.getProgress();
        iniData();
    }

    private void iniData(){
        if(data == null){
            data = new ArrayList<>();
        }
        data.clear();
        for(int i=0;i<dataSize;i++){
            data.add("item " + i);
        }
    }

    public void showCustomBottomDialog(View v){
        customBottomDialog = new CustomBottomDialog(this, R.style.ThemeOverlay_App_BottomSheetDialog, data);
        customBottomDialog.setOnListItemClickedListener(this);
//        customBottomDialog.setBottomText("Ohohohoh");
//        customBottomDialog.setBottomTextBold(false);
//        customBottomDialog.setBottomTextColor(getResources().getColor(R.color.colorBlue));
        customBottomDialog.setAllTextColor(getResources().getColor(R.color.colorRed));
//        customBottomDialog.setAllTextColor(Color.BLUE);
//        customBottomDialog.setAllTextBold(true);
//        customBottomDialog.setTextColor(getResources().getColor(R.color.colorBlue), 0);
        customBottomDialog.setTextColor(Color.BLUE, 4);
        customBottomDialog.setTextBold(true, 3);
        customBottomDialog.show();
    }

    public void showBottomDialog(View v){
        if(bottomSheetDialog == null){
            bottomSheetDialog = new BottomSheetDialog(this, R.style.ThemeOverlay_App_BottomSheetDialog);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_settings, null);
            view.findViewById(R.id.tv_copy_link).setOnClickListener(this);
            view.findViewById(R.id.tv_qr_code).setOnClickListener(this);
            view.findViewById(R.id.tv_share).setOnClickListener(this);
            view.findViewById(R.id.tv_edit).setOnClickListener(this);
            view.findViewById(R.id.tv_delete).setOnClickListener(this);
            view.findViewById(R.id.tv_cancel).setOnClickListener(this);
            bottomSheetDialog.setContentView(view);
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        }
        bottomSheetDialog.show();
    }

    @Override
    public void onClick(View view) {
        bottomSheetDialog.dismiss();
        String msg = "";
        switch (view.getId()){
            case R.id.tv_copy_link:
                msg = "You click copy link";
                break;
            case R.id.tv_qr_code:
                msg = "You click QR code";
                break;
            case R.id.tv_share:
                msg = "You click share";
                break;
            case R.id.tv_edit:
                msg = "You click edit";
                break;
            case R.id.tv_delete:
                msg = "You click delete";
                break;
            case R.id.tv_cancel:
                msg = "You click cancel";
                break;
        }
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onListItemClicked(int position) {
        customBottomDialog.dismiss();
        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content)
                , "Click " + data.get(position)
                , Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
