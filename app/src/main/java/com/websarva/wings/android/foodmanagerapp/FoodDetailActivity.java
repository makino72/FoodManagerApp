package com.websarva.wings.android.foodmanagerapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.websarva.wings.android.DAO.DatabaseHelper;
import com.websarva.wings.android.DAO.FoodDAO;
import com.websarva.wings.android.entity.Food;

public class FoodDetailActivity extends AppCompatActivity {

    private DatabaseHelper _helper;//データベースヘルパーオブジェクト
    Food food; //食材情報
    Double setStock; //セットしたい在庫数
    EditText etFoodStock; //在庫数を表示している画面部品(EditText)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //データベースを使う準備としてデータベースヘルパーオブジェクトを作成
        _helper = new DatabaseHelper(FoodDetailActivity.this);

        //MainActivityからのIntentを取得する
        Intent intent = getIntent();
        //IntentからFoodNameを取得する
        String foodName = intent.getStringExtra("FoodName");
        //データベースから該当する食材名の食材情報を取得する
        FoodDAO dao = new FoodDAO(_helper);
        food = dao.findFoodByFoodName(foodName);
        //TextViewの取得
        TextView tvFoodName=findViewById(R.id.foodName);
        //TextViewにfoodNameをセットする
        tvFoodName.setText(food.getFoodName());

        //EditTextを取得
        etFoodStock = findViewById(R.id.etFoodStock);
        //食材情報の在庫数を変数に代入
        setStock = food.getFoodStock();
        //EditText内に在庫数を表示
        etFoodStock.setText(String.valueOf(setStock));

        /*在庫数の増加・減少ボタンに表示する文字を設定*/
        Button btIncreaseStock = findViewById(R.id.btIncreaseStock);
        btIncreaseStock.setText("＋\n("+food.getPurchaseCount()+food.getFoodStockUnit()+")");
        Button btDecreaseStock = findViewById(R.id.btDecreaseStock);
        btDecreaseStock.setText("－\n("+food.getUsageCount()+food.getFoodStockUnit()+")");
    }

    public void onIncreaseStock(View view){
        //在庫数を増やす処理
        setStock += food.getPurchaseCount();
        //EditText内に在庫数を表示
        etFoodStock.setText(String.valueOf(setStock));
    }

    public void onDecreaseStock(View view){
        //在庫数を減らす処理
        setStock -= food.getUsageCount();
        //EditText内に在庫数を表示
        etFoodStock.setText(String.valueOf(setStock));
    }

    public void onUpdateStock(View view){
        //DAOを生成
        FoodDAO dao = new FoodDAO(_helper);
        //画面に表示されているEditTextの在庫数を取得する
        double setStockDouble = Double.parseDouble(etFoodStock.getText().toString());
        //食材情報を削除するメソッド
        dao.updateStock(food.getFoodName(),setStockDouble);
        //MainActivityからFoodAddActivityへ遷移するIntentを宣言
        Intent intent = new Intent(FoodDetailActivity.this, MainActivity.class);
        //FoodAddActivityを起動する
        FoodDetailActivity.this.startActivity(intent);
    }

    public void onDeleteFood(View view){
        //削除するボタンを押下した時、確認ダイアログを表示する(https://qiita.com/suzukihr/items/8973527ebb8bb35f6bb8から引用)
        new AlertDialog.Builder(FoodDetailActivity.this)
                .setTitle(R.string.dialog_delete_title)
                .setMessage(R.string.dialog_delete_desc)
                .setPositiveButton(R.string.dialog_bt_OK, new DialogInterface.OnClickListener() {
                    //OKを押下した時の処理
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //DAOを生成
                        FoodDAO dao = new FoodDAO(_helper);
                        //食材情報を削除するメソッド
                        dao.deleteFood(food.getFoodName());
                        //MainActivityからFoodAddActivityへ遷移するIntentを宣言
                        Intent intent = new Intent(FoodDetailActivity.this, MainActivity.class);
                        //FoodAddActivityを起動する
                        FoodDetailActivity.this.startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.dialog_bt_Cancel, null)
                .show();
    }

    /**
     * FoodDetailActivity終了時に行う処理
     */
    @Override
    protected void onDestroy(){
        //データベースヘルパーオブジェクトを
        _helper.close();
        super.onDestroy();
    }
}
