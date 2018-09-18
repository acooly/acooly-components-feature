<!-- title: 白盒测试组件 -->
<!-- type: app -->
<!-- author: qiubo -->

## 1. 组件介绍

此组件提供测试支持

关于spring-boot对测试的支持，参考[blog](https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4)


## 2. 使用说明

### 2.1 单元测试

单元测试继承`AppTestBase`或者`AppWebTestBase`,`archtype`生成的测试module中有`TestBase`对象，应用开发者集成即可。

* `AppTestBase` 不会启动web环境
* `AppWebTestBase` 会启动web环境

### 2.2 参数化测试

相关代码:[JUnitParams](http://gitlab.yiji/fintech/JUnitParams)，对官方代码进行增强，使之支持类型转换。


    @RunWith(JUnitParamsRunner.class)
    @Slf4j
    public class XXTest {
    	
    	@Test
    	//读取csv文件，csv文件第一行为头信息
    	@CsvParameter(value = "test.csv")
    	@TestCaseName("id={0}")
    	public void test1(int id, int age, String name) {
    		log.info("id={},age={},name={}", id, age, name);
    	}
    	
    	@Test
    	@CsvParameter(value = "test.csv")
    	@TestCaseName("id={0}")
    	//转换csv文件内容为对象
    	public void test2(ParamDTO dto) {
    		log.info("{}", dto);
    	}
    	
    	@Data
    	public static class ParamDTO {
    		private Long id;
    		private int age;
    		private String name;
    	}
    }

### 2.3 集成测试

集成测试继承`IntegrationTestBase`,集成测试场景下，不会启动tomcat/web,并支持参数化测试。

例子：


    @Slf4j
    public class MultiDSTest extends IntegrationTestBase {
    	public static final String PROFILE = "sdev";
        //设置环境
    	static {
    		Apps.setProfileIfNotExists(PROFILE);
    	}
    	//注入dao
    	@Autowired
    	private AppDao appDao;
    	@Autowired
    	private CityDao cityDao;
    	
    	@Test
    	public void testMultiDataSource() throws Exception {
    		App app = new App();
    		app.setName("bohr");
    		appDao.create(app);
    		City city = new City();
    		city.setName("chongqing");
    		cityDao.create(city);
    	}
        
    	@Test
    	//读取csv文件，csv文件第一行为头信息，当文件不存在时，会自动创建csv文件(这里使用了jdk8特性，请给java compiler加上-parameters参数,保证参数名正确)
    	@CsvParameter(value = "test.csv")
    	//测试命名，方便测试用例失败时，IDE可视化展示
    	@TestCaseName("id={0}")
    	//参数自动转换,我们可以充分利用代码生成器生成的entity
    	public void test2(MultiDSTest.ParamDTO dto) {
    		log.info("{}", dto);
    	}
    
    	@Data
    	public static class ParamDTO {
    		private Long id;
    		private int age;
    		private String name;
    	}
    }

  csv 文件如下：

    id,age,name
    1,123,dfd
    2,145,bohr
    3,,

### 2.4 dubbo 测试

在开发时，我们希望服务debug模式启动(并使用spring loaded/jrebel)，然后跑单元测试来发现问题，发现问题后及时修改，不重启服务，并且测试也能快速跑完。

请使用`@DubboTest`，以后测试dubbo服务，速度屌炸天。

代码参考`com.acooly.core.test.dubbo.DubboConsumerTest`

    @RunWith(SpringRunner.class)
    @DubboTest
    public class DubboConsumerTest {
      protected static final String PROFILE = "sdev";

      static {
        Apps.setProfileIfNotExists(PROFILE);
      }

      @Reference(version = "1.0")
      private DemoFacade demoFacade;

      @Test
      public void test() {
        SingleOrder<String> request = new SingleOrder<>();
        request.gid().partnerId("test");
        request.setDto("123");
        demoFacade.echo(request);
      }
    }


注意：建议测试类放在`basePackage`下的test包（如果放在Main类相同的包，会扫描所有类，影响启动速度。）。
