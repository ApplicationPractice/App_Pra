package com.example.myapplication.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import com.example.myapplication.NavigationActivity;
import com.google.gson.Gson;
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableResource;
import com.example.myapplication.CommentExpandableListView;
import com.example.myapplication.R;
import com.example.myapplication.adapt.CommentExpandAdapter;
import com.example.myapplication.bean.CommentBean;
import com.example.myapplication.bean.CommentDetailBean;
import com.example.myapplication.bean.MessageBean;
import com.example.myapplication.bean.ReplyDetailBean;
import com.example.myapplication.model.VideoModel;
import com.example.myapplication.video.SampleCoverVideo;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoShotListener;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;
import wseemann.media.FFmpegMediaMetadataRetriever;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;


public class RecyclerItemNormalHolder extends RecyclerItemBaseHolder {

    public final static String TAG = "RecyclerView2List";
    public Bitmap bitmap=null;
    protected Context context = null;

    @BindView(R.id.video_item_player)
    SampleCoverVideo gsyVideoPlayer;

    ImageView imageView;
    GSYVideoShotListener gsyVideoShotListener;
    GSYVideoOptionBuilder gsyVideoOptionBuilder;
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
    public RecyclerItemNormalHolder(Context context, View v) {
        super(v);
        this.context = context;
        ButterKnife.bind(this, v);
        imageView = new ImageView(context);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }
    public void onBind(final int position, VideoModel videoModel) {

        String url;
        String title;
        if (position % 2 == 0) {
            url="http://192.144.212.162:8081/UploadVideo/hhh1.mp4";
         //   url="http://192.144.212.162:8081/UploadVideo/d2dtogod.mp4";
        //    url = "https://pointshow.oss-cn-hangzhou.aliyuncs.com/McTk51586843620689.mp4";
            title = "";
        } else {
            url="http://192.144.212.162:8081/UploadVideo/hhh.mp4";
        //    url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
            title = "";
        }


        Map<String, String> header = new HashMap<>();
        header.put("ee", "33");

        //防止错位，离开释放
        //gsyVideoPlayer.initUIState();
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                //.setThumbImageView(imageView)
                .setUrl(url)
                .setVideoTitle(title)
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setMapHeadData(header)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                            //静音
                            GSYVideoManager.instance().setNeedMute(false);
                        }

                    }

//                    @Override
//                    public void onQuitFullscreen(String url, Object... objects) {
//                        super.onQuitFullscreen(url, objects);
//                        //全屏不静音
//                        GSYVideoManager.instance().setNeedMute(false);
//                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                    }
                }).build(gsyVideoPlayer);


        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);

        //设置返回键
 //       gsyVideoPlayer.getBackButton().setVisibility(View.INVISIBLE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(gsyVideoPlayer);
            }
        });
        final List<CommentDetailBean> commentsList= generateTestData();
        final CommentExpandAdapter adapter=new CommentExpandAdapter(context,commentsList);
        gsyVideoPlayer.getMarkon().bringToFront();
        gsyVideoPlayer.getMarkon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentDialog(context,adapter,commentsList);
            }
        });

        gsyVideoPlayer.loadCoverImageBy(R.drawable.bg, R.mipmap.ic_launcher);
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true);
    }

    public  SampleCoverVideo getPlayer() {
        return gsyVideoPlayer;
    }

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
                    ReplyDetailBean detailBean = new ReplyDetailBean("wwww",replyContent);
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
