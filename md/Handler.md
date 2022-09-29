# Handler

Handler的运行机制既是Android的消息机制

## 消息机制模型包括：

1. Looper：消息循环，不断的从消息队列中取出消息交给对应的Handler处理
2. Handler：用于发送和处理消息
3. MessageQueue：消息队列，存储和传递Handler发送过来的消息，内部用单链表维护消息列表，方便插入和删除
4. Message：消息对象，可以传递数据

## 消息机制

在已经开启Looper消息循环的线程中，通过Handler的sendMessage发送消息，将会调用MessageQueue的enqueueMessage方法向消息队列
中添加消息，Looper会不断的调用MessageQueue的next方法获取新消息，然后调用Handler的dispatchMessage方法分发消息，目标Handler接收到消息后调动handleMessage处理消息

## Thread，ThreadLocal，Handler，Looper，MessageQueue的关系

> ThreadLocal是Thread内部的数据存储类，通过它可以在指定的线程中存储数据，数据存储以后，只有在指定线程中可以获取到存储的数据，对于其它线程来说无法获取到数据
> Looper的数据保存在ThreadLocal中
> 一个Thread只能有一个Looper
> 一个Thread可以有多个Handler
> 使用Handler必须创建Looper，MessageQueue并开启循环
> 一个Looper维护一个MessageQueue
> 一个Looper可以处理多个线程的Handler，一个MessageQueue可以处理多个线程Handler发送过来的Message

## Message使用要求

1. 尽管Message有public的默认构造方法，但是你应该通过Message.obtain()来从消息池中获得空消息对象，以节省资源
2. 如果你的message只需要携带简单的int信息，请优先使用Message.arg1和Message.arg2来传递信息，这比用Bundle更省内存
3. 用message.what来标识信息，以便用不同方式处理message。

## 为什么主线程不会因为Looper.loop()里的死循环卡死或者不能处理其他事务

1. 为什么不会卡死

> handler机制是使用管道来实现的，主线程没有消息处理时会阻塞在管道的读端，binder线程会往主线程消息队列里添加消息，然后往管道写端写一个字节，这样就能唤醒主线程从管道读端返回，也就是说queue.next()会调用返回，主线程大多数时候都是处于休眠状态，并不会消耗大量CPU资源。

2. 既然是死循环又如何去处理其他事务呢？

> 通过创建新线程的方式 在ActivityThread的main方法里调用了thread.attach(false)，这里便会创建一个Binder线程（具体是指ApplicationThread，Binder的服务端，用于接收系统服务AMS发送来的事件），该Binder 线程通过Handler将Message发送给主线程 ActivityThread对应的Handler是一个内部类H，里边包含了启动Activity、处理Activity生命周期等方法
