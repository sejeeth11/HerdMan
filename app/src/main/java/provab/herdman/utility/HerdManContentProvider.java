package provab.herdman.utility;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by PTBLR-1057 on 5/20/2016.
 */
public class HerdManContentProvider extends ContentProvider {

    DatabaseHelper databaseHelper;

    public static final String AUTHORITY="provab.herdman.db";

    public static final String USER_TABLE_NAME="user";
    public static final int USER_TABLE_INDEX=1;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY,USER_TABLE_NAME,USER_TABLE_INDEX);
    }

    @Override
    public boolean onCreate() {
//        databaseHelper=DatabaseHelper.getDatabaseHelperInstance();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int tableName=uriMatcher.match(uri);
        switch (tableName){
            case USER_TABLE_INDEX:
//                databaseHelper.inser
                break;
        }
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
