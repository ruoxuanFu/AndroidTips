#### Activity的启动流程[（1）](https://blog.csdn.net/zhaokaiqiang1992/article/details/49428287)[（2）](https://www.jianshu.com/p/cd7c5952b821)

##### 主要对象功能

1. ActivityThread：App真正的入口，App打开后会执行main方法，开启Loop循环，在其attach方法中通过ActivityManagerNative获取AMS的代理对象并把ApplicationThread传递给AMS，与AMS一起管理Activity
2. ApplicationThread：该类继承自ApplicationThreadNative，实现AMS与ActivityThread通信，当AMS需要管理应用中Activity的生命周期时，AMS通过代理对象和Binder驱动的方式向ActivityThread发送消息
3. ApplicationThreadNative：该类继承自Binder，实现IApplicationThread接口，通过asInterface()方法获取ApplicationThreadProxy对象，用于和ActivityThread通信
4. ApplicationThreadProxy：实现IApplicationThread接口，ActivityThread在AMS中的代理对象，用于对通过Binder传递的数据进行打包
5. ActivityManagerService：简称AMS，继承ActivityManagerNative，服务端对象，管理系统中所有Activity的生命周期
6. ActivityManagerNative：继承Binder，实现IActivityManager接口，ActivityThread通过其getDefault()方法获取ActivityManagerProxy对象，发送消息给AMS
7. ActivityManagerProxy：实现IActivityManager接口，AMS在ActivityThread中的代理对象，用于对通过Binder传递的数据进行打包
8. instrumentation：每个应用程序都有且只有一个instrumentation对象，应用程序中的Activity都会持有该对象的引用，是应用程序的管家，当创建Activity或生命周期变化时都需要instrumentation进行具体操作
9. ActivityStack：AMS用来管理Activity的栈，用来记录已经启动的Activity的先后顺序，启动状态信息等，通过ActivityStack判断是否需要启动新的进程
10. ActivityStackSupervisor：AMS用于管理ActivityStack的对象，AMS初始化时会创建一个该对象，每个ActivityStack会持有该对象的引用
11. ActivityRecord：ActivityStack的管理对象，用来记录Activity的状态信息，每个Activity在AMS中都对应一个ActivityRecord，是AMS中Activity对象的映射
12. TaskRecord：AMS中抽象出来的任务栈，用来记录ActivityRecord，一个TaskRecord包含若干个ActivityRecord，确保Activity创建和退出的顺序

> ###### ActivityStack，TaskRecord，ActivityRecord，Activity之间的关系
>
> 1. 一个ActivityRecord对应一个Activity，一个Activity可能对应多个ActivityRecord，这取决于Activity的LaunchMode
> 2. 一个TaskRecord包含一个或者多个ActivityRecoed，是一个先进后出的任务栈
> 3. 一个ActivityStack包含一个或者多个TaskRecord
> 4. 一个应用程序只有一个ActivityStackSupervisor，用于管理ActivityStack，当系统sdk小于9.0时，一个应用程序只有一个ActivityStack，大于9.0时，每创建一个TaskRecord就会创建一个ActivityStack

##### ActivityThread向AMS通信

方式：Binder驱动，通过代理对象

代理对象接口：IActivityManager

代理对象接口实现类：

> 服务端的实现类是ActivityManagerService，继承ActivityManagerNative
>
> 客户端（ActivityThread）持有服务端代理对象：ActivityManagerNative，通过ActivityManagerNative获取到ActivityManagerProxy
>
> ActivityManagerNative和ActivityManagerProxy都实现了IActivityManager接口

过程：ActivityThread通过ActivityManagerNative的getDefault方法获取到服务端在客户端的代理对象ActivityManagerProxy，由于AMS继承自ActivityManagerNative，ActivityManagerNative实现自IActivityManager接口，所以ActivityThread可以通过ActivityManagerProxy打包数据，通过Binder驱动，传递给AMS的相同方法，保证参数相同

##### AMS向ActivityThread通信

方式：Binder驱动，通过代理对象

代理对象接口：IApplicationThread

代理对象接口实现类：

