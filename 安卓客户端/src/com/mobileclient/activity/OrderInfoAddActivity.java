package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.Movie;
import com.mobileclient.service.MovieService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class OrderInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明订单编号输入框
	private EditText ET_orderNo;
	// 声明下单电影下拉框
	private Spinner spinner_movieObj;
	private ArrayAdapter<String> movieObj_adapter;
	private static  String[] movieObj_ShowText  = null;
	private List<Movie> movieList = null;
	/*下单电影管理业务逻辑层*/
	private MovieService movieService = new MovieService();
	// 声明电影价格输入框
	private EditText ET_moviePrice;
	// 声明购买数量输入框
	private EditText ET_orderNum;
	// 声明订单总价输入框
	private EditText ET_orderPrice;
	// 声明下单用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*下单用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明下单时间输入框
	private EditText ET_orderTime;
	// 声明收货人输入框
	private EditText ET_receiveName;
	// 声明收货人电话输入框
	private EditText ET_telephone;
	// 声明收货人地址输入框
	private EditText ET_address;
	// 声明订单状态下拉框
	private Spinner spinner_orderStateObj;
	private ArrayAdapter<String> orderStateObj_adapter;
	private static  String[] orderStateObj_ShowText  = null;
	private List<OrderState> orderStateList = null;
	/*订单状态管理业务逻辑层*/
	private OrderStateService orderStateService = new OrderStateService();
	// 声明订单备注输入框
	private EditText ET_orderMemo;
	protected String carmera_path;
	/*要保存的订单信息*/
	OrderInfo orderInfo = new OrderInfo();
	/*订单管理业务逻辑层*/
	private OrderInfoService orderInfoService = new OrderInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加订单");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_orderNo = (EditText) findViewById(R.id.ET_orderNo);
		spinner_movieObj = (Spinner) findViewById(R.id.Spinner_movieObj);
		// 获取所有的下单电影
		try {
			movieList = movieService.QueryMovie(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int movieCount = movieList.size();
		movieObj_ShowText = new String[movieCount];
		for(int i=0;i<movieCount;i++) { 
			movieObj_ShowText[i] = movieList.get(i).getMovieName();
		}
		// 将可选内容与ArrayAdapter连接起来
		movieObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, movieObj_ShowText);
		// 设置下拉列表的风格
		movieObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_movieObj.setAdapter(movieObj_adapter);
		// 添加事件Spinner事件监听
		spinner_movieObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setMovieObj(movieList.get(arg2).getMovieId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_movieObj.setVisibility(View.VISIBLE);
		ET_moviePrice = (EditText) findViewById(R.id.ET_moviePrice);
		ET_orderNum = (EditText) findViewById(R.id.ET_orderNum);
		ET_orderPrice = (EditText) findViewById(R.id.ET_orderPrice);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的下单用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_orderTime = (EditText) findViewById(R.id.ET_orderTime);
		ET_receiveName = (EditText) findViewById(R.id.ET_receiveName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_address = (EditText) findViewById(R.id.ET_address);
		spinner_orderStateObj = (Spinner) findViewById(R.id.Spinner_orderStateObj);
		// 获取所有的订单状态
		try {
			orderStateList = orderStateService.QueryOrderState(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int orderStateCount = orderStateList.size();
		orderStateObj_ShowText = new String[orderStateCount];
		for(int i=0;i<orderStateCount;i++) { 
			orderStateObj_ShowText[i] = orderStateList.get(i).getOrderStateName();
		}
		// 将可选内容与ArrayAdapter连接起来
		orderStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, orderStateObj_ShowText);
		// 设置下拉列表的风格
		orderStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_orderStateObj.setAdapter(orderStateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_orderStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setOrderStateObj(orderStateList.get(arg2).getOrderStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_orderStateObj.setVisibility(View.VISIBLE);
		ET_orderMemo = (EditText) findViewById(R.id.ET_orderMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加订单按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取订单编号*/
					if(ET_orderNo.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "订单编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderNo.setFocusable(true);
						ET_orderNo.requestFocus();
						return;
					}
					orderInfo.setOrderNo(ET_orderNo.getText().toString());
					/*验证获取电影价格*/ 
					if(ET_moviePrice.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "电影价格输入不能为空!", Toast.LENGTH_LONG).show();
						ET_moviePrice.setFocusable(true);
						ET_moviePrice.requestFocus();
						return;	
					}
					orderInfo.setMoviePrice(Float.parseFloat(ET_moviePrice.getText().toString()));
					/*验证获取购买数量*/ 
					if(ET_orderNum.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "购买数量输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderNum.setFocusable(true);
						ET_orderNum.requestFocus();
						return;	
					}
					orderInfo.setOrderNum(Integer.parseInt(ET_orderNum.getText().toString()));
					/*验证获取订单总价*/ 
					if(ET_orderPrice.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "订单总价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderPrice.setFocusable(true);
						ET_orderPrice.requestFocus();
						return;	
					}
					orderInfo.setOrderPrice(Float.parseFloat(ET_orderPrice.getText().toString()));
					/*验证获取下单时间*/ 
					if(ET_orderTime.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "下单时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderTime.setFocusable(true);
						ET_orderTime.requestFocus();
						return;	
					}
					orderInfo.setOrderTime(ET_orderTime.getText().toString());
					/*验证获取收货人*/ 
					if(ET_receiveName.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "收货人输入不能为空!", Toast.LENGTH_LONG).show();
						ET_receiveName.setFocusable(true);
						ET_receiveName.requestFocus();
						return;	
					}
					orderInfo.setReceiveName(ET_receiveName.getText().toString());
					/*验证获取收货人电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "收货人电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					orderInfo.setTelephone(ET_telephone.getText().toString());
					/*验证获取收货人地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "收货人地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					orderInfo.setAddress(ET_address.getText().toString());
					/*验证获取订单备注*/ 
					if(ET_orderMemo.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "订单备注输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderMemo.setFocusable(true);
						ET_orderMemo.requestFocus();
						return;	
					}
					orderInfo.setOrderMemo(ET_orderMemo.getText().toString());
					/*调用业务逻辑层上传订单信息*/
					OrderInfoAddActivity.this.setTitle("正在上传订单信息，稍等...");
					String result = orderInfoService.AddOrderInfo(orderInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
