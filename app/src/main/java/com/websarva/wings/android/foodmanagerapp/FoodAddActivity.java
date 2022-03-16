package com.websarva.wings.android.foodmanagerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.websarva.wings.android.DAO.DatabaseHelper;
import com.websarva.wings.android.DAO.FoodDAO;
import com.websarva.wings.android.entity.Food;

public class FoodAddActivity extends AppCompatActivity {

    private DatabaseHelper _helper; //データベースヘルパーオブジェクト

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add);

        //食材を追加するボタンオブジェクトを生成し、リスナインスタンスを設定
        Button btFoodAdd = findViewById(R.id.btFoodAdd);
        AddButtonClickListener addButtonListener = new AddButtonClickListener();
        btFoodAdd.setOnClickListener(addButtonListener);
        //データベースヘルパーオブジェクトを生成
        _helper = new DatabaseHelper(FoodAddActivity.this);
    }

    private class AddButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //EditTextから値を取得
            EditText etFoodName = findViewById(R.id.etFoodName);
            String foodName = etFoodName.getText().toString();
            EditText etFoodStockUnit = findViewById(R.id.etFoodStockUnit);
            String foodStockUnit = etFoodStockUnit.getText().toString();
            EditText etPurchaseCount = findViewById(R.id.etPurchaseCount);
            String purchaseCount = etPurchaseCount.getText().toString();
            EditText etUsageCount = findViewById(R.id.etUsageCount);
            String usageCount = etUsageCount.getText().toString();

            if (foodName.equals("") || foodStockUnit.equals("") || purchaseCount.equals("") || usageCount.equals("")) {
                //未入力の欄があるとき
                //トーストに表示する文字列を生成
                String msg = FoodAddActivity.this.getString(R.string.msg_input_check);
                //トースト表示
                Toast.makeText(FoodAddActivity.this, msg, Toast.LENGTH_SHORT).show();
            }else{
                //全ての欄が入力されている時
                //DAOを生成
                FoodDAO dao = new FoodDAO(_helper);
                //食材名でデータベースを検索し、該当するものがあれば食材名を、無ければnullを返却する。
                Food food = dao.findFoodByFoodName(foodName);
                if(food == null) {
                    //該当する食材名がなかった時
                    //purchaseCountとusageCountに値があったらdouble型に変換(値がない状態でparseDoubleしようとするとエラーになる)
                    double purchaseCountdouble = Double.parseDouble(purchaseCount);
                    double usageCountdouble = Double.parseDouble(usageCount);
                    //DAOのaddFoodメソッドで食材を追加
                    dao.addFood(foodName, 0, foodStockUnit, purchaseCountdouble, usageCountdouble);
                    //IntentでMainActivityに画面遷移
                    Intent intent = new Intent(FoodAddActivity.this, MainActivity.class);
                    FoodAddActivity.this.startActivity(intent);
                }else{
                    //該当する食材名がデータベースに存在した時
                    //トーストに表示する文字列を生成
                    String msg = food.getFoodName()+FoodAddActivity.this.getString(R.string.msg_foodname_check);
                    //トースト表示
                    Toast.makeText(FoodAddActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
            }
        }

    @Override
    public void onDestroy(){
        //データベースヘルパーオブジェクトを解放
        _helper.close();
        super.onDestroy();
    }
}