> 客户端实现类是ApplicationThread，继承ApplicationThreadNative
>
> 服务端（AMS）持有客户端的代理对象：ApplicationThreadNative，通过ApplicationThreadNative获取ApplicationThreadProxy
>
> ApplicationThreadNative和ApplicationThreadProxy都实现了IApplicationThread接口

过程：AMS通过ApplicationThreadNative获取到客户端在服务端的代理对象ApplicationThreadProxy，通过其打包数据传递给ApplicationThread，完成通信

##### 从Launcher启动一个App

1. Launcher本质也是一个Activity，启动一个Activity时Launcher调用startActivitySafely方法，最终调用Instrumentation.execStartActivity方法，通过ActivityManagerProxy，包装参数，通过Binder驱动（ActivityManagerNative类）通知AMS，将要启动一个新的Activity，Instrumentation.execStartActivity方法中通过ActivityManagerNative.getDefault()返回AMS在ActivityThread中的代理对象ActivityManagerProxy，与AMS通信

   > 1. Launcher.startActivitySafely
   > 2. Launcher.startActivity
   > 3. Activity.startActivity，要求在新的Task中启动新的Activity，需要指定intent的flags为Intent.FLAG_ACTIVITY_NEW_TASK
   > 4. Activity.startActivityForResult
   > 5. Instrumentation.execStartActivity
   >
2. AMS端收到代理对象新建Activity的请求，调用ActivityStack类对象（ActivityStackSupervisor）生成新ActivityRecord，放入ActivityStack中，保存新启动Activity相关信息，通知Launcher进入Paused状态

   > 1. ActivityManagerNative.getDefault().startActivity
   > 2. ActivityManagerProxy.startActivity，与AMS通信，调用AMS的对应方法
   > 3. ActivityManagerService.startActivity，AMS端收到代理对象请求
   > 4. ActivityManagerService.startActivityAsUser
   > 5. ActivityStack.startActivityMayWait
   > 6. ActivityStack.resolveActivity
   > 7. ActivityStack.startActivityLocked，获取ActivityInfo，类名，包名等信息
   > 8. ActivityStack.startActivityUncheckedLocked，处理调用者信息（即Launcher），生成ActivityRecord保存将要创建的Activity相关信息
   > 9. ActivityStack.startActivityLocked，解析新的Activity的启动模式，并创建一个新的Task和TaskRecord，保存ActivityRecord，把新创建的Activity放在栈顶
   > 10. ActivityStack.resumeTopActivityLocked
   > 11. ActivityStack.startPausingLocked，使Launcher进入Paused状态
   >
3. AMS通过ApplicationThreadNative获取ActivityThread的代理对象ApplicationThreadProxy，通知Launcher进入Paused状态，ActivityThread接收到AMS的消息，并处理

   > 1. ApplicationThreadProxy.schedulePauseActivity
   > 2. ApplicationThread.schedulePauseActivity
   > 3. ActivityThread.queueOrSendMessage
   > 4. ActivityThread.handlePauseActivity，调用Activity.onPause方法，并通知AMS，Launcher已经进入Paused状态
   > 5. ActivityManagerProxy.activityPaused，通过代理对象，ActivityThread发送通知给AMS
   >
4. AMS接收到Launcher进入Paused的消息，调用ActivityStack对象处理Launcher Activity状态信息，把Launcher进入onPause状态，再恢复栈顶的Activity并获取其ActivityRecord，AMS通过zygote机制创建一个新的进程，用来启动新的ActivityThread实例，即将要启动的Activity会在这个ActivityThread实例中运行，AMS通知zygote创建新的进程，执行ActivityThread的main方法，初始化新的Activity

   > 1. ActivityManagerService.activityPaused
   > 2. ActivityStack.activityPaused，把Launcher Activity状态修改为PAUSED
   > 3. ActivityStack.completePauseLocked
   > 4. ActivityStack.resumeTopActivityLokced，让栈顶Activity变为onResume状态
   > 5. ActivityStack.topRunningActivityLocked，获取栈定Activity的ActivityRecord
   > 6. ActivityStack.startSpecificActivityLocked，AMS创建一个新的进程，用来启动一个ActivityThread实例，即将要启动的Activity就是在这个ActivityThread实例中运行
   > 7. ActivityManagerService.startProcessLocked，创建对应的ProcessRecord
   > 8. Process.start->Process.startViaZygote，通过zygote机制创建一个新的进程，新的进程会导入android.app.ActivityThread类，并且执行它的main函数，
   >
