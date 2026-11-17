# Android Compose
记录Android Compose的学习到的内容

## 控件
1. Box。对齐方式contentAlignment，以及Box作用域内的组件可以通过Modifier.align默认的对齐方式
2. CheckBox,Switch,TriStateCheckbox使用方式
3. Row 作用域内子组件的Arrangement方式
4. Image 几种ContentScale
5. Flow流式布局 包括FlowRow和FlowColumn
6. 官方推荐刷新组件PullToRefreshBox
7. 脚手架Scaffold包含AppTopbar,NavigationBar,ModalNavigationDrawer,NavHost
8. 输入框OutlineTextField和BasicTextField
9. HorizontalPager以及轮播图


## 存储
DataStore替代sp，来存储信息。示例写的是存储深色，浅色模式

Room TODO

## 导航
实现登陆页，注册页，主页的跳转


## kotlin Flow
1. Flow的创建方式
   * 冷流。flowOf asFlow flow{}
   * 热流 MutableStateFlow
   * 其他 CallbackFlow 示例写的是监听系统的网络状态
   * 冷流转热流
2. 转换
   * map   作用于上游， 比如将int转化成string流
   * filter   过滤
   * transform 实施更复杂的转换，可以发射任意次
3. 合并 
   * merge  乱序合并
   * zip    严格1对1
   * combine  依赖于相应流的最新值
   * flatMapConcat。串行，内部流一个接一个严格按照顺序执行
   * flatMapLatest  只保留最新，搜索联想,按钮防重复点击
4. 限长操作符
   * take 取
   * drop 放弃
5. 末端操作符  collect时最基础的末端操作符。 还有toList toSet  single  first
6. 异常 catch
7. 生命周期
   * onStart
   * onEach
   * onCompletion
8. 限流
   * debounce  延迟
   * distinctUntilChanged 去重直到不重复
9. 切换协程 flowOn 上流异步

SnapShotFlow。将state转化成StateFlow
.stateIn。将flow转化成StateFlow。


## 动画
* AnimatedVisibility  进入退出的动画。
  Modifier.animateEnterExit 还可以为AnimatedVisibility内部子元素单独设置动画


* AnimatedContent  用来实现不同组件间切换的动画


* animateValueAsState。动画规格 spring tween keyframes
  **animateTo 方法**

## 手势
TODO

## 依赖注入
IOC控制反转 借助 第三方 实现 具有依赖关系的对象之间的解耦。 也叫依赖注入

### Dagger

1. 最简单的例子Car依赖Engine。
需要使用Inject注解添加到Engine的构造方法

```
class Engine {
    @Inject
    public Engine() 
}
```
然后需要创建一个用Component标注的接口CarComponent。实际上是注入器
```
@Component
public interface CarComponent {
   void inject(Car car)
}
```
最后在Car类中完成注入动作
```
class Car {
    @Inject
    Engine engine
    
    public Car() {
       DaggerCarComponent.builder().build().inject(this)
    }
}
```

2. 模块注入方式，使用Module类和Provide来
```
@Module
public class NetModule{

    @Provides
    public Retrofit provideRetrofit() {
         return new Retrofit.Builder().baseUrl().build()
    }
    
    //该Module已经知道怎么获取Retrofit的实例，所以可以以参数的形式传进来
    @Provides
    public ApiService provideApiService(Retrofit retrofit) {
         return retrofit.create(ApiService.class)
    }
}
```
在注入器中 指定module的class
```agsl
@Component(modules=NetModule.class)
public interface AppComponent {
   void inject(MainActivity activity)
}
```


作用域 可以将某个对象的生命周期限定为组件的生命周期。也就是在作用域范围内，使用到的是同一实例
Singleton是默认的作用域注解。 某个对象的生命周期指定了Singleton，那么它的注入器也要指定对应的Singleton

使用Scope可以自定义作用域

组件依赖 , 使用dependencies或者subComponent
```
@UserScope
@Component(modules=UserModule.class,dependencies = AppComponent.class)
public interface UserComponent {

}

```

### Hilt
安装指南https://dagger.dev/hilt/gradle-setup.html 

添加对Compose的支持
```
implementation("androidx.hilt:hilt-navigation-compose:1.4.0")
```

|hilt组件|注入对象|作用域|
|:--:|:--:|:--:|
|SingletonComponent|全局单例|Singleton|
|AcvitityRetainedComponent|ViewModel|AcvitityRetainedScope|
|AcvitityComponent|Activity|AcvitityScope|



### 固有特性测量
固有特性测量IntrinsicSize 是解决 父容器尺寸由内容决定，但子项又需要撑满父容器 这类问题

Compose测量流程
父容器给约束，子项测量自己。子项报告尺寸，父容器决定自己的尺寸

IntrinsicSize会带来两遍测量的性能
父容器先问每个子项的固有尺寸，父容器根据固有尺寸确定自己的尺寸。再以这个确定的尺寸作为约束，重新测量子项

Row高度由最高子项决定,子项撑满
Row(Modifier.height(IntrinsicSize.Min)) + Modifier.fillMaxHeight()

Column宽度由最高子项决定，其他子项撑满时用

Column(Modifier.width(IntrinsicSize.Max)) + Modifier.fillMaxWidth()

注意点:  
不是所有组件都支持固有测量，比如Canvas和位置定尺寸的Image。

两遍测量有性能成本