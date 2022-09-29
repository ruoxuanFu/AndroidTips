# LauchMode

启动模式主要是指如何启动一个Activity

#### 设置方式

1. 在AndroidManifest.xml中，在需要的activity标签里设置`android:launchMode=""`；
   1. standard：标准模式，默认值，新建一个Activity并加入任务栈中
   2. singleTop：栈顶唯一，当需要新建的Activity处于任务栈顶时不会新建Activity，会复用当前栈顶Activity
   3. singleTask：栈内唯一，当需要新建的Activity在任务栈内有实例时不会新建Activity，会复用栈内Activity实例，并出栈此实例上方所有Activity
   4. singleInstance：堆内单例，启动一个新的任务栈存放新建的Activity，并保证不再有其他Activity实例进入

      > 设置了singleTop，singleTask，singleInstance的Activity如果在任务栈中有实例时，并且再次启动此Activity时，由于是复用，不会执行onCreate方法，而是执行onNewIntent方法。设置singleTop的Activity会执行onCreate->onStart->onResume->onPause->onNewIntent->onResume，设置了singleTask的会执行onCreate->onStart->onResume->onPause->onStop->其他页面跳转回来->onNewIntent->onRestart->onStart->onResume
      >
2. 在Intent中设置flag，`intent.setFlag(int flags)`
   1. FLAG_ACTIVITY_CLEAR_TOP：相当于singleTask
   2. FLAG_ACTIVITY_SINGLE_TOP：相当于singleTop
   3. FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS：设置了此标记的Activity相当于在AndroidManifest中设置了`android:excludeFromRecents="true"`，用户不会在手机的‘任务列表’中看到此任务栈
   4. FLAG_ACTIVITY_NO_HISTORY：设置了此标记的Activity相当于在AndroidManifest中设置了`android:noHistory="true"`，一旦退出Activity后，不会保存在任务栈中
   5. FLAG_ACTIVITY_NEW_TASK：此标记需要配合taskAffinity属性，把Activity存入taskAffinity所指的任务栈中（如果是启动其他应用则不需要设置taskAffinity），此外在非Activity中调用startActivity时，需要设置此标记，如在Service中启动Activity