5. 新的进程和ActivityThread创建后，新进程的ActivityThread在attach方法中，获取ActivityManagerNative和ActivityManagerProxy，让AMS绑定ApplicationThread，并通过ActivityManagerProxy与AMS通信，使的AMS持有ActivityThread的代理对象ApplicationThread，实现AMS控制ActivityThread

   > 1. ActivityThread.main，执行ActivityThread的main函数，实例化ActivityThread，每个应用有且仅有一个ActivityThread实例，开启主线程消息循环
   > 2. ActivityThread.attach，调用attach函数，创建ApplicationThread，并把ApplicationThread的Binder对象，通过AMS在ActivityThread中的代理对象，传递给AMS，实现通信
   > 3. ActivityManagerNative.getDefault()->IActivityManager.attachApplication(mAppThread)->ActivityManagerProxy.attachApplication->ActivityManagerService.attachApplication，AMS持有ActivityThread的代理对象
   >
6. AMS收到ActivityThread的通知后，持有ActivityThread的ApplicationThread，并通过ApplicationThreadNative的Binder驱动通知ActivityThread可以启动新的Activity了，把Activity参数传递给ActivityThread

   > 1. ActivityManagerService.attachApplicationLocked，绑定进程信息
   > 2. ActivityStack.realStartActivityLocked，真正启动Activity
   > 3. ApplicationThreadProxy.scheduleLaunchActivity->ApplicationThread.scheduleLaunchActivity->ActivityThread.handleLaunchActivity，通知ActivityThread创建新的Activity
   >
7. ActivityThread收到消息后，加载新的Activity类

   > 1. ActivityThread.handleLaunchActivity->ActivityThread.performLaunchActivity->Instrumentation.newActivity
   > 2. ActivityClientRecord.packageInfo.makeApplication，创建Application对象
   > 3. Activity.attach()，把Application对象attach到Activity, 即把Activtiy相关信息设置到新创建的Activity中
   > 4. Instrumentation.callActivityOnCreate，调用新Activity的onCreate，onStart，onResume，同时调用Launcher的onPause和onStop
   >

Launcher启动App

1. Launcher的ActivityThread通知AMS创建新的Activity
   1. 调通startActivity
   2. 通过instrumentation的execStartActivity
   3. execStartActivity中获取到ActivityManagerNative->ActivityManagerProxy，传递消息给AMS
2. AMS接收到创建Activity的请求，会交给ActivityStack处理新创建的Activity，并生成与之对应的ActivityRecord，放入ActivityStack中，保存新启动的Activity的信息
   1. 通过ApplicationThreadNative获取到ApplicationThreadProxy，发送消息给Launcher的ActivityThread，告诉Launcher进入pause状态
   2. Launcher进程接收到AMS的消息后，处理消息并执行onPause方法，并把执行结果通过ActivityManagerProxy发送给AMS
   3. AMS接收到Launcher进入pause状态后，首先会让ActivityStack把Launcher置为Pause状态，然后恢复栈顶Activity，并获取其ActivityRecord，AMS通过Socket通知zygote进程fork一个新的App进程，并把ActivityRecord传递给新的进程
3. 新进程被fork出来，导入了ActivityThread类，ActivityThread的main方法将会被执行，开启新App的主线程循环，并在attach方法中，初始化ApplicationThread，通过AMS的代理对象ActivityManagerNative把ApplicationThread与AMS绑定
4. AMS接收到ActivityThread的消息后，进行进程绑定，ActivityStack真正启动Activity，并通知ActivityThread创建新的Activity
5. ActivityThread收到消息后，开始加载新的Activity类
   1. 创建Application，把Application绑定到Activity，instrumentation执行对应生命周期方法
