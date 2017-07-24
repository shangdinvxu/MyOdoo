package tarce.myodoo.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.myodoo.R;

/**
 * Created by zws on 2017/7/24.
 */

public class PdfActivity extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener {

    @InjectView(R.id.pdfView)
    PDFView pdfView;

    private final static int REQUEST_CODE = 42;
    private int pageNumber = 0;
    private Uri uri;
    private String pdfFileName;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        displayFromUri(uri);
    }

    private void displayFromUri(Uri uri) {
        pdfFileName = getFileName(uri);

        pdfView.fromUri(uri)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.e("zws", "title = " + meta.getTitle());
        Log.e("zws", "author = " + meta.getAuthor());
        Log.e("zws", "subject = " + meta.getSubject());
        Log.e("zws", "keywords = " + meta.getKeywords());
        Log.e("zws", "creator = " + meta.getCreator());
        Log.e("zws", "producer = " + meta.getProducer());
        Log.e("zws", "creationDate = " + meta.getCreationDate());
        Log.e("zws", "modDate = " + meta.getModDate());

        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e("zws", String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        File file = new File(path);
        if (file.exists()){
            file.delete();
        }
    }
}
