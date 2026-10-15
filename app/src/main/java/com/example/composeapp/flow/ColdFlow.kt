package com.example.composeapp.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip

suspend fun createFlow() {
    //创建方式flowOf  asFlow  flow{}
    val demoFlow = flowOf("数据1","数据2","数据3")
    demoFlow.collect {
        println("$it")
    }

    val demoList = listOf("数据1","数据2","数据3")
    val demoFlow2 = demoList.asFlow()
    demoFlow2.collect {
        println("$it")
    }


    flow {
        for (i in 1..10) {
            delay(1000)
            emit(i)
        }
    }.collect { value->print("数据$value") }
}

suspend fun performRequest(request:Int):String {
    delay(1000)
    return "response $request"
}


suspend fun transformFlow() {
    //map操作符, 作用于上游， 将int转化成string流
    (1..10).asFlow()
        .map { request-> performRequest(request) }
        .collect {
            response-> println(response)
        }

   //过滤
    (1..10).asFlow()
        .debounce(300) //防抖
        .distinctUntilChanged() //去除连续重复
        .filter { request-> request>5 }
        .collect {
                response-> println(response)
        }

    //transform 实施更复杂的转换，可以发射任意次
    (1..10).asFlow()
        .transform { request->
            emit("making request $request")
            emit(performRequest(request))
        }
        .collect {
            response-> println(response)
        }
}

//限长操作符 take  drop
suspend fun takeFlow() {
    flow {
        for (i in 1..10) {
            emit(i)
        }
    }.take(4).collect {
        response-> println(response)
    }
}


//末端操作符
//collect时最基础的末端操作符。 还有toList toSet  single  first
suspend fun reduceFlow() {
    val result = flow {
        for (i in 1..10) {
            emit(i)
        }
    }.map{it*it}
        //.reduce { a, b -> a+b }
        .toList()
    print(result)
}

//合并流
// 乱序合并
suspend fun mergeFlow() {
    val nums = (1..3).asFlow()
    val nums2 = (4..10).asFlow()

    merge(nums,nums2)
        .collect { println(it) }
}

//zip。 严格配对 一一对应
suspend fun zipFlow() {
    val nums = (1..3).asFlow()
    val strs = flowOf("One","Two","Three")

    nums.zip(strs) { a,b-> "$a $b"}
        .collect { println(it) }
}


//combine  依赖于相应流的最新值
suspend fun combineFlow() {
    val nums = (1..3).asFlow().onEach { delay(3000) }
    val strs = flowOf("One","Two","Three").onEach { delay(4000) }

    nums.combine(strs) { a,b-> "$a $b"}
        .collect { println(it) }
}

//flatMapConcat 串行
//内部流一个接一个严格按照顺序执行
suspend fun flatMapConcatFlow() {
    flowOf(1,2,3,4)
        .flatMapConcat { id->
            flow {
                emit("$id-start")
                delay(1000)
                emit("$id-end")
            }}
        .collect { println(it) }

    //flatMapMerge 并发乱序
    //flatMapLatest 只保留最新， 搜索联想,按钮防重复点击
}

//
suspend fun lifeFlow() {
    flowOf(1,2,3,4)
        .onStart {
            print("开始")
        }
        .onEach { print(it) } //可以将catch操作符的代码移动到onEach中，并将其放在catch操作符前。收集该流必须由无参的collect来触发
        .onCompletion { print("结束") }
        .collect()
}

//捕获异常
suspend fun catchExceptionFlow() {
    flow<Int> {
        throw Exception("网络错误")
    }.catch { exception->
        print(exception.message)
    }.collect { it->
        print("更新$it")
    }
}


//切换线程flowOn
suspend fun flowOnFlow() {
    val nums = (1..3).asFlow()
    val nums2 = (4..10).asFlow()

    merge(nums,nums2)
        .flowOn(Dispatchers.IO)
        .collect { println(it) }
}
