package com.websarva.wings.android.foodmanagerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.websarva.wings.android.entity.Food;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerListViewHolder>{
    //コンテキスト(MainActivity)を取得するフィールド
    private Context _context;
    //リストデータを保持するフィールド
    private List<Food> _listData;

    //コンストラクタ
    public MainAdapter(Context context, List<Food> listData){
        //コンテキスト
        _context=context;
        //引数のリストデータをフィールドに格納
        _listData = listData;
    }

    /**
     * ViewHolderクラス
     */
    public class RecyclerListViewHolder extends RecyclerView.ViewHolder{
        //リスト1行文中で食材名と在庫数と単位を表示する画面部品
        public TextView _tvFoodNameRow;
        public TextView _tvFoodStockRow;
        public TextView _tvFoodStockUnitRow;

        //コンストラクタ
        public RecyclerListViewHolder(View itemView){
            //親クラスの呼び出し
            super(itemView);
            //引数で渡されたリスト1行分の画面部品中から表示に使われるTextViewを取得
            _tvFoodNameRow = itemView.findViewById(R.id.tvFoodNameRow);
            _tvFoodStockRow = itemView.findViewById(R.id.tvFoodStockRow);
            _tvFoodStockUnitRow = itemView.findViewById(R.id.tvFoodStockUnitRow);
        }
    }

    /**
     * ViewHolderを作成するメソッド
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MainAdapter.RecyclerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //レイアウトインフレーターを取得
        LayoutInflater inflater = LayoutInflater.from(_context);
        //food_row.xmlをインフレートし、1行分の画面部品とする
        View view = inflater.inflate(R.layout.food_row,parent,false);
        //インフレートされた1行分の画面部品にリスナを設定
        view.setOnClickListener(new MainAdapter.ItemClickListener());
        //ビューホルダオブジェクトを生成
        MainAdapter.RecyclerListViewHolder holder = new MainAdapter.RecyclerListViewHolder(view);
        //生成したビューホルダをリターン
        return holder;
    }

    /**
     * RecyclerViewのViewHolderに値を入れるメソッド
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MainAdapter.RecyclerListViewHolder holder, int position){
        //リストデータから該当1行分のデータを取得
        Food item = _listData.get(position);
        //メニュー名文字列を取得
        String foodName = item.getFoodName();
        double foodStock = item.getFoodStock();
        String foodStockUnit = item.getFoodStockUnit();
        //表示用に在庫数を文字列に変換
        String foodStockStr = String.valueOf(foodStock);
        //メニュー名と金額をビューホルダ中のTextViewに設定
        holder._tvFoodNameRow.setText(foodName);
        holder._tvFoodStockRow.setText(foodStockStr);
        holder._tvFoodStockUnitRow.setText(foodStockUnit);
    }

    /**
     * RecyclerViewのアイテム数を数えるメソッド
     * @return _listData.size() リストデータ中の件数をリターン
     */
    @Override
    public int getItemCount(){
        //リストデータ中の件数をリターン
        return _listData.size();
    }

    /**
     * RecyclerViewの画面部品がクリックされた時の動作を実装するリスナークラス
     */
    private class ItemClickListener implements View.OnClickListener{
        /**
         *RecyclerViewのアイテムが押下された時の処理メソッド
         * @param view RecyclerViewの画面部品(food_row.xml)
         */
        @Override
        public void  onClick(View view){
            //タップされたLinearLayout内にあるメニュー表示TextViewを取得
            TextView tvFoodName = view.findViewById(R.id.tvFoodNameRow);
            //メニュー名表示TextViewから表示されているメニュー名文字列を取得
            String foodName = tvFoodName.getText().toString();

            //MainActivityからFoodDetailActivityへ値を受け渡すIntentを宣言
            Intent intent = new Intent(_context, FoodDetailActivity.class);
            //食材名を受け渡す
            intent.putExtra("FoodName",foodName);
            //FoodDetailActivityを起動する
            _context.startActivity(intent);
        }
    }


}
