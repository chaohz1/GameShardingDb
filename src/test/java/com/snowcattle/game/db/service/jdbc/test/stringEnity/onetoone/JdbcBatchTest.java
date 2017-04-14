package com.snowcattle.game.db.service.jdbc.test.stringEnity.onetoone;

import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.mapper.OrderMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jwp on 2017/3/24.
 */
public class JdbcBatchTest {
    public static void main(String[] args) {
       commonTest();
    }

    public static void commonTest(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        SqlSessionTemplate sqlSessionTemplate = (SqlSessionTemplate) classPathXmlApplicationContext.getBean("sqlSessionTemplate");
        SqlSessionTemplate batchSqlSessionTemplate = new  SqlSessionTemplate(sqlSessionTemplate.getSqlSessionFactory(), ExecutorType.BATCH);
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
            Order order = new Order();
            int startSize = 30000;
            int endSize = startSize + 200;
            long startTime = System.currentTimeMillis();
            for(int i= startSize; i< endSize; i++) {
                long id = i;
                order.setUserId(id);
                order.setId(id);
                order.setStatus("测试10000");
                order.setSharding_table_index(0);
                mapper.insertEntity(order);
            }
            sqlSession.commit();
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }catch (Exception e){

        }finally {
            sqlSession.close();
        }
    }

}
