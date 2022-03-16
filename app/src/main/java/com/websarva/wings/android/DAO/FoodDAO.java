package com.websarva.wings.android.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.websarva.wings.android.entity.Food;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodDAO {
    DatabaseHelper _helper;

    /**
     * コンストラクタ
     * @param _helper データベースヘルパーオブジェクトを受け取る
     */
    public FoodDAO(DatabaseHelper _helper) {
        this._helper = _helper;
    }

    /**
     * 全ての食材情報を読み込むメソッド
     * @return foodList 全ての食材情報
     */
    public List<Food> findAllFoodList(){
        List<Food> foodList = new ArrayList<>(); //全食材情報を格納するリスト
        Food food; //foodListに食材情報を追加するためのFood型変数

        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        SQLiteDatabase db = _helper.getWritableDatabase();
        //全ての食材情報を取得
        String sql = "SELECT * FROM foodmanager";
        //SQLの実行
        Cursor cursor = db.rawQuery(sql,null);
        //SQL実行の戻り値であるカーソルオブジェクトをループさせてデータを取り出す
        while(cursor.moveToNext()){
            int idxNote = cursor.getColumnIndex("foodname");
            String foodName = cursor.getString(idxNote);
            idxNote = cursor.getColumnIndex("foodstock");
            double foodStock = cursor.getDouble(idxNote);
            idxNote = cursor.getColumnIndex("foodstockunit");
            String foodStockUnit= cursor.getString(idxNote);
            idxNote = cursor.getColumnIndex("purchasecount");
            double purchaseCount = cursor.getDouble(idxNote);
            idxNote = cursor.getColumnIndex("usagecount");
            double usageCount = cursor.getDouble(idxNote);
            food = new Food(foodName,foodStock,foodStockUnit, purchaseCount,usageCount);
            foodList.add(food);
        }
        //カーソルオブジェクトをクローズする
        cursor.close();
        //食材リストを返す
        return foodList;
    }

    /**
     * 食材名から食材情報を検索し、結果を返却するメソッド
     * @param foodName 食材名
     * @return food 食材情報(該当がない場合はnullを返却)
     */
    public Food findFoodByFoodName(String foodName){
        Food food = null; //該当する食材情報がない場合に備えて、nullで初期化しておく
        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        SQLiteDatabase db = _helper.getWritableDatabase();
        //全ての食材情報を取得
        String sql = "SELECT * FROM foodmanager WHERE foodname = '"+foodName+"'";
        //SQLの実行
        Cursor cursor = db.rawQuery(sql,null);
        //SQL実行の戻り値であるカーソルオブジェクトをループさせてデータを取り出す
        if(cursor.moveToFirst()){
            int idxNote = cursor.getColumnIndex("foodstock");
            double foodStock = cursor.getDouble(idxNote);
            idxNote = cursor.getColumnIndex("foodstockunit");
            String foodStockUnit= cursor.getString(idxNote);
            idxNote = cursor.getColumnIndex("purchasecount");
            double purchaseCount = cursor.getDouble(idxNote);
            idxNote = cursor.getColumnIndex("usagecount");
            double usageCount = cursor.getDouble(idxNote);
            food = new Food(foodName,foodStock,foodStockUnit, purchaseCount,usageCount);
        }
        //カーソルオブジェクトをクローズする
        cursor.close();
        //食材リストを返す
        return food;
    }

    /**
     * 食材情報をデータベースに追加する
     * @param foodName 食材名
     * @param foodStock 在庫数
     * @param foodStockUnit 単位(個とか枚とか)
     * @param purchaseCount 買う時の最低数量
     * @param usageCount 使う時の最低数量
     */
    public void addFood(String foodName, double foodStock, String foodStockUnit, double purchaseCount, double usageCount){
        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        SQLiteDatabase db = _helper.getWritableDatabase();
        //インサート用SQL文字列の用意
        String sqlInsert = "INSERT INTO foodmanager " +
                "(_id, foodname, foodstock, foodstockunit, purchasecount, usagecount) " +
                "VALUES (?,?, ?, ?, ?, ?)";
        //SQL文字列をもとにプリペアドステートメントを取得
        SQLiteStatement stmt = db.compileStatement(sqlInsert);
        //primary keyの_idに日付を設定
        Date date = new Date(); // 今日の日付
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String _id = dateFormat.format(date);
        //変数のバインド
        stmt.bindString(1,_id);
        stmt.bindString(2,foodName);
        stmt.bindDouble(3,foodStock);
        stmt.bindString(4,foodStockUnit);
        stmt.bindDouble(5,purchaseCount);
        stmt.bindDouble(6,usageCount);
        //インサートSQLの実行
        stmt.executeInsert();
    }

    public void updateStock(String foodName, Double foodStock) {
        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        SQLiteDatabase db = _helper.getWritableDatabase();
        //インサート用SQL文字列の用意
        String sql = "UPDATE foodmanager SET foodstock = ? WHERE foodname = ?";
        //SQL文字列をもとにプリペアドステートメントを取得
        SQLiteStatement stmt = db.compileStatement(sql);
        //変数のバインド
        stmt.bindDouble(1, foodStock);
        stmt.bindString(2,foodName);
        //デリートSQLの実行
        stmt.executeUpdateDelete();
    }

    /**
     * 食材名をもとにデータベースから食材情報を削除するメソッド
     * @param foodName 食材名
     */
    public void deleteFood(String foodName) {
        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        SQLiteDatabase db = _helper.getWritableDatabase();
        //インサート用SQL文字列の用意
        String sql = "DELETE FROM foodmanager WHERE foodname = ?";
        //SQL文字列をもとにプリペアドステートメントを取得
        SQLiteStatement stmt = db.compileStatement(sql);
        //変数のバインド
        stmt.bindString(1, foodName);
        //デリートSQLの実行
        stmt.executeUpdateDelete();
    }
}
