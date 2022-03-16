package com.websarva.wings.android.DAO;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Androidのデータベース(SQLite)を用いるためのデータベースヘルパークラス。
 * SQLiteOpenHelperを継承して作る。
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //データベースファイル名の定数フィールド
    private static final String DATABASE_NAME = "foodmanager.db";
    //バージョン情報の定数フィールド
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        //親クラスのコンストラクタの呼び出し
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            //テーブル作成用SQL文字列の作成
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE foodmanager (");
            sb.append("_id TEXT,");
            sb.append("foodname TEXT,");
            sb.append("foodstock NUMERIC,");
            sb.append("foodstockunit TEXT,");
            sb.append("purchasecount NUMERIC,");
            sb.append("usagecount NUMERIC");
            sb.append(");");
            String sql = sb.toString();
            //SQLの実行
            sqLiteDatabase.execSQL(sql);
        }catch (SQLException e){
            Log.e("FoodManagerApp","SQLite execution failed: "+e.getLocalizedMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
