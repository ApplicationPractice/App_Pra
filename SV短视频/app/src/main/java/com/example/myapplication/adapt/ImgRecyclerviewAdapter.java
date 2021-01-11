package com.example.myapplication.adapt;

import android.content.Context;
import android.graphics.Color;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.CommentExpandableListView;
import com.example.myapplication.R;
import com.example.myapplication.bean.CommentBean;
import com.example.myapplication.bean.CommentDetailBean;
import com.example.myapplication.bean.MessageBean;
import com.example.myapplication.bean.ReplyDetailBean;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

/**
 * Created by YLSN on 2020/9/8.
 */

public class ImgRecyclerviewAdapter extends RecyclerView.Adapter<ImgRecyclerviewAdapter.ViewHolder>{
    private List<MessageBean> mTxList;//用以将适配完的子项储存的链表，它的泛型是之前的实体类
    private BottomSheetDialog dialog;
    private BottomSheetDialog dialog1;
    private CommentBean commentBean;
    private Context cont;
    private String testJson = "{\n" +
            "\t\"code\": 1000,\n" +
            "\t\"message\": \"查看评论成功\",\n" +
            "\t\"data\": {\n" +
            "\t\t\"total\": 3,\n" +
            "\t\t\"list\": [{\n" +
            "\t\t\t\t\"id\": 42,\n" +
            "\t\t\t\t\"nickName\": \"程序猿\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"时间是一切财富中最宝贵的财富。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"三分钟前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\t\"commentId\": \"42\",\n" +
            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"一个小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 41,\n" +
            "\t\t\t\t\"nickName\": \"设计狗\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"这世界要是没有爱情，它在我们心中还会有什么意义！这就如一盏没有亮光的走马灯。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"一天前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\t\"commentId\": \"41\",\n" +
            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"三小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\"nickName\": \"产品喵\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"笨蛋自以为聪明，聪明人才知道自己是笨蛋。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 0,\n" +
            "\t\t\t\t\"createDate\": \"三天前\",\n" +
            "\t\t\t\t\"replyList\": []\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}";
    static class ViewHolder extends RecyclerView.ViewHolder {
        //内部静态类，用以定义TxApter.View的泛型
        View txView;//这个是用于整个子项的
        ImageView img;// 控制的控件
        CommentExpandableListView expandableListView;
        TextView bt;
        public ViewHolder(View view) {
            super(view);
            txView = view;//这个是整个子项的控件
            img=(ImageView) view.findViewById(R.id.img);
         //   expandableListView=(CommentExpandableListView)view.findViewById(R.id.detail_page_lv_comment7);
            bt=(TextView)view.findViewById(R.id.detail_page_do_comment);

        }
    }

    public ImgRecyclerviewAdapter(List<MessageBean> txList) {
        //链表的赋值
        mTxList = txList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //ViewHode方法，我的理解就是对某个具体子项的操作，包括对具体控件的设置，包括且不限于的点击动作两个参数
      //  A:ViewGroup parent主要用于调用其整个RecyclerView的上下文
     //   B:第二个参数因为在方法里面没有调用，所以我也没看懂，从字面上看，这个参数是一个整型的控件类型？？？
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        //将子项的布局通过LayoutInflater引入
        final ViewHolder holder = new ViewHolder(view);
        cont=holder.txView.getContext();
        return holder;  //返回一个holder对象，给下个方法使用
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        MessageBean tx = mTxList.get(position);//创建前面实体类的对象
        Uri uri=Uri.parse(tx.getName());
        Glide.with(cont).load("http://192.144.212.162:8081/UploadVideo/2mrks3iz.jpeg").placeholder(R.drawable.bg).error(R.drawable.video_click_error_selector).into(holder.img);
         final List<CommentDetailBean> commentsList= generateTestData();
        final CommentExpandAdapter adapter=new CommentExpandAdapter(holder.txView.getContext(),commentsList);
        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentDialog(cont,adapter,commentsList);
            }
        });
    }

    @Override
    public int getItemCount() {
        //用以返回RecyclerView的总共长度，这里直接使用了链表的长度（size）
        return mTxList.size();
    }
    /**
     * func:生成测试数据
     * @return 评论数据
     */
    private List<CommentDetailBean> generateTestData(){
        Gson gson = new Gson();
        commentBean = gson.fromJson(testJson, CommentBean.class);
        List<CommentDetailBean> commentList = commentBean.getData().getList();
        return commentList;
    }


    /**
     * func:弹出评论框
     */
    private void showCommentDialog(final Context context, final CommentExpandAdapter adapter, final List<CommentDetailBean> commentsList){
        dialog = new BottomSheetDialog(context);
        View commentView = LayoutInflater.from(context).inflate(R.layout.hhhh,null);
        TextView shuo=(TextView)commentView.findViewById(R.id.detail_page_do_comment);
        final CommentExpandableListView expandableListView1=(CommentExpandableListView)commentView.findViewById(R.id.detail_page_lv_comment7);
        expandableListView1.setAdapter(adapter);
        for(int i=0;i<commentsList.size();i++){
            expandableListView1.expandGroup(i);
        }
        expandableListView1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentsList.get(groupPosition).getId());
                showReplyDialog(groupPosition,context,expandableListView1,adapter,commentsList);
                return true;
            }
        });
        expandableListView1.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(context,"点击了回复", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expandableListView1.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });
        shuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                View v = LayoutInflater.from(context).inflate(R.layout.comment_dialog_layout,null);
                final EditText commentText = (EditText) v.findViewById(R.id.dialog_comment_et);
                final Button bt_comment = (Button) v.findViewById(R.id.dialog_comment_bt);
                dialog1 = new BottomSheetDialog(context);
                dialog1.setContentView(v);
                bt_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String commentContent = commentText.getText().toString().trim();
                        if(!TextUtils.isEmpty(commentContent)){
                            //commentOnWork(commentContent);
                            dialog1.dismiss();
                            CommentDetailBean detailBean = new CommentDetailBean("wwww", commentContent,"刚刚");
                            adapter.addTheCommentData(detailBean);
                            Toast.makeText(context,"评论成功", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(context,"评论内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                commentText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                            bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                        }else {
                            bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                dialog1.show();
            }
        });

        dialog.setContentView(commentView);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    /**
     * func:弹出回复框
     */
    private void showReplyDialog(final int position, final Context context, final CommentExpandableListView expandableListView1, final CommentExpandAdapter adapter, List<CommentDetailBean> commentsList){
        dialog = new BottomSheetDialog(context);
        View commentView = LayoutInflater.from(context).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(replyContent)){

                    dialog.dismiss();
                    ReplyDetailBean detailBean = new ReplyDetailBean("www",replyContent);
                        adapter.addTheReplyData(detailBean, position);
                    expandableListView1.expandGroup(position);

                    Toast.makeText(context,"回复成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }
}
