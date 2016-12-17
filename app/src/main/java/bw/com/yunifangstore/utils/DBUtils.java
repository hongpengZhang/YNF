package bw.com.yunifangstore.utils;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;


public class DBUtils {

    private static DbManager db;

    public static DbManager getDb() {
        return db;
    }

    private DBUtils() {
    }

    static {
        //本地数据的初始化
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("goods") //设置数据库名
                .setDbVersion(1) //设置数据库版本,每次启动应用时将会检查该版本号,
                //发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
                .setAllowTransaction(true)//设置是否开启事务,默认为false关闭事务
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreate(DbManager db, TableEntity<?> table) {
                    }
                })//设置数据库创建时的Listener
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    }
                });//设置数据库升级时的Listener,这里可以执行相关数据库表的相关修改,比如alter语句增加字段等
        db = x.getDb(daoConfig);
    }

}
