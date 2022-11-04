# Intent

用于Android中四大组件的通信，底层是由Binder实现，物理层是由共享内存方式实现

#### 属性

component：指定四大组件，实现组件之间通信

action：指定目标组件行为的字符串，通过自定义action匹配组件实现隐式启动或通过系统定义的字符串打开系统页面

category：指定action的被执行环境

data：表示intent要操作的数据类型，通常是uri形式

type：指定data的类型

extras：需要传递的数据，基本数据类型和可序列化数据

flags：指定intent的启动flag

#### 启动方式

对于Activity来说分为隐式启动和显示启动

显示启动：通过startActivity，startActivityForResult，ActivityResultLauncher启动的Activity，这些Activity需要在AndroidMainfest中注册

隐式启动：通过指定Intent的action属性启动的Activity，这些Activity需要在AndroidMainfest中注册，并在intent-filter中指定action值，并把category设置为android.intent.category.DEFAULT

#### [与Intent-Filter标签的关系](https://blog.csdn.net/qq_26460841/article/details/118439845)

简单来说，代码中通过intent设置的属性值会与intent-filter对应的标签值进行匹配

##### Action属性匹配规则：

1. 在intent-filter标签中，action标签可以设置多个，而在intent中，只能set一个action属性值
2. 当intent-filter中没有设置action标签时，intent无论设置任何action值都无法进行匹配
3. 当intent-filter中设置action标签时，如果intent没有设置action，可以匹配通过；如果intent设置action，则必须包含在action标签中，才可以匹配通过

##### Category属性匹配规则：

1. 在intent-filter标签中，category标签可以设置多个，在intent中，可以add多个category属性值
2. 当intent-filter中没有设置category标签时，intent无论设置任何category值都无法进行匹配，特例：通过startActivity启动的Activity会被默认标记为android.intent.category.DEFAULT，因此通过隐式启动的Activity必须设置category标签，并制定为其值
3. 当intent-filter中设置category标签时，如果intent没有设置category，可以匹配通过；如果intent设置category，则必须所有值包含在category标签中，才可以匹配通过

##### Data属性匹配规则：

data属性则是用来指定Intent传递过来的URI和数据类型，除了data还需要指定type，type指定媒体类型

intent-filter中可以指定多个data，而intent中只能设置一个data，或者使用setDataAndType方法

比较时，intent-filter和intent的data必须全部匹配，适用于通配符

#### [PendingIntent和Intent的关系](https://juejin.cn/post/7122767360976486413)

1. 结构，PendingIntent是Intent的包装类，内部持有代表最终Intent操作的持有类
2. 调用执行，Intent通常是在创建它的进程中执行，调用者通常当前应用程序；PendingIntent通常不会在创建它的进程中执行，且支持授权其他应用程序以当前应用的身份调用
3. 执行时间，PendingIntent可以延时执行，甚至可以在创建它的进程消亡后也可以执行
