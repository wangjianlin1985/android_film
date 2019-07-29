package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.Movie;
import com.mobileclient.service.MovieService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class OrderInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明订单编号控件
	private TextView TV_orderNo;
	// 声明下单电影控件
	private TextView TV_movieObj;
	// 声明电影价格控件
	private TextView TV_moviePrice;
	// 声明购买数量控件
	private TextView TV_orderNum;
	// 声明订单总价控件
	private TextView TV_orderPrice;
	// 声明下单用户控件
	private TextView TV_userObj;
	// 声明下单时间控件
	private TextView TV_orderTime;
	// 声明收货人控件
	private TextView TV_receiveName;
	// 声明收货人电话控件
	private TextView TV_telephone;
	// 声明收货人地址控件
	private TextView TV_address;
	// 声明订单状态控件
	private TextView TV_orderStateObj;
	// 声明订单备注控件
	private TextView TV_orderMemo;
	/* 要保存的订单信息 */
	OrderInfo orderInfo = new OrderInfo(); 
	/* 订单管理业务逻辑层 */
	private OrderInfoService orderInfoService = new OrderInfoService();
	private MovieService movieService = new MovieService();
	private UserInfoService userInfoService = new UserInfoService();
	private OrderStateService orderStateService = new OrderStateService();
	private String orderNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看订单详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_orderNo = (TextView) findViewById(R.id.TV_orderNo);
		TV_movieObj = (TextView) findViewById(R.id.TV_movieObj);
		TV_moviePrice = (TextView) findViewById(R.id.TV_moviePrice);
		TV_orderNum = (TextView) findViewById(R.id.TV_orderNum);
		TV_orderPrice = (TextView) findViewById(R.id.TV_orderPrice);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_orderTime = (TextView) findViewById(R.id.TV_orderTime);
		TV_receiveName = (TextView) findViewById(R.id.TV_receiveName);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_address = (TextView) findViewById(R.id.TV_address);
		TV_orderStateObj = (TextView) findViewById(R.id.TV_orderStateObj);
		TV_orderMemo = (TextView) findViewById(R.id.TV_orderMemo);
		Bundle extras = this.getIntent().getExtras();
		orderNo = extras.getString("orderNo");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    orderInfo = orderInfoService.GetOrderInfo(orderNo); 
		this.TV_orderNo.setText(orderInfo.getOrderNo());
		Movie movieObj = movieService.GetMovie(orderInfo.getMovieObj());
		this.TV_movieObj.setText(movieObj.getMovieName());
		this.TV_moviePrice.setText(orderInfo.getMoviePrice() + "");
		this.TV_orderNum.setText(orderInfo.getOrderNum() + "");
		this.TV_orderPrice.setText(orderInfo.getOrderPrice() + "");
		UserInfo userObj = userInfoService.GetUserInfo(orderInfo.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_orderTime.setText(orderInfo.getOrderTime());
		this.TV_receiveName.setText(orderInfo.getReceiveName());
		this.TV_telephone.setText(orderInfo.getTelephone());
		this.TV_address.setText(orderInfo.getAddress());
		OrderState orderStateObj = orderStateService.GetOrderState(orderInfo.getOrderStateObj());
		this.TV_orderStateObj.setText(orderStateObj.getOrderStateName());
		this.TV_orderMemo.setText(orderInfo.getOrderMemo());
	} 
}
