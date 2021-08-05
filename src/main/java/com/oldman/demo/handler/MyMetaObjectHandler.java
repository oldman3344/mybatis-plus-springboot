package com.oldman.demo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author oldman
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入填充策略
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        //因为我的mp版本是3.4.3.1，所以官方推荐是使用这种方式来进行填充，如果使用的其它版本请参考一下下面的注释

        // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);

        // 起始版本 3.3.0(推荐使用)
        //this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());

        // 也可以使用(3.3.0 该方法有bug)
        //this.fillStrategy(metaObject, "createTime", LocalDateTime.now());
    }

    /**
     * 更新填充策略
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        //因为我的mp版本是3.4.3.1，所以官方推荐是使用这种方式来进行填充，如果使用的其它版本请参考一下下面的注释

        // 起始版本 3.3.3(推荐)
        this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);

        // 起始版本 3.3.0(推荐使用)
        //this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        // 也可以使用(3.3.0 该方法有bug)
        //this.fillStrategy(metaObject, "updateTime", LocalDateTime.now());
    }
}
