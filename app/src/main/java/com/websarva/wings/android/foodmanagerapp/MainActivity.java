package com.websarva.wings.android.foodmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.websarva.wings.android.DAO.DatabaseHelper;
import com.websarva.wings.android.DAO.FoodDAO;
import com.websarva.wings.android.entity.Food;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DatabaseHelper _helper;//データベースヘルパーオブジェクト

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //データベースを使う準備としてデータベースヘルパーオブジェクトを作成
        _helper = new DatabaseHelper(MainActivity.this);

        //RecyclerViewを取得
        RecyclerView lvMenu = findViewById(R.id.lvMenu);
        //LinearLayoutManagerオブジェクトを生成
        LinearLayoutManager layout = new LinearLayoutManager(MainActivity.this);
        //RecyclerViewにレイアウトマネージャーとしてLinearLayoutManagerを設定
        lvMenu.setLayoutManager(layout);
        //定食メニューリストデータを生成
        List<Food> foodList = createFoodList();
        //アダプタオブジェクトを生成
        MainAdapter adapter = new MainAdapter(MainActivity.this,foodList);
        //RecyclerViewにアダプタオブジェクトを設定
        lvMenu.setAdapter(adapter);

        //区切り線用のオブジェクトを生成
        DividerItemDecoration decorator = new DividerItemDecoration(MainActivity.this, layout.getOrientation());
        //RecyclerViewに区切り線オブジェクトを設定
        lvMenu.addItemDecoration(decorator);
    }

    /**
     * データベース内の全食材情報を取得するメソッド
     * @return foodList データベース内の全食材情報
     */
    private List<Food> createFoodList(){
        //DAOを生成
        FoodDAO dao = new FoodDAO(_helper);
        //データベース内の全ての食材リストを取得する
        List<Food> foodList = dao.findAllFoodList();
        //食材リストを返す
        return foodList;
    }

    /**
     * FloatingActionButtonが押下された時にFoodAddActivityに遷移するメソッド
     * onAddButtonClickはactivity_main.xmlにてonClick属性に登録済
     * @param view
     */
    public void onAddButtonClick(View view){
        //MainActivityからFoodAddActivityへ遷移するIntentを宣言
        Intent intent = new Intent(MainActivity.this, FoodAddActivity.class);
        //FoodAddActivityを起動する
        MainActivity.this.startActivity(intent);
    }

    /**
     * MainActivity終了時に行う処理
     */
    @Override
    protected void onDestroy(){
        //データベースヘルパーオブジェクトを
        _helper.close();
        super.onDestroy();
    }
}