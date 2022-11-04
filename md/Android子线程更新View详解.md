#### [Android子线程更新View详解](https://www.jianshu.com/p/c39203884209)

1. 子线程为什么不能更新View？

   > 当更新界面时，界面不可见，不需要调用invalidate方法时，可以在子线程更新View（例如在onCreate中设置简单数据，在界面不可见时是可以更新数据的）
   >

   > 当更新界面时，界面可见，需要调用invalidate方法时，不可以在子线程更新View，因为会调用checkThread方法，检查当前线程
   >
2. 如何在子线程更新View

   1. 在主线程中定义handler
   2. 使用Activity对象的runOnUiThread方法
   3. 使用View的post(Runnabke r)方法，该方法需要在onPause方法中removeCallBacks掉，避免泄露
