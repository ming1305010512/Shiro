package learn.relam.jdbcRealm;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Created with IDEA
 * @author:longming
 * @Date:2019/4/24
 * @Time:14:35
 * @Description 测试JdbcRealm如何使用
 */
public class JdbcRealmTest {

    private DruidDataSource druidDataSource = new DruidDataSource();

    /**
     * 初始化 DataSource
     */
    @Before
    public void befor(){
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/shiro?serverTimezone=UTC");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("longming123");
    }

    @Test
    public void testJdbcRealm(){
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //构建JdbcRealm
        JdbcRealm jdbcRealm = new JdbcRealm();
        //为JdbcRealm设置数据源
        jdbcRealm.setDataSource(druidDataSource);
        //设置启用权限查询，默认为false
        jdbcRealm.setPermissionsLookupEnabled(true);
        defaultSecurityManager.setRealm(jdbcRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhao","123456");

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("登录失败");
        }

        System.out.println("===========认证=================");
        System.out.println("是否具备admin权限："+subject.hasRole("admin"));
        System.out.println("是否具备user权限："+subject.hasRole("user"));
        System.out.println("是否同时具备 admin 和 user 权限: " + subject.hasAllRoles(Arrays.asList("admin", "user")));
        System.out.println("--------------------授权--------------------");
        System.out.println("是否具备 user:delete 权限" + subject.isPermitted("user:delete"));
        System.out.println("是否具备 user:select 权限" + subject.isPermitted("user:select"));
        System.out.println("是否同时具备 user:delete 和 user:select 权限" + subject.isPermittedAll("user:delete", "user:select"));
    }

}
