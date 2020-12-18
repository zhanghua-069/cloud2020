package com.fleexy.springcloud.controller;

import com.fleexy.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_OK(id);
        log.info("*******result:" + result);
        return result;
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("*******result:" + result);
        return result;
    }


    //===服务熔断
    /*@HystrixCommand(fallbackMethod = "str.fallbackMethod",
            groupKey = "strGroupCommand",
            commandKey = "strCommand",
            threadPoolKey = "strThreadPoo1",
            commandProperties = {
                //没置隔离策略，THREAD 表示线程他SEMAPHORE: 信号他隔离
                @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                //当隔离策略选撣信号他隔离的时候，用来没置信号她的大小(最大并发数)
                @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "10"),
                //配置命令执行的超时时间
                @HystrixProperty(name = "execution.isolation.thread.timeoutinMilliseconds", value = "10"),
                //是否启用超时时间
                @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
                //执行超时的时候是否中断
                @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout", value = "true"),
                //执行被取消的时候是否中断
                @HystrixProperty(name = "execution.isolation.thread.interruptOnCancel", value = "true"),
                //允许回调方法执行的最大并发数
                @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "10"),
                //服务降級是否肩用，是否执行回调西数
                @HystrixProperty(name = "fallback.enabled", value = "true"),
                //服务熔断是否启用
                @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                //该属性用来没置在演动时间窗中，断路器熔断的最小请求数。例如，默认该值为20的时候，
                //如果壤动时间窗(默以10秒)内仅收到了19个请求，即使这19个请求都失败了， 断路器也不会打开。
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                //该属性用来没置在演动时间窗中，表示在滚动时间窗中，在请求数量超过
                // circuitBreaker.requestVoLumeThreshold的情况下，如果错误请求数的百分比超过50,
                //就把断路器没置为“打开”状态，否则就没置为"关闭”状态。
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                //该属性用来设置当断路器打开之后的休眠时间窗。休眠时间窗结束之后，
                //会将断路器置为”半开”状态，t熔断的请求命令，如果低然失败就將断路器继续设置为"打开”状态，
                //如果成功就设置为"关闭”状态。
                @HystrixProperty(name = "circuitBreaker.sleepWindowinMilliseconds", value = "5000"),
                //断路器强制打开
                @HystrixProperty(name = "circuitBreaker.forceOpen", value = "false"),
                //断路器强制关闭
                @HystrixProperty(name = "circuitBreaker.forceClosed", value = "false"),
                //演动时间窗设置，该时间用于断路器判断健康度时需要收集信息的持续时间
                @HystrixProperty(name = "metrics.rollingStats.timeinMilliseconds", value = "10000"),
                //该属性用来没置婉动时间窗统计指标信息时划分”桶”的数量，断路器在收葉指标信息的时候会根据
                //设置的时间窗长度拆分成多个“捕”来累计各度量值，每个”榜" 记录了- -段时间内的采集指标。
                //比如10砂内拆分成10个”捕"收集这样，所以timeinMilliseconds 必须能被numBuckets 整除。否则会抛异常
                @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),
                //该属性用来没置对命令执行的延迟是否使用百分位数来跟踪和计算。如果没置为false,那么所有的概要统计都将返回-1。
                @HystrixProperty(name = "metrics.rollingPercentile.enabled", value = "false"),
                //该属性用来没置百分位统计的演动窗口的持续时间，单位为毫秒。
                @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value ="60000"),
                //该属性用来没置百分位统计演动窗口中使用“桶”的数量。
                @HystrixProperty(name = "metrics.rollingPercenthie.numBuckets", value = "60000"),
                // 该属性用来没置在执行过程中每个“桶” 中保留的最大执行次数。 如果在演动时间窗内发生超过该没定值的执行次数，
                //就从最初的位置开始重写。例如，将该值设置为100,颇动窗口为10秒，若在10秒内一 -个“桶 ”中发生了500次执行，
                //那么该“桶” 中只保留最后的100次执行的统计。另外，增加该值的大小将会增加内存量的消耗，并增加排序百分位数所需的计算时间。
                @HystrixProperty(name = "metrics.rollingPercentile.bucketSize", value = "100"),
                //该属性用来没置采集影响断路器状态的健康快照(请求的成功、错误百分比) 的间隔等待时间。
                @HystrixProperty(name = "metrics.healthSnapshot.intervalinMilliseconds", value = "500"),
                //是否开启请求缓存
                @HystrixProperty(name = "requestCache.enabled", value = "true"),
                // HstrixCommand的执行和事件是否打印日志到HystrixRequestlog中
                @HystrixProperty(name = "requestLog.enabled", value = "true"),
            },
            threadPoolProperties = {
                //该参数用来设置执行命令线程她的核心线程数，该值也就是命 令执行的最大并发量
                @HystrixProperty(name = "coreSize", value = "10"),
                // 该参数用来设置线程她的最大队列大小。当汝置为-1时，线程池将使用SynchronousQueue实现的队列，
                //否则将使用LinkedBlockingQueue实现的队列。
                @HystrixProperty(name = "maxQueueSize", value = "-1"),
                //该参数用来为队列设置拒绝阅值。通过该参数， 即使队列没 有达到最大值也能拒绝请求。
                //该参数主要是对LinkedBlockingQueue队列的补充,因为L inkedBlockingQueue
                //队列不能动态修改它的对象大小，而通过该属性就可以调整拒绝请求的队列大小了。
                @HystrixProperty(name = "queueSizeRejectionThreshold", value = "5")
            }
    )*/
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        return paymentService.paymentCircuitBreaker(id);
    }
}
