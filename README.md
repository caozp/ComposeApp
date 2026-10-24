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
TODO

