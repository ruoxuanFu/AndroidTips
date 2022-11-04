#### onConfigurationChanged(Configuration newConfig) 系统配置信息变动

1. 只有在AndroidManifest文件中对应的Activity中设置android:configChanges=""属性时，且所设置的属性与对应系统配置信息变动一致时，才会调用Activity中的onConfigurationChanged方法，否则Activity就会销毁重建
   configChanges属性：
   mcc：检测到sim卡并更新mcc网络
   mnc：检测到sim卡并更新mnc网络
   uiMode：用户界面模式发生变化
   smallestScreenSize：物理屏幕尺寸发生变化
   layoutDirection：布局方向发生变化
   locale：语言设置发生变化
   touchscreen：触摸屏发生变化
   keyboard：键盘类型发生变化，如用户接入一个外接键盘
   keyboardHidden：键盘无障碍功能发生了变化，如用户显示了硬件键盘
   navigation：导航类型发生变化
   screenLayout：屏幕布局发生变化
   fontScale：字体大小发生变化
   orientation：屏幕方向发生变化，当设备api大于12时，需要配合screenSize才会调用
   screenSize：屏幕大小发生变化

   常用的，屏幕方向发生变化时需要配置：
   android:configChanges="orientation|keyboard|keyboardHidden|screenSize"
2. onConfigurationChanged方法只会在onPause方法之前调用
   解决办法：

   1. 自定义BroadcastReceiver，接受`android.intent.action.CONFIGURATION_CHANGED`广播，动态注册此广播，并处理配置变换
   2. 重写onResume方法，代替onConfigurationChanged，记录此前页面方向并和当前页面方向对比，处理配置变更

      ```java
          @Override
          protected void onResume() {
              super.onResume();
              if (isLandOrientation()) {
                  // 处理横屏
                
              } else {
                  // 处理竖屏

              }
          }

          public boolean isLandOrientation() {
              return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
          }
      ```
