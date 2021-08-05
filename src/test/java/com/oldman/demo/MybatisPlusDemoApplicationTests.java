package com.oldman.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oldman.demo.mapper.UserMapper;
import com.oldman.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testSelectAll() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void testInsert() {
        User user = new User();
        user.setName("测试01");
        user.setAge(20);
        user.setEmail("测试01@gmail.com");
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setId(1423098673211789314L);
        user.setName("测试01更新");
        int insert = userMapper.updateById(user);
        System.out.println(insert);
    }

    /**
     * 测试乐观锁成功
     */
    @Test
    void testVersionSuccess() {
        //查询
        User user = userMapper.selectById(1423098673211789314L);
        //更新
        user.setName("测试01乐观锁");
        int insert = userMapper.updateById(user);
        System.out.println(insert);
    }

    /**
     * 测试乐观锁失败
     */
    @Test
    void testVersionError() {
        //查询
        User user = userMapper.selectById(1423098673211789314L);
        //更新
        user.setName("测试011乐观锁");


        //查询
        User user2 = userMapper.selectById(1423098673211789314L);
        //更新
        user2.setName("测试022乐观锁");

        //我这里先执行更新user2来模拟多线程情况下被抢的情况
        userMapper.updateById(user2);
        userMapper.updateById(user);
    }



    @Test
    void testSelect() {
        //根据ID查询
        User users = userMapper.selectById(1423098673211789314L);
        //根据多个ID查询
        List<User> users1 = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        //根据Map查询
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","测试022乐观锁");
        List<User> users2 = userMapper.selectByMap(map);
    }


    @Test
    void testSelectByPage() {
        //参数一：当前页码 参数二：页面大小
        Page<User> page = new Page<>(1,2);
        Page<User> userPage = userMapper.selectPage(page, null);
        userPage.getRecords().forEach(System.out::println);
    }


    @Test
    void testDelete() {
        //根据ID删除
        int i = userMapper.deleteById(1423098673211789314L);
        //根据多个ID删除
        int i1 = userMapper.deleteBatchIds(Arrays.asList(1, 2, 3));
        //根据Map删除
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","测试022乐观锁");
        int i2 = userMapper.deleteByMap(map);
    }

    @Test
    void testDeleteById() {
        //根据ID删除
        int i = userMapper.deleteById(4L);
    }

    @Test
    void testSelectByWrapper() {
        //查询name不为空，并且邮箱不为空，年龄大于等于12的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name").isNotNull("email").ge("age",12);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void testSelectByWrapper2() {
        //查询name为测试022乐观锁的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","测试022乐观锁");
        User users = userMapper.selectOne(wrapper);
        System.out.println(users.toString());
    }

    @Test
    void testSelectByWrapper3() {
        //查询年龄在20~30之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",20,30);
        int count = userMapper.selectCount(wrapper);
        System.out.println(count);
    }

    @Test
    void testSelectByWrapper4() {
        //模糊查询 不包含e并且左模糊email（%gmail.com）
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.notLike("name","e")
                .likeLeft("email","gmail.com");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    @Test
    void testSelectByWrapper5() {
        //id在子查询查出来
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.inSql("id","select id from user where id < 3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    @Test
    void testSelectByWrapper6() {
        //通过ID进行排序
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //倒序
        wrapper.orderByDesc("id");
        //正序
        //wrapper.orderByAsc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

}
