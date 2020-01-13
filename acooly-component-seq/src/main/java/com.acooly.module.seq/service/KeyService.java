package com.acooly.module.seq.service;

import com.acooly.core.utils.Asserts;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author qiubo@yiji.com
 */
public class KeyService {
    public static final int NOT_EXISTS_KEY = -1;
    private String keyName;

    private int incrementBy;

    private AtomicLong nextId;

    private AtomicLong maxId;

    private DataSource dataSource;

    /**
     * @param dataSource  数据源
     * @param keyName     key的名称
     * @param incrementBy 每次批量获取id增量
     */
    public KeyService(DataSource dataSource, String keyName, int incrementBy) {
        this.dataSource = dataSource;
        this.incrementBy = incrementBy;
        this.keyName = keyName;
        maxId = new AtomicLong(0);
        nextId = new AtomicLong(0);
        Asserts.isTrue(incrementBy > 0);
    }

    /**
     * 获取下一个可用的id
     */
    public synchronized long nextId() {
        if (nextId.get() >= maxId.get()) {
            readFromDb();
        }
        return nextId.incrementAndGet();
    }

    private void readFromDb() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            long newNextId = queryNextId(connection);
            if (newNextId == -1) {
                insertKey(connection);
                newNextId = queryNextId(connection);
            }
            long newMaxId = newNextId + incrementBy;
            updateNextId(connection, newMaxId);
            connection.commit();
            nextId.set(newNextId);
            maxId.set(newMaxId);
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void insertKey(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("INSERT INTO sys_seq (`keyName`,`nextId`) VALUES('" + keyName + "',0);");
        } catch (SQLException e) {
            //忽略重复的key
        }
    }

    private void updateNextId(Connection connection, long nextId) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("UPDATE sys_seq SET nextId = ? where keyName= ?");
            stmt.setLong(1, nextId);
            stmt.setString(2, keyName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private long queryNextId(Connection connection) {
        try {
            PreparedStatement stmt =
                    connection.prepareStatement("SELECT nextId FROM sys_seq WHERE keyName =? FOR UPDATE");
            stmt.setString(1, keyName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("nextId");
            } else {
                return NOT_EXISTS_KEY;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
