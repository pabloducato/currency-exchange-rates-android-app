package com.android.app.currency.exchange.rates.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.adapters.ScannerAdapter;
import com.android.app.currency.exchange.rates.items.ScannerItem;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.ArrayList;

public class ScannerFragment extends Fragment implements ScannerAdapter.OnItemClickListener {

    private final ArrayList<ScannerItem> scannerList = new ArrayList<>();
    public IntentResult intentResult = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_scanner, container, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.scanner_recycler_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        if (scannerList != null || scannerList.size() > 0) {
            scannerList.clear();
        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        scannerList.add(new ScannerItem(R.drawable.ic_baseline_qr_code_scanner_24, "Uruchom skaner kodów QR"));

        ScannerAdapter adapter = new ScannerAdapter(scannerList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        super.onCreate(savedInstanceState);
        return fragmentView;
    }

    @Override
    public void onItemClick(int position) {
        if (position == 0) {
            scanCode();
        }
    }

    private void scanCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.setCaptureActivity(CaptureActivity.class);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Trwa skanowanie kodu");
        intentIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        intentResult = null;
        intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            Log.e(">>>>", " " + intentResult.getContents());
            if (intentResult.getContents() != null) {
                if (intentResult.getContents().matches("^(http|https|ftp)://.*$")) {
                    try {
                        String url = intentResult.getContents();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                openDialog(intentResult.getContents());
            } else {
                openDialog("Brak wyników");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void openDialog(String barcode) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        dialog.setCancelable(true);
        dialog.setMessage(barcode);
        dialog.setTitle("Wynik procesu skanowania");
        dialog.setPositiveButton("Zeskanuj ponownie", (dialog1, which) -> {
            scanCode();
            dialog1.dismiss();
        });
        dialog.setNegativeButton("Anuluj", (dialog12, which) -> {
            dialog12.dismiss();
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

}
