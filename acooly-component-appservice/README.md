<!-- title: appservice组件 -->
<!-- type: app -->
<!-- author: qiubo -->
## 1. 组件介绍

### 1.1 提供主要能力

此组件提供服务通用处理能力。此组件应用于`DDD`中的应用服务层

目前提供能力如下:

 * 打印日志
 * 请求对象检查
 * 异常统一处理

### 1.2 改变您的代码生活 

您原来写代码可能如下:

 
 	public SingleValueResult<AppDto> test1(SingleValueOrder<AppDto> appRequest) {
 		long begin = System.currentTimeMillis();
 		logger.info("[{}]请求入参:{}", "AppServiceTestContorller#test4", appRequest);
 		
 		SingleValueResult<AppDto> response = new SingleValueResult<AppDto>();
 		try {
 			Validators.checkJsr303(appRequest, Default.class, AppDto.Test1.class);
 			//do what you like
 			response.setPlayload(appRequest.getPlayload());
 		} catch (OrderCheckException e) {
 			appRequest.getPlayload().checkOnTest1(e);
 			logger.error("处理异常:", e);
 			response.setDescription(e.getMessage());
 			response.setStatus(Status.FAIL);
 			response.setCode(CommonErrorCode.INVALID_ARGUMENTS.code());
 		}
 		logger.info("[{}]请求响应:{},耗时:{}ms", "AppServiceTestContorller#test4", response, System.currentTimeMillis() - begin);
 		return response;
 	}

现在只需要这样写：

    //类上面需要加@AppService
 	@AppService.ValidationGroup(AppDto.Test1.class)
 	public SingleValueResult<AppDto> test1(SingleValueOrder<AppDto> appRequest) {
 		//do what you like
 		return SingleValueResult.from(appRequest.getPlayload());
 	}
 	
 
### 1.3 能力介绍

#### 1.3.1 打印日志

根据请求情况，如果是dubbo请求并且已经打印日志，不会再打印日志。
在controller中调用此服务会打印日志。
参考`AppServiceLogFilter`


#### 1.3.2  请求对象检查

支持如下能力：

1. 支持原`orderBase`校验逻辑
2. 支持jsr303分组校验(需要在应用服务方法上添加`AppService.ValidationGroup`注解)
3. 支持自定义方法校验，支持请求对象和请求对象字段(需标注@javax.validation.Valid)类执行checkOnXxx()方法，比如	`ValidationGroup.value=Update.class`,执行校验方法为checkOnUpdate(OrderCheckException oce)

当启用分组校验时，`orderBase.check()`方法不在调用。

参考`ParameterCheckFilter`


#### 1.3.3 异常统一处理

应用服务层所有抛出的异常由异常处理器统一处理。异常处理器遵循异常继承体系.

默认内置异常处理器如下:

	SystemExceptionExceptionHandler
	OrderCheckExceptionHandler
	BusinessExceptionHandler
	ConstraintViolationExceptionHandler
	ThrowableExceptionHandler

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-appservice</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

## 3. FAQ

### 3.1 如何使用？

在类或者公共非静态方法上标注`@AppService`注解既可。

比如我们对外提供dubbo服务，使用如下:

        @Service(version = "1.0")//dubbo服务注解
        public class DemoServiceImpl implements DemoService {
        	
        	@Override
        	@AppService//增加AppService提供的能力
        	@Transactional
        	public DemoResponse invoke(DemoRequest request) {
        		DemoInfo info = new DemoInfo();
        		Copier.copy(request, info);
        		DemoResponse demoResponse = new DemoResponse();
        		demoResponse.setStatus(Status.SUCCESS);
        		demoResponse.setInfo(info);
        		throw Exceptions.newRuntimeException("xxx");
        	}
        }

调用此服务，会响应如下:

    DemoResponse{code=comn_04_0000,description=xxx,info=null,params={},status=FAIL}

dubbo和spring其他annotation任然可以使用。@AppService内部会确保增强顺序。

### 3.2 如何扩展

1. 可以扩展请求处理链，自定义请求处理链实现`Filter<AppServiceContext>`接口。请用`getOrder`方法定义`Filter<AppServiceContext>`顺序。
2. 内置异常处理器`ExceptionHandlers`，扩展请参考`OrderCheckExceptionHandler`。
3. 由于采用自动扫描注册机制，应用开发人员不需要配置任何东东，就能让上面的扩展生效。

### 3.3 @AppService性能如何

上面的代码测试性能数据如下:
	
	AppServicePerfTest.test_AppService: [measured 40000 out of 41000 rounds, threads: 1 (sequential)]
 	round: 0.00 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+- 0.00], GC.calls: 4, GC.time: 0.09, time.total: 17.55, time.warmup: 0.50, time.bench: 17.05
	AppServicePerfTest.test_CommonService: [measured 40000 out of 41000 rounds, threads: 1 (sequential)]
 	round: 0.00 [+- 0.00], round.block: 0.00 [+- 0.00], round.gc: 0.00 [+- 0.00], GC.calls: 5, GC.time: 0.07, time.total: 15.39, time.warmup: 0.40, time.bench: 14.99

测试41000次请求，1000次warmup，每次请求延时`0.0515`ms.


### 3.4 还可以做更多么？

在应用服务层上可以提供http服务(当然小系统没有必要分这么多层，简单的直接访问数据库也行)或者`dubbo`服务，所以这一层可以做集中统一做不少事情：

1. 请求限流
2. 统一的性能、监控数据
3. 容错
4. 服务认证和授权


### 3.5 @AppService 增加logPrefix


当dubbo服务实现方法标注@AppService时，并且dubbo providerLog开启，优先打印@AppService日志。

	2017-08-11 11:45:53.766 INFO  [http-nio-8081-exec-9] ConsumerLogFilter:52-- [DUBBO-6]请求:DemoFacade#echo1[SingleOrder{dto=a,gid=598d28717a1e14044742e314,partnerId=test}] ip:192.168.13.34:12345
	2017-08-11 11:45:53.777 INFO  [DubboServerHandler-192.168.13.34:12345-thread-6] AppServiceLogFilter:59-598d28717a1e14044742e314- [测试]请求入参:[SingleOrder{dto=a,gid=598d28717a1e14044742e314,partnerId=test}]
	2017-08-11 11:45:53.780 INFO  [DubboServerHandler-192.168.13.34:12345-thread-6] AppServiceLogFilter:61-598d28717a1e14044742e314- [测试]请求响应:SingleResult{code=null,detail=null,dto=a,status=success:成功},耗时:3ms
	2017-08-11 11:45:53.786 INFO  [http-nio-8081-exec-9] ConsumerLogFilter:59-- [DUBBO-6]响应:RpcResult [result=SingleResult{code=null,detail=null,dto=a,status=success:成功}, exception=null] 耗时:20ms

如上，第一行和第四行日志为consumerLog。第二行和第三行为@AppService输出日志。

当`logPrefix`没有设置时，日志输出方法名。