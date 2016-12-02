package com.zrj.dllo.meetyou.tools;


import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.zrj.dllo.meetyou.app.MeetYouApp;

import java.util.List;


/**
 * 数据库操作单例类
 * 单例模式定义:动态确保某一个类只有一个实例，而且自行实例化并向整个系统提供这个实例。
 * 单例好处:
 * 1.由于单例模式在内存中只有一个实例，减少了内存开销。
 * 2.单例模式可以避免对资源的多重占用，例如一个写文件时，由于只有一个实例存在内存中，避免对同一个资源文件的同时写操作。
 * 3.单例模式可以在系统设置全局的访问点，优化和共享资源访问。
 * 4.其中使用到单例模式时，考虑较多的就是多线程的情况下如何防止被多线程同时创建等问题
 */
public class LiteOrmInstance {
    /**
     * 知识扩展:
     * 封装单例的7中方法
     * 1.懒汉模式(线程不安全)
     * 2.懒汉模式(线程安全)
     * 3.饿汉模式(线程安全)
     * 4.DLC双重校验锁模式
     * 5.静态内部类单例模式
     * 6.枚举单例模式
     * 7.使用容器实现单例模式
     */
    /*********************************双重检验锁****************************************/
    /**
     * 封装单例的三个步骤(双重检验锁)
     * 1.私有静态变量
     * 2.私有构造方法
     * 3.公有静态外部接口
     */
    //private static LiteOrmInstance liteOrmInstance;//老版本DLC可能失效
    private volatile static LiteOrmInstance liteOrmInstance;//JDK1.5之后的改进版,防止DLC失效
    private LiteOrm mLiteOrm;

    private LiteOrmInstance() {
        //创建数据库
        mLiteOrm = LiteOrm.newCascadeInstance(MeetYouApp.getContext(), StaticValues.LO_DB_NAME);
        mLiteOrm.setDebugged(true);
    }

    public static LiteOrmInstance getInstance() {
        if (liteOrmInstance == null) {//为了避免不必要的同步
            synchronized (LiteOrmInstance.class) {
                if (liteOrmInstance == null) {//当没有这个实例的时候创建实例
                    liteOrmInstance = new LiteOrmInstance();
                }
            }
        }
        return liteOrmInstance;
    }

    /**********************************懒汉模式(线程不安全)*************************************/
    /**
     * 1.私有的静态方法
     * 2.私有的构造方法
     * 3.暴露的静态方法
     */
    /**
     * 特点:
     * 1.线程不安全(解决方法:加入同步代码块-下面这种方法)
     */
//    private static LiteOrmInstance liteOrmInstance;
//    private LiteOrmInstance(){}
//    public static LiteOrmInstance getInstance(){
//        if (liteOrmInstance==null){
//            liteOrmInstance=new LiteOrmInstance();
//        }
//        return liteOrmInstance;
//    }
    /**********************************懒汉模式线程安全*******************************************************/
    /**
     * 1.私有静态变量
     * 2.私有构造方法
     * 3.公有同步静态方法
     */
    /**
     * 特点:同步代码块
     * 也就是告诉Java（JVM）getInstance是一个同步方法
     * 同步的意思是当两个并发线程访问同一个类中的这个synchronized同步方法时，
     * 一个时间内只能有一个线程得到执行，
     * 另一个线程必须等待当前线程执行完才能执行，
     * 因此同步方法使得线程安全，
     * 保证了单例只有唯一个实例。
     */
//    private static LiteOrmInstance liteOrmInstance;
//    private LiteOrmInstance(){}
//    public static synchronized LiteOrmInstance getInstance(){
//        if (liteOrmInstance==null){
//            liteOrmInstance=new LiteOrmInstance();
//        }
//        return liteOrmInstance;
//    }
    /**********************************饿汉模式(线程安全)************************************************/
    /**
     *  1.私有静态变量(同时实例化)
     *  2.私有构造方法
     *  3.公有静态方法(直接返回实例)
     */
    //  private static LiteOrmInstance liteOrmInstance=new LiteOrmInstance();//老版本
//      private static LiteOrmInstance liteOrmInstance;//新版本(变种的俄汉单例模式)
//      static {//新版本(变种的俄汉单例模式)
//          //也是基于classloder机制避免了多线程的同步问题，instance在类装载时就实例化了。
//          liteOrmInstance=new LiteOrmInstance();
//      }
//    private LiteOrmInstance(){}
//    public static LiteOrmInstanc getInstance(){
//        return liteOrmInstance;
//    }
    /***********************************静态内部类单例模式******************************************/
    /**
     * 1.私有构造方法
     * 2.静态内部类
     * 3.公有静态final方法
     */
    /**
     * 特点:
     * 1.当第一次加载LiteOrmInstance 类的时候并不会初始化INSTANCE.
     * 2.只有第一次调用LiteOrmInstance的getInstance（）方法时才会导致INSTANCE 被初始化.
     * 3.因此，第一次调用getInstance（）方法会导致虚拟机加载LiteOrmInstanceHolder类，
     *   这种方式不仅能够确保单例对象的唯一性，同时也延迟了单例的实例化。
     */
//    private LiteOrmInstance(){}
//    private static class LiteOrmInstanceHolder{
//        private static final LiteOrmInstance INSTANCE=new LiteOrmInstance();
//    }
//    public static final LiteOrmInstance getInstance(){
//        return LiteOrmInstanceHolder.INSTANCE;//在这里创建实例
//    }
    /***********************************枚举单例********************************************************/


    /***********************************使用容器实现单例****************************************************/


    /*****************对外提供操作数据库的方法********************/
    /**
     * 插入一条记录
     *
     * @param t
     */
    public <T> long insert(T t) {
        return mLiteOrm.save(t);
    }

    /**
     * 插入所有记录
     *
     * @param list
     */
    public <T> void insertAll(List<T> list) {
        mLiteOrm.save(list);
    }

    /**
     * 查询所有
     *
     * @param cla
     * @return
     */
    public <T> List<T> getQueryAll(Class<T> cla) {
        return mLiteOrm.query(cla);
    }

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public <T> List<T> getQueryByWhere(Class<T> cla, String field, String[] value) {
        return mLiteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", value));
    }

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     *
     * @param cla
     * @param field
     * @param value
     * @param start
     * @param length
     * @return
     */
//    public <T> List<T> getQueryByWhereLength(Class<T> cla, String field, String[] value, int start, int length) {
//        return mLiteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", value).limit(start, length));
//    }

    /**
     * 删除一个数据
     *
     * @param t
     * @param <T>
     */
    public <T> void delete(T t) {
        mLiteOrm.delete(t);
    }

    /**
     * 删除一个表
     *
     * @param cla
     * @param <T>
     */
    public <T> void delete(Class<T> cla) {
        mLiteOrm.delete(cla);
    }

    /**
     * 删除集合中的数据
     *
     * @param list
     * @param <T>
     */
    public <T> void deleteList(List<T> list) {
        mLiteOrm.delete(list);
    }

    /**
     * 删除数据库
     */
    public void deleteDatabase() {
        mLiteOrm.deleteDatabase();
    }

}
