package com.acooly.core.test.event;

import com.acooly.core.test.dao.CityMybatisDao;
import com.acooly.module.cms.domain.Content;
import com.acooly.module.cms.event.ContentCreatedEvent;
import com.acooly.module.event.EventBus;
import com.acooly.module.event.EventHandler;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author qiubo@yiji.com
 */
@RestController
@EventHandler
@RequestMapping(value = "/event")
@Slf4j
public class EventBusController {
    @Autowired(required = false)
    private EventBus eventBus;

    @Autowired
    private CityMybatisDao cityDao;
    @Autowired
    private SqlSessionFactory sessionFactory;

    @RequestMapping("test")
    @Transactional
    public void test() {
        val holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
        if (holder != null) {
            log.info("{}", holder.getSqlSession().getConnection());
        }
        cityDao.get(1l);
        CreateCustomerEvent event = new CreateCustomerEvent();
        event.setId(1l);
        event.setUserName("dfd");
        eventBus.publishAfterTransactionCommitted(event);
        eventBus.publishAfterTransactionCommitted(event);
        cityDao.get(1l);
    }

    // 同步事件处理器
    @Handler(delivery = Invoke.Asynchronously)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleCreateCustomerEvent(CreateCustomerEvent event) {
        log.info("{}", event);
        cityDao.get(1l);
        log.info("{}", event);


        throw new CannotAcquireLockException("x");
        // do what you like
    }

    @EventHandler
    public class CmsEventHandler {
        //同步事件处理器
        @Handler
        public void handleContentCreatedEvent(ContentCreatedEvent event) {
            log.info("同步cms ContentCreatedEvent事件处理器{}",event.getId());
        }
        //异步事件处理器
        @Handler(delivery = Invoke.Asynchronously)
        public void handleContentCreatedEventAsyn(ContentCreatedEvent event) {
            //do what you like
            log.info("异步cms ContentCreatedEvent事件处理器{}",event.getId());
        }
    }

    @EventHandler
    public class UerCreatedEventHandler {
        //同步事件处理器
        @Handler
        public void handleUserCreatedEventEvent(UserCreatedEvent event) {
            //do what you like
            log.info("同步UserCreatedEvent事件处理器{}",event.toString());
        }
        //异步事件处理器
        @Handler(delivery = Invoke.Asynchronously)
        public void handleUserCreatedEventAsyn(UserCreatedEvent event) {
            //do what you like
            log.info("异步UserCreatedEvent事件处理器{}",event.toString());
        }
    }
}
