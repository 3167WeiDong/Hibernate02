import com.wd.po.Emp;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.junit.Before;

import java.util.List;

public class Test {
    //会话工厂
    private SessionFactory sessionFactory;
    //会话
    private Session session;
    //事务
    private  Transaction tx;


    @org.junit.Before
    public void before(){
        //1.获取SessionFactiory 会话工厂
        sessionFactory=new Configuration().configure().buildSessionFactory();
        //2.生产会话  通过openSession获取session对象
        session=sessionFactory.openSession();
        //3.开启事务
        tx = session.beginTransaction();
    }
    @org.junit.Test
    public void testInset(){
       Emp e=new Emp();
       e.setName("王五8");
       e.setEmail("1244@qq.com");
       session.save(e);
    }
    @org.junit.Test
    public void testdelete(){
        Emp e=session.get(Emp.class,1);
        if(e!=null){
            session.close();
        }
    }
    //新增或者修改    根据实体类中的主键进行查询 如果查询到有数据则进行修改
    //                                      如果查询不到数据则进行新增
    @org.junit.Test
    public void testSaveOrUpdate(){
        Emp e=new Emp();
        e.setId(1);
        e.setEmail("234@qq.com");
        e.setName("张三");
        session.saveOrUpdate(e);
    }

    @org.junit.Test
    public void test() {
        //4.1执行查询 get(要查询的实体类型,参数,)
        Emp emp=session.get(Emp.class,1);
        System.out.println("emp="+emp);
        tx.commit();//提交事务
        session.close();//关闭session
        session=sessionFactory.openSession();
        tx=session.beginTransaction();
        //4.2执行查询(查询单挑数据)
        Emp emp1=session.load(Emp.class,1);
        System.out.println("emp1="+emp1);
        //4.3执行查询(查询单条数据)  hql语句
        Query query =session.createQuery("from Emp where id=2");
        Object o= query.uniqueResult();//只针对于已经知道的一条语句是才能使用
        System.out.println("o="+o);
        //查询全部

        SQLQuery sqlQuery=session.createSQLQuery("select * from emp");
        sqlQuery.addEntity(Emp.class);
        List<Emp> list1=sqlQuery.list();
        for(Emp stu:list1){
            System.out.println("stu="+stu);
        }

    }
    @org.junit.Test
    public void after(){
        tx.commit();
        session.close();
    }

}
