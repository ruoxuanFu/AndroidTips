package com.fdd.mydagger2test.mpvtest;

import dagger.Module;
import dagger.Provides;

/**
 * 为什么要创建model
 * 因为注入MvpTestPresenter时，构造方法需要传递参数，参数是一个接口
 * 使用@Inject有局限：
 * 1.接口没有构造函数
 * 2.第三方库的类不能被标注
 * 3.构造函数中的参数必须配置
 * <p>
 * 此时@Provides标注的方法来提供依赖实例，方法的返回值就是依赖的对象实例，@Provides方法必须在Module中，Module即用@Module标注的类
 * <p>
 * 所以Module是提供依赖的对象实例的另一种方式。
 */
@Module
public class MvpTestModule {
    private final MvpTestContract.BaseView view;

    public MvpTestModule(MvpTestContract.BaseView view) {
        this.view = view;
    }

    @Provides
    MvpTestContract.BaseView provideMainView() {
        return view;
    }

}
