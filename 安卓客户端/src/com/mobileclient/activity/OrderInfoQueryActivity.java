package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.domain.Movie;
import com.mobileclient.service.MovieService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class OrderInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明订单编号输入框
	private EditText ET_orderNo;
	// 声明下单电影下拉框
	private Spinner spinner_movieObj;
	private ArrayAdapter<String> movieObj_adapter;
	private static  String[] movieObj_ShowText  = null;
	private List<Movie> movieList = null; 
	/*电影管理业务逻辑层*/
	private MovieService movieService = new MovieService();
	// 声明下单用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明下单时间输入框
	private EditText ET_orderTime;
	// 声明收货人输入框
	private EditText ET_receiveName;
	// 声明收货人电话输入框
	private EditText ET_telephone;
	// 声明订单状态下拉框
	private Spinner spinner_orderStateObj;
	private ArrayAdapter<String> orderStateObj_adapter;
	private static  String[] orderStateObj_ShowText  = null;
	private List<OrderState> orderStateList = null; 
	/*订单状态管理业务逻辑层*/
	private OrderStateService orderStateService = new OrderStateService();
	/*查询过滤条件保存到这个对象中*/
	private OrderInfo queryConditionOrderInfo = new OrderInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置订单查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_orderNo = (EditText) findViewById(R.id.ET_orderNo);
		spinner_movieObj = (Spinner) findViewById(R.id.Spinner_movieObj);
		// 获取所有的电影
		try {
			movieList = movieService.QueryMovie(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int movieCount = movieList.size();
		movieObj_ShowText = new String[movieCount+1];
		movieObj_ShowText[0] = "不限制";
		for(int i=1;i<=movieCount;i++) { 
			movieObj_ShowText[i] = movieList.get(i-1).getMovieName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		movieObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, movieObj_ShowText);
		// 设置下单电影下拉列表的风格
		movieObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_movieObj.setAdapter(movieObj_adapter);
		// 添加事件Spinner事件监听
		spinner_movieObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderInfo.setMovieObj(movieList.get(arg2-1).getMovieId()); 
				else
					queryConditionOrderInfo.setMovieObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_movieObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "不限制";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置下单用户下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderInfo.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionOrderInfo.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_orderTime = (EditText) findViewById(R.id.ET_orderTime);
		ET_receiveName = (EditText) findViewById(R.id.ET_receiveName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		spinner_orderStateObj = (Spinner) findViewById(R.id.Spinner_orderStateObj);
		// 获取所有的订单状态
		try {
			orderStateList = orderStateService.QueryOrderState(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int orderStateCount = orderStateList.size();
		orderStateObj_ShowText = new String[orderStateCount+1];
		orderStateObj_ShowText[0] = "不限制";
		for(int i=1;i<=orderStateCount;i++) { 
			orderStateObj_ShowText[i] = orderStateList.get(i-1).getOrderStateName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		orderStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, orderStateObj_ShowText);
		// 设置订单状态下拉列表的风格
		orderStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_orderStateObj.setAdapter(orderStateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_orderStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderInfo.setOrderStateObj(orderStateList.get(arg2-1).getOrderStateId()); 
				else
					queryConditionOrderInfo.setOrderStateObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_orderStateObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionOrderInfo.setOrderNo(ET_orderNo.getText().toString());
					queryConditionOrderInfo.setOrderTime(ET_orderTime.getText().toString());
					queryConditionOrderInfo.setReceiveName(ET_receiveName.getText().toString());
					queryConditionOrderInfo.setTelephone(ET_telephone.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionOrderInfo", queryConditionOrderInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
