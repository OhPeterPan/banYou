package com.banutech.collectiontreasure.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.banutech.collectiontreasure.R;

public class QRcodeDialog extends Dialog {

    private Bitmap bitmap;
    private ImageView ivDialogQRcode;
    private TextView tvDialogQRcodeClose;

    public QRcodeDialog(@NonNull Context context, Bitmap bitmap) {
        super(context, R.style.Dialog);
        this.bitmap = bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_qrcode);
        ivDialogQRcode = findViewById(R.id.ivDialogQRcode);
        tvDialogQRcodeClose = findViewById(R.id.tvDialogQRcodeClose);
        tvDialogQRcodeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null && !bitmap.isRecycled())
                    bitmap.recycle();
                dismiss();
            }
        });
        if (bitmap != null && !bitmap.isRecycled())
            ivDialogQRcode.setImageBitmap(bitmap);
    }
}
